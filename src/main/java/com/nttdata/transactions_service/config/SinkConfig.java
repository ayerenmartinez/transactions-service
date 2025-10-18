package com.nttdata.transactions_service.config;

import com.nttdata.transactions_service.models.Transaction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Sinks;

@Configuration
public class SinkConfig {

    @Bean
    public Sinks.Many<Transaction> txSink(){
        return Sinks.many().multicast().onBackpressureBuffer();
    }

}
