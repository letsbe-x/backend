package com.hulhul.server.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hulhul.server.domain.category.Category;
import com.hulhul.server.domain.category.CategoryRepo;
import com.hulhul.server.web.service.CategoryService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor // 자동 wired
@RequestMapping("/api/v1/category")
@CrossOrigin("*")
public class CategoryController {

	private final CategoryService categoryService;
	
	private final CategoryRepo categoryRepo;
	// TODO : Dto(VO)

	@GetMapping("/list")
	public ResponseEntity<List> getCategoryList() {
		return ResponseEntity.ok(categoryService.getCategoryList());
	}

	@GetMapping("{category_id}/list")
	public ResponseEntity getPosts(@PathVariable Long category_id) {
		Category category = categoryService.getCategory(category_id);

		// TODO : 랜덤 20개 -> 12개 
		if (category != null) {
			return ResponseEntity.ok(categoryService.getPosts(category));
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	@ApiOperation(value = "카테고리 없으면 post안들어가서 일단 만들어놓은 임시")
	@PostMapping("write")
	public ResponseEntity TestInputCategory(@RequestParam String categoryName) {
		Category category = Category.builder().name(categoryName).build();
		Long id = categoryRepo.save(category).getId();
		return ResponseEntity.ok(id);
	}

}
