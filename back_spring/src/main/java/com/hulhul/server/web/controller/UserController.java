package com.hulhul.server.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hulhul.server.domain.user.UserRepo;

@RestController
@RequestMapping("/user")
public class UserController {

	//TODO : Spring Security로 변경 
	@Autowired
	UserRepo userRepo;

	@GetMapping("/login")
	public ResponseEntity login() {
		return ResponseEntity.ok("일단 테스트");
	}

	@GetMapping("/logout")
	public ResponseEntity logout() {
		return ResponseEntity.ok("일단 테스트");
	}

	@RequestMapping("/sign")
	public ResponseEntity sign() {
		return ResponseEntity.ok("일단 테스트");
	}
}