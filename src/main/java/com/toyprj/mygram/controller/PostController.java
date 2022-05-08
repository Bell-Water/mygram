package com.toyprj.mygram.controller;

import com.toyprj.mygram.dto.BoardDto;
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
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/post")
    public Response<BoardDto> getBoardByPageable(
            @RequestParam(value="page", required=false, defaultValue= "0") Integer page,
            @RequestParam(value="query", required=false) String word) {

        System.out.println("query : " + word);

        PageRequest pageRequest = PageRequest
                .of(page, 10)
                .withSort(Sort.Direction.DESC, "id");

        BoardDto board;
        if(word != null) {
            board = postService.getPostWithWord(word, pageRequest);
        } else {
            board = postService.getPost(pageRequest);
        }

        return new Response(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, board);
    }

    @GetMapping("/post/hashtag/{hashtag}")
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

    @GetMapping("/post/user/{nickname}")
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

    /*@PostMapping("/post")
    public Response<BoardDto> craetPost(@RequestBody PostDto postDto) {


    }*/

    /*@GetMapping("/post")
    public void getBoard2() {

    }*/

    /*@PostConstruct
    public void init() {

        for(int i=0; i<500; i++) {
            if(i%3 == 2) {
//                User user = userRepository.getById(1L);
                User user = userRepository.findById(6L).get();
                Post post = Post.builder()
                        .title("title" + i)
                        .content("content" + i)
                        .likenumber(i * 5 + 2)
                        .user(user)
                        .build();

                postRepository.save(post);

                Comment comment = new Comment(0L, "i'll be back" + i * 6, post, user);
                commentRepository.save(comment);
            } else if(i%3 == 1) {
                User user = userRepository.findById(7L).get();
                Post post = Post.builder()
                        .title("제목" + i)
                        .content("내용" + i)
                        .likenumber(i * 5 + 2)
                        .user(user)
                        .build();

                postRepository.save(post);

                Comment comment = new Comment(0L, "내가 왔다감" + i * 6, post,user);
                commentRepository.save(comment);
            } else {
//                User user = userRepository.findById(8L).get();
                User user = userRepository.getById(8L);
                Post post = Post.builder()
                        .title("무제" + i)
                        .content("제곧내" + i)
                        .likenumber(i * 5 + 2)
                        .user(user)
                        .build();

                postRepository.save(post);

                Comment comment = new Comment(0L, "나 아님" + i * 6, post, user);
                commentRepository.save(comment);
            }
        }
    }*/

}
