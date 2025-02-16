package com.zidol.springreactive.example.context;

import com.zidol.springreactive.utils.Logger;
import com.zidol.springreactive.utils.TimeUtils;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.context.Context;

/**
 * ContextView API 예제 코드
 *
 */
public class ContextAPIExample03 {
    public static void main(String[] args) {
        String key1 = "id";
        String key2 = "name";

        Mono.deferContextual(ctx ->
                        Mono.just("ID: " + " " + ctx.get(key1) + ", "
                                + "Name: " + ctx.get(key2) + ", "
                                + "Job: " + ctx.getOrDefault("job", "Software Engineer"))
                )
                .publishOn(Schedulers.parallel())
                .contextWrite(Context.of(key1, "zidol", key2, "Jihyeok"))
                .subscribe(Logger::onNext);

        TimeUtils.sleep(100L);
    }
}
