package com.hulhul.server.web.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hulhul.server.domain.user.User;
import com.hulhul.server.domain.user.UserRepo;
import com.hulhul.server.web.util.HttpSessionUtils;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	// TODO : Spring Security로 변경
	// TODO : 예외 상황관련해서도 처리 할것
	// TODO : 이메일 확인 관련 Mapping
	@Autowired
	UserRepo userRepo;

	@GetMapping("/loginForm")
	public String loginForm() {
		return "/user/login";
	}

	@PostMapping("/login")
	public String login(String email, String password, HttpSession session) {
		User user = userRepo.findByEmail(email);

		if (user == null) {
			System.out.println("Login Failure !");
			return "redirect:/users/loginForm";
		}

		if (!user.matchPassword(password)) {
			System.out.println("Login Failure !");
			return "redirect:/users/loginForm";
		}

		System.out.println("Login Success !");
		session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);

		return "redirect:/";
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

	@PostMapping("")
	public String create(User user) {
		userRepo.save(user);
		System.out.println(user);
		return "redirect:/users";
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
	public String update(@PathVariable Long id, User updateUser, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}

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