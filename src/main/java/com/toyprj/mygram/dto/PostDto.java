package com.toyprj.mygram.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private Long postId;
    @NotNull
    private String nickname;
    @NotNull
    private String title;
    @NotNull
    private String content;
    private Integer likeNumber;

}
