package com.zidol.springreactive.example.coldhotsequnce;

import com.zidol.springreactive.utils.Logger;
import reactor.core.publisher.Flux;

import java.util.Arrays;

public class ColdSequenceExample {
    public static void main(String[] args) {
        Flux<String> coldFlux = Flux.fromIterable(Arrays.asList("RED", "YELLOW", "PINK"))
                .map(String::toLowerCase);
        coldFlux.subscribe(country -> Logger.info("# Subscriber1 : {}", country));
        Logger.info("-------------------------");
        coldFlux.subscribe(country -> Logger.info("# Subscriber2 : {}", country));
    }
}
