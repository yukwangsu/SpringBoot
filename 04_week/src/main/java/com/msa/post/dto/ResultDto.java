package com.msa.post.dto;

// record란?
// "데이터 클래스"이며 순수하게 데이터를 보유하기 위한 특수한 종류의 클래스이다.
public record ResultDto<T>(
        int code,
        String message,
        T data
) {

}
