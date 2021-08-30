package com.koreait.day4.model.network;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Header<T> {

    // api 통신시간
    private LocalDateTime transactionTime;

    // api 응답코드
    private String resultCode;

    // api 설명
    private String description;

    // 실제 데이터를 담아줌
    private T data;

    private Pagination pagination;


    // Ok에 대한 응답
   public static <T> Header<T> OK(){
        return (Header<T>) Header.builder()
                .transactionTime(LocalDateTime.now())   // 현재 시간
                .resultCode("OK")   // OK
                .description("OK")  // OK
                .build();
    }


    // Data가 리턴될 메소드
    public static <T> Header<T> OK(T data){
       return (Header<T>) Header.builder()
               .transactionTime(LocalDateTime.now())   // 현재 시간
               .resultCode("OK")   // OK
               .description("OK")  // OK
               .data(data)
               .build();
    }


    // error
    public static <T> Header<T> ERROR(String description){  // 뭐때문에 에러가 났는지 설명
       return (Header<T>) Header.builder()
               .transactionTime(LocalDateTime.now())   // 현재 시간
               .resultCode("ERROR")
               .description("ERROR")
               .build();
    }

    public static <T> Header<T> OK(T data, Pagination pagination){
        return (Header<T>)Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("OK")
                .description("OK")
                .data(data)
                .pagination(pagination)
                .build();
    }

}
