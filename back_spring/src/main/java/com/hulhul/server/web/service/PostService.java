package com.hulhul.server.web.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hulhul.server.domain.category.Category;
import com.hulhul.server.domain.post.Post;
import com.hulhul.server.domain.post.PostRepo;
import com.hulhul.server.domain.user.User;
import com.hulhul.server.web.dto.PostRequestDto;
import com.hulhul.server.web.dto.PostResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true) // 데이터의 변경이 없는 읽기 전용 메서드에 사용, 속성 컨텍스트를 플러시 하지 않으므로 약간의 성능 향상(읽기 전용에는 다 적용)
@RequiredArgsConstructor // final, @NonNull이 붙은 필드를 파라미터로 받는 생성자를 만들어주는 에너테이션
public class PostService {
	
	private final PostRepo postRepo;


	
//
//    @Transactional
//    public Long update(Long id, PostsUpdateRequestDto requestDto) {
//        Posts posts = postsRepo.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("해당 포스트가 없습니다. id=" + id));
//
//        posts.update(requestDto.getTitle(), requestDto.getContent());
//
//        return id;
//    }
//
	public Post getPost(Long post_id) {
		return postRepo.findById(post_id).get();
	}
	
	@Transactional
	public Long save(PostRequestDto postDto, User user, Category category) {
		Post post = postDto.toEntity(user, category);
		
		return postRepo.save(post).getId();
	}
	
//	public Long update(PostRequestDto postDto, User user, Category category) {
//		Post post = postDto.toEntity(user, category);
//		
//		return postRepo.save(post).getId();
//	}
	
}
