package com.hulhul.server.web.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hulhul.server.domain.category.Category;
import com.hulhul.server.domain.category.CategoryRepo;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor // 자동 wired
@RequestMapping("/api/v1/category")
public class CategoryController {

	private final CategoryRepo categoryRepo;
	// TODO : Dto(VO)

	@GetMapping("/list")
	public ResponseEntity getCategoryList() {
		List<Category> all = categoryRepo.findAll();
		for (Category category : all) {
		}
		return ResponseEntity.ok(all);
	}

}
