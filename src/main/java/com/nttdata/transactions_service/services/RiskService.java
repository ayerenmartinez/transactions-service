package com.nttdata.transactions_service.services;

import com.nttdata.transactions_service.models.RiskRule;
import com.nttdata.transactions_service.repositories.RiskRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;

import static com.nttdata.transactions_service.utils.Constans.DEBIT_TRANSACTION;

@Service
@RequiredArgsConstructor
public class RiskService {

    private final RiskRuleRepository riskRepo;

    public Mono<Boolean> isAllowed(String currency, String type, BigDecimal amount) {
        return Mono.fromCallable(()->
            riskRepo.findFirstByCurrency(currency)
                    .map(RiskRule::getMaxDebitPerTx)
                    .orElse(new BigDecimal("0")))
                .subscribeOn(Schedulers.boundedElastic())
                .map(max ->{
                   if(DEBIT_TRANSACTION.equalsIgnoreCase(type)){
                       return amount.compareTo(max) <= 0;
                   }
                   return true;
                });
    }

}
