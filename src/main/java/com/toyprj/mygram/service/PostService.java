package com.toyprj.mygram.service;

import com.toyprj.mygram.dto.BoardDto;
import com.toyprj.mygram.dto.PostDto;
import com.toyprj.mygram.entity.*;
import com.toyprj.mygram.repository.HashtagPostRepository;
import com.toyprj.mygram.repository.HashtagRepository;
import com.toyprj.mygram.repository.PostRepository;
import com.toyprj.mygram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.IllformedLocaleException;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final HashtagRepository hashtagRepository;
    private final HashtagPostRepository hashtagPostRepository;

    public BoardDto getBoard(Pageable pageable) {

        // 글 작성자, 글 제목, 골 좋아요 수 리턴
        ArrayList<PostDto> postList = new ArrayList<>();
        // 페이지, 사이즈 만큼 가졍괴
        Page<Post> result = postRepository.findAll(pageable);

        result.map(post -> postList.add(
                new PostDto(post.getId(), post.getUser().getNickname(), post.getTitle(), null, post.getLikenumber())
        ));

        return new BoardDto(pageable.getPageNumber(), postList.size(), postList);

    }

    public BoardDto getPostWithWord(String word, Pageable pageable) {
        ArrayList<PostDto> postList = new ArrayList<>();

        Page<Post> result = postRepository.findByTitleContaining(word, pageable);

        result.map( post -> postList.add(
                new PostDto(post.getId(), post.getUser().getNickname(), post.getTitle(), null, post.getLikenumber())
        ));

        return new BoardDto(pageable.getPageNumber(), postList.size(), postList);
    }

    public BoardDto getPostWithHashtag(String hashtag, Pageable pageable) {
        /*
            1. hashtag 테이블에서 string으로 id찾기
            2. hashtag_post 테이블에서 해당 hashtag id로 post id 배열 가져오기
            3. 해당 post id에 해당하는 post 전부 가져오기
         */
//        ArrayList<PostDto> postList = new ArrayList<>();

        Hashtag findHashtag = hashtagRepository.findByName(hashtag)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 hashtag입니다."));

        Page<HashtagPost> hashtagPostList = hashtagPostRepository.findByHashtag(findHashtag, pageable);

        ArrayList<Long> postIdList = new ArrayList<>();

        hashtagPostList.map(hashtagPost -> postIdList.add(hashtagPost.getPost().getId()));

        //확인용
        for(Long a : postIdList) {
            System.out.println("postId : " +  a);
        }

        ArrayList<Post> postList = postRepository.findByIdIn(postIdList);

        ArrayList<PostDto> postDtoList = new ArrayList<>();

        postList.forEach(post -> postDtoList
                .add(new PostDto(post.getId(), post.getUser().getNickname(), post.getTitle(), null, post.getLikenumber())));


        return new BoardDto(pageable.getPageNumber(), postList.size(), postDtoList);
    }

    public BoardDto getPostWithNickname(String nickname, Pageable pageable) {


//        int a[] = new int[3];

        ArrayList<PostDto> postList = new ArrayList<>();

        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 닉네임 입니다."));

        Page<Post> result = postRepository.findByUser(user, pageable);

        result.map(post -> postList.add(
                new PostDto(post.getId(), post.getUser().getNickname(), post.getTitle(), null, post.getLikenumber())
        ));

        return new BoardDto(pageable.getPageNumber(), postList.size(), postList);
    }

    public PostDto getPost(Long postId) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new IllformedLocaleException("존재하지 않는 post입니다."));

        return new PostDto(post.getId(), post.getUser().getNickname(),
                post.getTitle(), post.getContent(), post.getLikenumber());
    }

    public PostDto savePost(User user, PostDto postDto) {

        /*
        1. 양쪽 공백 제거
        2. 해당 해시태그 존재 검사
        3.1 해당 해시태그 존재하면 가져와서 plan이랑 저장
        3.2 해당 해시태그 존재안하면 만들고 plan이랑 저장
        4.
         */

        String[] hashArray = postDto.getContent().split("#");

        Post post = Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .user(user)
                .likenumber(0)
                .build();

        postRepository.save(post);

        for(int i=1; i<hashArray.length; i++) {
            Optional findHashtag = hashtagRepository.findByName(hashArray[i].trim());
            if(findHashtag.isEmpty()) {
                Hashtag ht = new Hashtag(null, hashArray[i].trim(), new ArrayList<>());
                hashtagRepository.save(ht);
                findHashtag = Optional.of(ht);
            }
            Hashtag hashtag = (Hashtag)findHashtag.get();
            HashtagPostId hpi = new HashtagPostId(post.getId(), hashtag.getId());
            HashtagPost hp = new HashtagPost(hpi, post, hashtag);
            //HashtagPost hp = new HashtagPost(null, post, hashtag);
            hashtagPostRepository.save(hp);
            hp.changePost(post);
            hp.changeHashtag(hashtag);
        }

        return new PostDto(post.getId(), post.getUser().getNickname(), post.getTitle(), post.getContent(), post.getLikenumber());
    }

    public Integer updatePost(PostDto postDto, User user) {

        User postUser = userRepository.findByNickname(postDto.getNickname())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저 입니다."));

        if(postUser.getId() != user.getId()) {
            throw new IllegalArgumentException("Post를 작성한 유저가 아닙니다.");
        }

        return postRepository.updatePost(postDto.getPostId(), postDto.getTitle(), postDto.getContent());
    }

    public void deletePost(Long postId, User user) {

        try {
            postRepository.deleteById(postId);
        } catch(RuntimeException e) {
            throw new IllegalArgumentException("post를 삭제하지 못하였습니다.");
        }
    }

    /*public ArrayList<PostDto> getPost(Pageable pageable) {

        // 글 작성자, 글 제목, 골 좋아요 수 리턴
        ArrayList<PostDto> postList = new ArrayList<>();
        // 페이지, 사이즈 만큼 가졍괴
        Page<Post> result = postRepository.findAll(pageable);

        result.map(post -> postList.add(
                new PostDto(post.getId(), post.getUser().getUsername(), post.getTitle(), post.getLikeNumber())
        ));

        return postList;
    }*/

}
