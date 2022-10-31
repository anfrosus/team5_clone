package com.team5.sparta_clone_5.service;

import com.team5.sparta_clone_5.dto.request.PostReqDto2;
import com.team5.sparta_clone_5.dto.response.CommentResponseDto;
import com.team5.sparta_clone_5.dto.response.GlobalResDto;
import com.team5.sparta_clone_5.dto.response.OnePostResponseDto;
import com.team5.sparta_clone_5.dto.response.PostResponseDto;
import com.team5.sparta_clone_5.exception.CustomException;
import com.team5.sparta_clone_5.exception.ErrorCode;
import com.team5.sparta_clone_5.model.Img;
import com.team5.sparta_clone_5.model.Comment;
import com.team5.sparta_clone_5.model.Member;
import com.team5.sparta_clone_5.model.Post;
import com.team5.sparta_clone_5.repository.CommentRepository;
import com.team5.sparta_clone_5.repository.PostRepository;
import com.team5.sparta_clone_5.s3.S3Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final S3Service s3Service;

    @Transactional
    public GlobalResDto<PostResponseDto> createPost(String postRequestDto, List<MultipartFile> file, Member member){
        List<Img> imgs = new ArrayList<>();
        for (MultipartFile multipartFile : file) {
            String img = s3Service.uploadFile(multipartFile);
            imgs.add()
        }

        Post post = new Post(postRequestDto,member,img);
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
    public GlobalResDto<PostResponseDto> modifyPost(Long postId,MultipartFile file, String contents, Member member){
        Post post = postRepository.findPostByPostIdAndMember(postId,member);
        if (post==null) return GlobalResDto.fail("수정 권한이 없습니다.");
        String img = post.getImg();
        s3Service.deleteFile(img);
        String img2 = s3Service.uploadFile(file);
        post.setContents(contents);
        post.setImg(img2);
        PostResponseDto postResponseDto = new PostResponseDto(postRepository.save(post));
        return GlobalResDto.success(postResponseDto,"수정이 완료 되었습니다.");

    }
}
