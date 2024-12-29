package com.zidol.springreactive.example.scheduler;

import com.zidol.springreactive.utils.Logger;
import com.zidol.springreactive.utils.TimeUtils;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;

/**
 * Operator 체인에서 publishOn( )이 호출되면 publishOn( ) 호출 이후의 Operator 체인은
 * *** 다음 publisherOn( )을 만나기전까지 *** publishOn( )에서 지정한 Thread에서 실행이 된다.
 */
public class SchedulerOperatorExample03 {
    public static void main(String[] args) {
        // 길이가 10,000인 int 배열 선언
        Integer[] arr = new Integer[100000];

        // 배열에 값 할당(예: 인덱스 + 1로 채우기)
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i + 1;
        }
        List<Integer>  newList = new ArrayList<>();
        Flux.fromArray(arr)
                .doOnNext(data -> Logger.doOnNext("fromArray", data))
                .publishOn(Schedulers.parallel())
                .filter(data -> data > 3)
                .doOnNext(data -> Logger.doOnNext("filter", data))
                .publishOn(Schedulers.parallel())
                .map(data -> data * 10)
                .doOnNext(data -> {
                    Logger.doOnNext("map", data);
                    newList.add(data);
                })
                .subscribe(Logger::onNext);

        TimeUtils.sleep(500L);
    }
}
