package com.toyprj.mygram.service;

import com.toyprj.mygram.dto.BoardDto;
import com.toyprj.mygram.dto.PostDto;
import com.toyprj.mygram.entity.Hashtag;
import com.toyprj.mygram.entity.HashtagPost;
import com.toyprj.mygram.entity.Post;
import com.toyprj.mygram.entity.User;
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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Transactional
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final HashtagRepository hashtagRepository;
    private final HashtagPostRepository hashtagPostRepository;

    public BoardDto getPost(Pageable pageable) {

        // 글 작성자, 글 제목, 골 좋아요 수 리턴
        ArrayList<PostDto> postList = new ArrayList<>();
        // 페이지, 사이즈 만큼 가졍괴
        Page<Post> result = postRepository.findAll(pageable);

        result.map(post -> postList.add(
                new PostDto(post.getId(), post.getUser().getNickname(), post.getTitle(), post.getLikenumber())
        ));

        return new BoardDto(pageable.getPageNumber(), postList.size(), postList);

    }

    public BoardDto getPostWithWord(String word, Pageable pageable) {
        ArrayList<PostDto> postList = new ArrayList<>();

        Page<Post> result = postRepository.findByTitleContaining(word, pageable);

        result.map( post -> postList.add(
                new PostDto(post.getId(), post.getUser().getNickname(), post.getTitle(), post.getLikenumber())
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
                .add(new PostDto(post.getId(), post.getUser().getNickname(), post.getTitle(), post.getLikenumber())));


        return new BoardDto(pageable.getPageNumber(), postList.size(), postDtoList);
    }

    public BoardDto getPostWithNickname(String nickname, Pageable pageable) {


//        int a[] = new int[3];

        ArrayList<PostDto> postList = new ArrayList<>();

        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 닉네임 입니다."));

        Page<Post> result = postRepository.findByUser(user, pageable);

        result.map(post -> postList.add(
                new PostDto(post.getId(), post.getUser().getNickname(), post.getTitle(), post.getLikenumber())
        ));

        return new BoardDto(pageable.getPageNumber(), postList.size(), postList);
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
