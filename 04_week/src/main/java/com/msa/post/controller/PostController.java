package com.msa.post.controller;

import java.util.List;
import java.util.Optional;

import com.msa.post.domain.Post;
import com.msa.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.msa.post.dto.PostDto;
import com.msa.post.dto.ResultDto;

// @RestController
// 메소드의 return(반환 결과값)을 문자열(JSON) 형태로 반환합니다.
@RestController
@RequestMapping("/posts")
// @Tag 란?
//api 그룹 설정을 위한 어노테이션입니다.
//name 속성으로 태그의 이름을 설정할 수 있고, description 속성으로 태그에 대한 설명을 추가할 수 있습니다.
//@Tag에 설정된 name이 같은 것 끼리 하나의 api 그룹으로 묶게 됩니다.
@Tag(name = "Post API", description = "Post 기능 API")

// @RequiredArgsConstructor 란?
//Lombok으로 스프링에서 DI(의존성 주입)의 방법 중에 생성자 주입을 임의의 코드없이 자동으로 설정해주는 어노테이션이다.
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("")
    @Operation(summary = "add post", description = "Post 를 추가하는 API")
    public ResultDto<PostDto> addPost(
            @Parameter(name = "dto", description = "post dto")
            @RequestBody PostDto dto) {
        return new ResultDto<>(200, "", postService.addPost(dto.title(), dto.content()).convert2DTO());
    }

    @GetMapping("")
    public ResultDto<List<PostDto>> getPostList() {
        List<PostDto> postDtos = postService.getPostList()
                .stream()
                .map(Post::convert2DTO)
                .toList();

        return new ResultDto<>(200, "ok", postDtos);
    }

    @GetMapping("/{postId}")
    public ResultDto<PostDto> getPost(@PathVariable("postId") long postId) {
        Optional<PostDto> optPost = postService.getPost(postId)
                .map(Post::convert2DTO);

        if (optPost.isEmpty()) {
            throw new IllegalArgumentException("not exist post : %s".formatted(postId));
        } else {
            return new ResultDto<>(200, "ok", optPost.get());
        }

    }

    @DeleteMapping("/{postId}")
    public ResultDto deletePost(@PathVariable("postId") long postId) {
        postService.getPost(postId)
                .ifPresentOrElse(post -> postService.deletePost(postId),
                        () -> {
                            throw new IllegalArgumentException("not exist post : %s".formatted(postId));
                        });

        return new ResultDto<>(200, "ok", null);
    }


}
