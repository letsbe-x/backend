package com.hulhul.server.web.controllerV2;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.hulhul.server.domain.user.UserRepo;
import com.hulhul.server.web.dto.PostRequestDto;
import com.hulhul.server.web.dto.PostResponseDto;
import com.hulhul.server.web.security.JwtTokenProvider;
import com.hulhul.server.web.service.CategoryService;
import com.hulhul.server.web.service.PostService;
import com.hulhul.server.web.service.UserService;
import com.hulhul.server.web.util.HttpSessionUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;

@Api(tags = { "V2-Post" })
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/post")
@CrossOrigin("*")
public class V2PostController {

	@Autowired
	UserService userService;

	@Autowired
	PostService postService;

	@Autowired
	CategoryService categoryService;

	private final JwtTokenProvider jwtTokenProvider;

	// C
	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@PostMapping("write")
	public ResponseEntity savePost(@RequestBody PostRequestDto dto) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		System.out.println(email);
		User user = userService.findByEmail(email);
		System.out.println(user);
		Category category = categoryService.getCategory(dto.getCategory_id());

		return ResponseEntity.ok(getDto(postService.writePost(dto, user, category)));
	}

	// R
	@Transactional(readOnly = true) // 데이터의 변경이 없는 읽기 전용 메서드에 사용, 속성 컨텍스트를 플러시 하지 않으므로 약간의 성능 향상(읽기 전용에는 다 적용)
	@GetMapping("{post_id}")
	public ResponseEntity getPost(@PathVariable Long post_id) {
		Post post = postService.getPost(post_id);

		if (post != null) {
			return ResponseEntity.ok(getDto(post));
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	// U
	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@PutMapping("{post_id}")
	public ResponseEntity updatePost(@PathVariable Long post_id, @RequestBody PostRequestDto dto) {
		if (post_id == null) {
			return new ResponseEntity(HttpStatus.NOT_MODIFIED);
			// 잘못된 요청
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		System.out.println(email);
		User user = userService.findByEmail(email);
		System.out.println(user);
		Category category = categoryService.getCategory(dto.getCategory_id());

		return ResponseEntity.ok(getDto(postService.updatePost(post_id, user.getId(), dto, category)));
	}

	// D
	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@DeleteMapping("{post_id}")
	public ResponseEntity deletePost(@PathVariable Long post_id) {
		if (post_id == null) {
			return new ResponseEntity(HttpStatus.NOT_MODIFIED);
			// 잘못된 요청
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		User user = userService.findByEmail(email);

		return ResponseEntity.ok(postService.deletePost(post_id, user.getId()));
	}

	public PostResponseDto getDto(Post post) {
		return PostResponseDto.builder().post(post).build();
	}
}
