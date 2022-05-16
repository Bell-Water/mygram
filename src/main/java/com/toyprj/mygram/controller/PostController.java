package com.toyprj.mygram.controller;

import com.toyprj.mygram.dto.BoardDto;
import com.toyprj.mygram.dto.PostDto;
import com.toyprj.mygram.entity.Post;
import com.toyprj.mygram.entity.User;
import com.toyprj.mygram.repository.CommentRepository;
import com.toyprj.mygram.repository.PostRepository;
import com.toyprj.mygram.repository.UserRepository;
import com.toyprj.mygram.response.Response;
import com.toyprj.mygram.response.ResponseCode;
import com.toyprj.mygram.response.ResponseMessage;
import com.toyprj.mygram.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

//    public void getBoard(@RequestParam("page") int page,
//                               @RequestParam("size") int size,
//                               @RequestParam(value = "name",required = false) String name) {
//    public void getBoard(Pageable pageable) {
    //    public void getBoardByPageable
//            (@PageableDefault(size = 10, sort="id", direction = Sort.Direction.DESC) Pageable pageable) {

    //메인 화면 - 유저 정보, 글 목록
    @GetMapping("/")
    public Response<BoardDto> main() {

        return new Response(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, new BoardDto());
    }

    @GetMapping("/board/post")
    public Response<BoardDto> getBoardByPageable(
            @RequestParam(value="page", required=false, defaultValue= "0") Integer page,
            @RequestParam(value="word", required=false) String word) {

        System.out.println("query : " + word);

        PageRequest pageRequest = PageRequest
                .of(page, 10)
                .withSort(Sort.Direction.DESC, "id");

        BoardDto board;
        if(word != null) {
            board = postService.getPostWithWord(word, pageRequest);
        } else {
            board = postService.getBoard(pageRequest);
        }

        return new Response(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, board);
    }

    @GetMapping("/board/post/hashtag/{hashtag}")
    public Response<BoardDto> getBoardByWord(
            @PathVariable String hashtag,
            @RequestParam(value="page", required = false, defaultValue = "0") Integer page) {

        System.out.println("hashtag : " + hashtag);
        PageRequest pageRequest = PageRequest
                .of(page, 10)
                .withSort(Sort.Direction.DESC, "Hashtag");

        BoardDto board = postService.getPostWithHashtag(hashtag, pageRequest);

        return new Response(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, board);

    }

    @GetMapping("/board/post/user/{nickname}")
    public Response<BoardDto> getBoardByPageableAndNickname(
            @PathVariable String nickname,
            @RequestParam(value="page", required=false, defaultValue= "0") Integer page) {

        System.out.println("nickname = " + nickname);

        PageRequest pageRequest = PageRequest
                .of(page, 10)
                .withSort(Sort.Direction.DESC, "id");

        BoardDto board = postService.getPostWithNickname(nickname, pageRequest);

        return new Response(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, board);
    }

    @GetMapping("/board/post/{postId}")
    public Response<PostDto> getPost(@PathVariable Long postId) {

        PostDto postDto = postService.getPost(postId);

        return new Response(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, postDto);
    }

    @PostMapping("/board/post")
    public Response<PostDto> createPost(@Valid @RequestBody PostDto postDto , @AuthenticationPrincipal User user) {

        if(user == null) {
            throw new IllegalArgumentException("로그인 해야 됩니다.");
        }

        PostDto boardDto = postService.savePost(user, postDto);

        return new Response(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, boardDto);
    }

    @PutMapping("/board/post")
    public Response<Integer> updatePost(@RequestBody PostDto postDto ,@AuthenticationPrincipal User user) {

        Integer postId = postService.updatePost(postDto, user);
        return new Response(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, postId);
    }

    @DeleteMapping("/board/post")
    public Response deletePost(@RequestBody Map<String, Long> postMap, @AuthenticationPrincipal User user) {

        postService.deletePost(postMap.get("postId"), user);
        return new Response(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }
}
