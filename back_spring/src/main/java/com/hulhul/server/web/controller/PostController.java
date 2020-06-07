package com.hulhul.server.web.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hulhul.server.domain.category.Category;
import com.hulhul.server.domain.post.Post;
import com.hulhul.server.domain.user.User;
import com.hulhul.server.web.dto.PostRequestDto;
import com.hulhul.server.web.dto.PostResponseDto;
import com.hulhul.server.web.service.CategoryService;
import com.hulhul.server.web.service.PostService;
import com.hulhul.server.web.util.HttpSessionUtils;

//@RestController
//@RequestMapping("/api/v1/post")
public class PostController {

	@Autowired
	PostService postService;

	@Autowired
	CategoryService categoryService;

	// C
	@PostMapping("write")
	public ResponseEntity savePost(@RequestBody PostRequestDto dto, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return null;
		}

		User user = HttpSessionUtils.getUserFormSession(session);
		Category category = categoryService.getCategory(dto.getCategory_id());

		return ResponseEntity.ok(postService.writePost(dto, user, category));
	}

	// R
	@Transactional(readOnly = true) // 데이터의 변경이 없는 읽기 전용 메서드에 사용, 속성 컨텍스트를 플러시 하지 않으므로 약간의 성능 향상(읽기 전용에는 다 적용)
	@GetMapping("{post_id}")
	public ResponseEntity getPost(@PathVariable Long post_id) {
		//TODO : PostStatus.PRIVATE 일경우 -> 작성자와 해결한 사람만 볼 수 있다.
		
		Post post = postService.getPost(post_id);

		if (post != null) {
			return ResponseEntity.ok(getDto(post));
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	// U
	@PutMapping("{post_id}")
	public ResponseEntity updatePost(@PathVariable Long post_id, @RequestBody PostRequestDto dto, HttpSession session) {
		if (post_id == null) {
			return new ResponseEntity(HttpStatus.NOT_MODIFIED);
			// 잘못된 요청
		}
		User user = HttpSessionUtils.getUserFormSession(session);
		Long user_id = user.getId();
		Category category = categoryService.getCategory(dto.getCategory_id());

		return ResponseEntity.ok(getDto(postService.updatePost(post_id, user_id, dto, category)));
	}

	// D
	@DeleteMapping("{post_id}")
	public ResponseEntity deletePost(@PathVariable Long post_id, HttpSession session) {
		if (post_id == null) {
			return new ResponseEntity(HttpStatus.NOT_MODIFIED);
			// 잘못된 요청
		}
		User user = HttpSessionUtils.getUserFormSession(session);
		Long user_id = user.getId();

		return ResponseEntity.ok(postService.deletePost(post_id, user_id));
	}

	public PostResponseDto getDto(Post post) {
		return PostResponseDto.builder().post(post).build();
	}
	
}
