package com.msa.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;

// record란?
// "데이터 클래스"이며 순수하게 데이터를 보유하기 위한 특수한 종류의 클래스이다.
public record PostDto(

        //@Schema 어노테이션은 API 모델의 속성을 정의하고 문서화하는 데 사용된다.
        // 다시 말해서 요청과 응답에 사용되는 DTO 클래스나 필드에 사용할 수 있다.
        @Schema(description = "게시물 제목", defaultValue = "디폴트 제목")
        String title,

        @Schema(description = "내용")
        String content
) {

}
