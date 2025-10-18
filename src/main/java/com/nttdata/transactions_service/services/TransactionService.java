package com.nttdata.transactions_service.services;

import com.nttdata.transactions_service.dtos.CreateTxRequest;
import com.nttdata.transactions_service.exceptions.BusinessException;
import com.nttdata.transactions_service.models.Account;
import com.nttdata.transactions_service.models.Transaction;
import com.nttdata.transactions_service.repositories.AccountRepository;
import com.nttdata.transactions_service.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;
import java.time.Instant;

import static com.nttdata.transactions_service.utils.Constans.*;
import static com.nttdata.transactions_service.utils.TransactionStatus.SUCCESS;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final RiskService riskService;
    private final Sinks.Many<Transaction> txSink;

    public Mono<Transaction> create(CreateTxRequest request) {
        return accountRepository.findByNumber(request.getAccountNumber())
                .switchIfEmpty(Mono.error(new BusinessException(EXCEPTION_ACCOUNT_NOT_FOUND)))
                .flatMap(acc -> validateAndApply(acc, request))
                .onErrorMap(IllegalStateException.class, e -> new
                        BusinessException(e.getMessage()));
    }

    private Mono<Transaction> validateAndApply(Account account, CreateTxRequest request) {
        String type = request.getType().toUpperCase();
        BigDecimal amount = request.getAmount();
        return riskService.isAllowed(account.getCurrency(),type,amount)
                .flatMap(allowed ->{
                    if(!allowed) return Mono.error(new BusinessException(EXCEPTION_RISK_REJECTED));
                    if(DEBIT_TRANSACTION.equals(type) && account.getBalance().compareTo(amount) < 0){
                        return Mono.error(new BusinessException(EXCEPTION_INSUFFICIENT_FUNDS));
                    }
                    return Mono.just(account).publishOn(Schedulers.parallel())
                            .map(a ->{
                                BigDecimal newBalance = DEBIT_TRANSACTION.equals(type) ?
                                        a.getBalance().subtract(amount) :
                                        a.getBalance().add(amount);
                                a.setBalance(newBalance);
                                return a;
                            })
                            .flatMap(accountRepository::save)
                            .flatMap(saved-> transactionRepository.save(Transaction.builder()
                                    .accountId(saved.getId())
                                    .type(type)
                                    .amount(amount)
                                    .timestamp(Instant.now())
                                    .status(SUCCESS.getStatus())
                                    .build()))
                            .doOnNext(tx -> txSink.tryEmitNext(tx));
                });
    }

    public Flux<Transaction> byAccount(String accountNumber) {
        return accountRepository.findByNumber(accountNumber)
                .switchIfEmpty(Mono.error(new BusinessException(EXCEPTION_ACCOUNT_NOT_FOUND)))
                .flatMapMany(acc -> transactionRepository.findByAccountIdOrderByTimestampDesc(acc.getId()));
    }

    public Flux<ServerSentEvent<Transaction>> stream() {
        return txSink.asFlux()
                .map(tx -> ServerSentEvent.builder(tx).event(EVENT_TRANSACTION).build());
    }

}
