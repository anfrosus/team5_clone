package com.team5.sparta_clone_5.service;

import com.team5.sparta_clone_5.dto.request.PostReqDto2;
import com.team5.sparta_clone_5.dto.response.CommentResponseDto;
import com.team5.sparta_clone_5.dto.response.GlobalResDto;
import com.team5.sparta_clone_5.dto.response.OnePostResponseDto;
import com.team5.sparta_clone_5.dto.response.PostResponseDto;
import com.team5.sparta_clone_5.exception.CustomException;
import com.team5.sparta_clone_5.exception.ErrorCode;
import com.team5.sparta_clone_5.model.Comment;
import com.team5.sparta_clone_5.model.Member;
import com.team5.sparta_clone_5.model.Post;
import com.team5.sparta_clone_5.repository.CommentRepository;
import com.team5.sparta_clone_5.repository.PostRepository;

import com.team5.sparta_clone_5.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final S3Uploader s3Uploader;

//    @Transactional
//    public GlobalResDto<PostResponseDto> createPost(PostRequestDto postRequestDto, Member member){
//        Post post = new Post(postRequestDto,member,"s");
//        String email = post.getMember().getEmail();
//        if(member.getEmail().equals(email)){
//            post = postRepository.save(post);
//            PostResponseDto postResponseDto = new PostResponseDto(post);
//            return GlobalResDto.success(postResponseDto,"게시글 작성이 완료 되었습니다.");
//        }
//        else {
//            return GlobalResDto.fail("작성 권한이 없습니다.");
//        }
//    }

    @Transactional
    public GlobalResDto<PostResponseDto> createPost(PostReqDto2 postReqDto2, Member currentMember) throws IOException {
        if (postReqDto2.getImg() == null) throw new CustomException("이미지를 찾을 수 없습니다.", ErrorCode.NotFound);
        String imgUrl = s3Uploader.uploadFiles(postReqDto2.getImg(), "mydir");
        Post post = Post.builder().img(imgUrl).contents(postReqDto2.getContent()).member(currentMember).build();
        postRepository.save(post);
        return GlobalResDto.success(new PostResponseDto(post), "작성 완료");
    }

    @Transactional(readOnly = true)
    public GlobalResDto<List<PostResponseDto>> allPost(){
        List<Post> posts = postRepository.findAll();
        List<PostResponseDto> postResponseDtos = new ArrayList<>();
        for (Post post : posts){
            postResponseDtos.add(new PostResponseDto(post));
        }
        return GlobalResDto.success(postResponseDtos,"조회성공");
    }

    @Transactional(readOnly = true)
    public GlobalResDto<OnePostResponseDto> onePost(Long postId){
        Post post = postRepository.findByPostId(postId).orElseThrow(() -> new CustomException("글 조회", ErrorCode.NotFound));
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        for (Comment comment : post.getCommentList()){
            commentResponseDtoList.add(new CommentResponseDto(comment));
        }
        OnePostResponseDto onePostResponseDto = new OnePostResponseDto(post, commentResponseDtoList);
        return GlobalResDto.success(onePostResponseDto,"조회 성공");
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
