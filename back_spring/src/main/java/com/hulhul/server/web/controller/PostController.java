package com.hulhul.server.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hulhul.server.domain.post.Post;
import com.hulhul.server.web.dto.PostResponseDto;
import com.hulhul.server.web.service.PostService;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {

	@Autowired
	PostService postService;

	// TODO : category에 대한 posts들 가져오기
	@GetMapping("{post_id}")
	public ResponseEntity getPost(@PathVariable Long post_id) {
		Post post = postService.getPost(post_id);

		if (post != null) {
			return ResponseEntity.ok(PostResponseDto.builder().post(post).build());
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}
}
