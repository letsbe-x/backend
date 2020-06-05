package com.hulhul.server.web.controller;

import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hulhul.server.domain.user.User;
import com.hulhul.server.domain.user.UserRepo;
import com.hulhul.server.web.service.UserService;
import com.hulhul.server.web.util.HttpSessionUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {

	// TODO : Spring Security로 변경
	// TODO : 예외 상황관련해서도 처리 할것
	// TODO : 이메일 확인 관련 Mapping
	
	private final UserRepo userRepo;
	
	private final UserService userService;
	
	
	@GetMapping("/loginForm")
	public String loginForm() {
		return "/user/login";
	}

	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestBody User user, HttpSession session) throws NoSuchAlgorithmException {
		User userInDB = userService.login(user.getEmail(), user.getPassword());
		session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);

		return new ResponseEntity<User>(userInDB, HttpStatus.OK) ;
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
		return "redirect:/";
	}

	@GetMapping("/form")
	public String form() {
		return "/user/form";
	}

	@PostMapping("/signup")
	public ResponseEntity<String> create(@RequestBody User user) throws NoSuchAlgorithmException {
		Long id = userService.doJoin(user);
		return new ResponseEntity<String>(Long.toString(id), HttpStatus.OK);
	}

	@GetMapping("")
	public String list(Model model) {
		model.addAttribute("users", userRepo.findAll());
		return "/user/list";
	}

	@GetMapping("{id}/form")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}

		User sessionUser = HttpSessionUtils.getUserFormSession(session);

		if (!sessionUser.matchId(id)) {
			throw new IllegalStateException("You can't update another user");
		}

		model.addAttribute("user", userRepo.findById(id));
		return "/user/updateForm";
	}

	@PutMapping("/{id}")
	public String update(@PathVariable Long id,@RequestBody User updateUser, HttpSession session) {
//		if (!HttpSessionUtils.isLoginUser(session)) {
//			return "redirect:/users/loginForm";
//		}

		User sessionUser = HttpSessionUtils.getUserFormSession(session);

		if (!sessionUser.matchId(id)) {
			throw new IllegalStateException("You can't update another user");
		}

		User user = userRepo.findById(id).get();
		user.update(updateUser);
		userRepo.save(user);
		return "redirect:/users";
	}
}