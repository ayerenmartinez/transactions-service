package com.nttdata.transactions_service.controllers;

import com.nttdata.transactions_service.dtos.CreateTxRequest;
import com.nttdata.transactions_service.models.Transaction;
import com.nttdata.transactions_service.services.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/transactions")
    public Mono<ResponseEntity<Transaction>> create(@Valid @RequestBody CreateTxRequest request){
        return transactionService.create(request)
                .map(tx -> ResponseEntity.status(HttpStatus.CREATED).body(tx));
    }

    @GetMapping("/transactions")
    public Flux<Transaction> list(@RequestParam String accountNumber){
        return transactionService.byAccount(accountNumber);
    }

    @GetMapping(value = "/stream/transactions", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<Transaction>> stream(){
        return transactionService.stream();
    }
}
