package com.hulhul.server.web.controllerV2;

import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.hibernate.annotations.Parameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hulhul.server.domain.post.Post;
import com.hulhul.server.domain.post.PostStatus;
import com.hulhul.server.domain.user.User;
import com.hulhul.server.domain.user.UserRepo;
import com.hulhul.server.web.dto.PagenationDto;
import com.hulhul.server.web.dto.PostResponseDto;
import com.hulhul.server.web.security.JwtTokenProvider;
import com.hulhul.server.web.service.PostService;
import com.hulhul.server.web.service.UserService;
import com.hulhul.server.web.util.AnonymousNickNameUtils;
import com.hulhul.server.web.util.HttpSessionUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(tags = { "V2-User" })
@RestController
@RequestMapping("/api/v2/user")
@RequiredArgsConstructor
@CrossOrigin("*")
public class V2UserController {

	// TODO : 예외 상황관련해서도 처리 할것
	// TODO : 이메일 확인 관련 Mapping

	private final UserRepo userRepo;

	private final UserService userService;

	private final PostService postService;

	// JWT 토큰 발급
	private final JwtTokenProvider jwtTokenProvider;

	@ApiOperation(value = "로그인")
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody User inputUser) throws NoSuchAlgorithmException {
		try {
			User user = userService.login(inputUser.getEmail(), inputUser.getPassword());
			String jwt = jwtTokenProvider.createToken(user);
			return new ResponseEntity(jwt, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getStackTrace(), HttpStatus.EXPECTATION_FAILED);
		}
	}

	@ApiOperation(value = "회원가입")
	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody User inputUser) throws NoSuchAlgorithmException {
		User user = User.builder().email(inputUser.getEmail()).nickname(inputUser.getNickname())
				.password(inputUser.getPassword()).roles(Collections.singletonList("ROLE_USER")).build(); // 회원 가입 후
																											// USER_ROLE
																											// 진행
		Long id = userService.doJoin(user);
		return new ResponseEntity<String>(Long.toString(id), HttpStatus.OK);
	}

	@ApiOperation(value = "회원정보수정")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@PutMapping("")
	public ResponseEntity update(@RequestParam Long user_id, @RequestBody User updateUser) {
		User user = userRepo.findById(user_id).get();
		user.update(updateUser);
		userRepo.save(user);

		return new ResponseEntity(user_id, HttpStatus.OK);
	}

	@ApiOperation(value = "닉네임검색")
	@GetMapping("/search/{nickname}")
	public ResponseEntity<List<User>> searchUserByNickName(@PathVariable String nickname) {
		List<User> users = userService.searchUserByNickName("%" + nickname + "%");

		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	@ApiOperation(value = "이메일중복체크")
	@GetMapping("/duplication/email/{email}")
	public ResponseEntity<String> validateDuplicateEmail(@PathVariable String email) {
		userService.validateDuplicateEmail(email);
		return new ResponseEntity<String>("Possible", HttpStatus.OK);
	}

	@ApiOperation(value = "닉네임중복체크")
	@GetMapping("/duplication/nickname/{nickname}")
	public ResponseEntity<String> validateDuplicateNickName(@PathVariable String nickname) {
		userService.validateDuplicateNickName(nickname);
		return new ResponseEntity<String>("Possible", HttpStatus.OK);
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@GetMapping("/mytree/totalSize")
	public ResponseEntity getMyTreeTotalSize(@RequestParam Integer pageNum, @RequestParam Integer requiredSize) {
		User solver = getUser();

		Integer result = postService.getUserSolvedPostSize(solver, pageNum, requiredSize, PostStatus.SOLVED);

		return new ResponseEntity(result, HttpStatus.OK);
	}

	@ApiOperation(value = "닉네임의 해결나무..")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@GetMapping("/mytree")
	public ResponseEntity getMyTree(@RequestParam Integer pageNum, @RequestParam Integer requiredSize) {
		User solver = getUser();

		List<PostResponseDto> posts = postService.getUserSolvedPost(solver, pageNum, requiredSize, PostStatus.SOLVED);

		PagenationDto page = new PagenationDto();
		Integer totalPage = postService.getUserSolvedPostSize(solver, pageNum, requiredSize, PostStatus.SOLVED);
		page.setPage(pageNum);
		page.setRequiredSize(requiredSize);
		page.setPost(posts);
		page.setTotalPage(totalPage);
		return new ResponseEntity(page, HttpStatus.OK);
	}

	@ApiOperation(value = "닉네임의 해결나무..")
	@GetMapping("{nickname}/mytree")
	public ResponseEntity getYourTree(@PathVariable String nickname, @RequestParam Integer pageNum,
			@RequestParam Integer requiredSize) {
		User users = userService.findByNickname(nickname);
		List<PostResponseDto> posts = postService.getUserSolvedPost(users, pageNum, requiredSize, PostStatus.SOLVED);

		PagenationDto page = new PagenationDto();
		Integer totalPage = postService.getUserSolvedPostSize(users, pageNum, requiredSize, PostStatus.SOLVED);
		page.setPage(pageNum);
		page.setRequiredSize(requiredSize);
		page.setPost(posts);
		page.setTotalPage(totalPage);
		return new ResponseEntity(page, HttpStatus.OK);
	}

	@ApiOperation(value = "나의 고민들..")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@GetMapping("/myPost")
	public ResponseEntity getMyPosts(@RequestParam Integer pageNum, @RequestParam Integer requiredSize) {
		User user = getUser();

		List<PostResponseDto> posts = postService.getUserPost(user, pageNum, requiredSize);

		PagenationDto page = new PagenationDto();
		Integer totalPage = postService.getUserPostSize(user, pageNum, requiredSize);
		page.setPage(pageNum);
		page.setRequiredSize(requiredSize);
		page.setPost(posts);
		page.setTotalPage(totalPage);
		return new ResponseEntity(page, HttpStatus.OK);
	}

	@ApiOperation(value = "그방의 닉네임 호출")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@GetMapping("/anonymous")
	public ResponseEntity getAnonymousNickname(@RequestParam Long post_id) {
		User user = getUser();
		Map<String, Object> results = new HashMap<String, Object>();
		//익명 닉네임
		String nickname = AnonymousNickNameUtils.getNick(user.getId(), post_id);
		results.put("nickname", nickname);
		Post post = postService.getPost(post_id);
		results.put("host", user.getId() == post.getUser().getId());
		return new ResponseEntity(results, HttpStatus.OK);
	}

//	@PageableDefault(sort = { "id" }, direction = Direction.DESC, size = 2) Pageable pageable

	private User getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String nickName = authentication.getName();
		User user = userService.findByNickname(nickName);
		return user;
	}

}