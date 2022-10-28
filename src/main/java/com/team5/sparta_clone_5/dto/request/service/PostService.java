package com.team5.sparta_clone_5.dto.request.service;

import com.team5.sparta_clone_5.dto.request.PostRequestDto;
import com.team5.sparta_clone_5.dto.response.GlobalResDto;
import com.team5.sparta_clone_5.dto.response.PostResponseDto;
import com.team5.sparta_clone_5.model.Member;
import com.team5.sparta_clone_5.model.Post;
import com.team5.sparta_clone_5.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public GlobalResDto<PostResponseDto> createPost(PostRequestDto postRequestDto, Member member){
        Post post = new Post(postRequestDto,member,"s");
        String email = post.getMember().getEmail();
        if(member.getEmail().equals(email)){
            post = postRepository.save(post);
            PostResponseDto postResponseDto = new PostResponseDto(post);
            return GlobalResDto.success(postResponseDto,"게시글 작성이 완료 되었습니다.");
        }
        else {
            return GlobalResDto.fail("작성 권한이 없습니다.");
        }
    }
    @Transactional(readOnly = true)
    public GlobalResDto<List<PostResponseDto>> allPost(){
        List<Post> posts = postRepository.findAll();
        List<PostResponseDto> postResponseDtos = new ArrayList<>();
        for (Post post : posts){
            postResponseDtos.add(new PostResponseDto(post));
        }
        return GlobalResDto.success(postResponseDtos,"a");
    }

    @Transactional(readOnly = true)
    public GlobalResDto<PostResponseDto> onePost(Long postId){
        Post post = postRepository.findByPostId(postId);
        PostResponseDto postResponseDto = new PostResponseDto(post);
        return GlobalResDto.success(postResponseDto,"a");
    }

    @Transactional
    public GlobalResDto<PostResponseDto> delPost(Long postId,Member member){
        Post post = postRepository.findPostByPostIdAndMember(postId, member);
        if (post == null) return GlobalResDto.fail("삭제 권한이 없습니다.");
        postRepository.deleteById(post.getPostId());
        return GlobalResDto.success(null,"삭제 되었습니다.");
    }

    @Transactional
    public GlobalResDto<PostResponseDto> modifyPost(Long postId,String contents, Member member){
        Post post = postRepository.findPostByPostIdAndMember(postId,member);
        if (post==null) return GlobalResDto.fail("수정 권한이 없습니다.");
        post.setContents(contents);
        PostResponseDto postResponseDto = new PostResponseDto(postRepository.save(post));
        return GlobalResDto.success(postResponseDto,"수정이 완료 되었습니다.");

    }


}
