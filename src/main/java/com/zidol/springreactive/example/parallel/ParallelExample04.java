package com.zidol.springreactive.example.parallel;

import com.zidol.springreactive.utils.Logger;
import com.zidol.springreactive.utils.TimeUtils;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class ParallelExample04 {
    public static void main(String[] args) {
        Flux.fromArray(new Integer[]{1, 3, 5, 7, 9, 11, 13, 15})
                .parallel(4)
                .runOn(Schedulers.parallel())
                .subscribe(Logger::onNext);
        TimeUtils.sleep(100L);

    }
}
