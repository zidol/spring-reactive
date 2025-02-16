package com.zidol.springreactive.example.context.example;

import com.zidol.springreactive.utils.Logger;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

/**
 * Context 활용 예제
 *  - 직교성을 가지는 정보를 표현할 때 주로 사용된다.
 */
public class ContextRealExample01 {
    public static final String HEADER_NAME_AUTH_TOKEN = "authToken";
    public static void main(String[] args) {
        Mono<String> mono =
                postBook(Mono.just(
                        new Book("abcd-23-32-1231"
                                , "Reactor's Bible"
                                ,"Zidol"))
                )
                        .contextWrite(Context.of(HEADER_NAME_AUTH_TOKEN, "egsfsfs.sadaddfsfds"));

        mono.subscribe(Logger::onNext);

    }

    private static Mono<String> postBook(Mono<Book> book) {
        return Mono.zip(book, Mono.deferContextual(ctx -> Mono.just(ctx.get(HEADER_NAME_AUTH_TOKEN))))
                .flatMap(Mono::just)  // 외부 API 서버로 HTTP POST request를 전송한다고 가정
                .flatMap(tuple -> {
                    String response = "POST the book(" + tuple.getT1().getBookName() +
                            "," + tuple.getT1().getAuthor() + ") with token: " +
                            tuple.getT2();
                    return Mono.just(response); // HTTP response를 수신했다고 가정
                });
    }
}
