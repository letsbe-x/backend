package com.hulhul.server.web.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hulhul.server.domain.category.Category;
import com.hulhul.server.domain.post.PostRepo;
import com.hulhul.server.web.dto.PostResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true) // 데이터의 변경이 없는 읽기 전용 메서드에 사용, 속성 컨텍스트를 플러시 하지 않으므로 약간의 성능 향상(읽기 전용에는 다 적용)
@RequiredArgsConstructor // final, @NonNull이 붙은 필드를 파라미터로 받는 생성자를 만들어주는 에너테이션
public class PostService {
	private PostRepo postsRepository;

	// TODO : 익명 글쓰기 인경우에는 HashSet에서 내려주는 dto의 닉네임을 변경한다.
	private PostResponseDto getPost(String CategoryId, String PostId) {
		return null;
	}
	
	
//	@Transactional
//    public Long save(PostsSaveRequestDto requestDto) {
//        return postsRepository.save(requestDto.toEntity()).getId();
//    }
//
//    @Transactional
//    public Long update(Long id, PostsUpdateRequestDto requestDto) {
//        Posts posts = postsRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));
//
//        posts.update(requestDto.getTitle(), requestDto.getContent());
//
//        return id;
//    }
//
//    @Transactional
//    public PostsResponseDto findById(Long id) {
//        Posts posts = postsRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));
//
//        return new PostsResponseDto(posts);
//    }
	
}
