package com.hulhul.server.web.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

@RestController
@RequestMapping("/api/v1/post")
public class PostController {

	@Autowired
	PostService postService;

	@Autowired
	CategoryService categoryService;

	@Transactional(readOnly = true) // 데이터의 변경이 없는 읽기 전용 메서드에 사용, 속성 컨텍스트를 플러시 하지 않으므로 약간의 성능 향상(읽기 전용에는 다 적용)
	@GetMapping("{post_id}")
	public ResponseEntity getPost(@PathVariable Long post_id) {
		Post post = postService.getPost(post_id);

		if (post != null) {
			return ResponseEntity.ok(PostResponseDto.builder().post(post).build());
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	@PostMapping("save")
	public ResponseEntity savePost(@RequestBody PostRequestDto dto, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return null;
		}

		User user = HttpSessionUtils.getUserFormSession(session);
		Category category = categoryService.getCategory(dto.getCategory_id());

		return ResponseEntity.ok(postService.save(dto, user, category));
	}
}
