package com.hulhul.server.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hulhul.server.domain.category.Category;
import com.hulhul.server.domain.post.Post;
import com.hulhul.server.domain.post.PostRepo;
import com.hulhul.server.domain.post.PostStatus;
import com.hulhul.server.domain.user.User;
import com.hulhul.server.web.dto.PostRequestDto;
import com.hulhul.server.web.dto.PostResponseDto;
import com.hulhul.server.web.dto.TalkResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true) // 데이터의 변경이 없는 읽기 전용 메서드에 사용, 속성 컨텍스트를 플러시 하지 않으므로 약간의 성능 향상(읽기 전용에는 다 적용)
@RequiredArgsConstructor // final, @NonNull이 붙은 필드를 파라미터로 받는 생성자를 만들어주는 에너테이션
public class PostService {

	private final PostRepo postRepo;
	
	private final TalkService talkService;
	public Post getPost(Long post_id) {
		return postRepo.findById(post_id).get();
	}

	public List<PostResponseDto> getUserPost(User solver, Integer pageNum, Integer pageSize, PostStatus status) {
		PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize, Sort.by("modified_at").descending());
		List<Post> posts = postRepo.findPostAnswerList(solver.getId(), status.toString(), pageRequest);
		List<PostResponseDto> result = new ArrayList<PostResponseDto>();
		for (Post post : posts) {
			PostResponseDto dto = getDto(post);
			dto.setTalks(talkService.getTalkResponseDtoList(post));
			result.add(dto);
		}
		return result;
	}

	public Integer getUserPostSize(User solver, Integer pageNum, Integer pageSize, PostStatus status) {
		Integer totalSize = postRepo.findPostAnswerListSize(solver.getId(), status.toString());
		Integer result = ((totalSize - 1) / pageSize) + 1;
		return result;
	}

	@Transactional
	public Post writePost(PostRequestDto postDto, User user, Category category) {
		Post post = postDto.toEntity(user, category);

		return postRepo.save(post);
	}

	@Transactional
	public Post updatePost(Long post_id, Long user_id, PostRequestDto postDto, Category category) {
		Post post = postRepo.findById(postDto.getId()).get();
		User originUser = post.getUser();
		if (doMatchUser(originUser, user_id)) {
			return null;
			// 권한 없음
		}
		post.setUpdate(category, post.getContents(), post.getAnonymous());

		return post;
	}

	@Transactional
	public boolean deletePost(Long post_id, Long user_id) {
		Post post = getPost(post_id);
		User originUser = post.getUser();

		if (doMatchUser(originUser, user_id)) {
			return false;
			// 권한 없음
		}

		postRepo.delete(post);
		return true;
	}

	public boolean doMatchUser(User user, Long user_id) {
		return !user.getId().equals(user_id);
	}

	public PostResponseDto getDto(Post post) {
		return PostResponseDto.builder().post(post).build();
	}

}
