package com.hulhul.server.web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hulhul.server.domain.category.Category;
import com.hulhul.server.domain.category.CategoryRepo;
import com.hulhul.server.domain.post.Post;
import com.hulhul.server.web.dto.PostResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true) // 데이터의 변경이 없는 읽기 전용 메서드에 사용, 속성 컨텍스트를 플러시 하지 않으므로 약간의 성능 향상(읽기 전용에는 다 적용)
@RequiredArgsConstructor // final, @NonNull이 붙은 필드를 파라미터로 받는 생성자를 만들어주는 에너테이션
public class CategoryService {

	// 객체를 필드주입이 아닌 생성자주입으로 넣는것이 좋다.
	private final CategoryRepo categoryRepo;

	public List<Category> getCategoryList() {
		return categoryRepo.findAll();
	}
	
	public Category getCategory(Long category_id) {
		return categoryRepo.findById(category_id).get(); 
	}
	
	public List<Post> doRandomPickUp(int postSize, int maxSize){
		//TODO : 랜덤 최대 20개
		List<Post> result = new ArrayList<Post>();
		
		return result;
	}
	
	public List<PostResponseDto> getPosts(Category category){
		List<Post> posts = category.getPosts();
		List<PostResponseDto> result = new ArrayList<PostResponseDto>();
		
		for(Post post : posts) {
			result.add(PostResponseDto.builder().post(post).build());
		}
		
//		List<PostResponseDto> result = posts.stream()
//                .map(post -> PostResponseDto.builder().post(post).build())
//                .collect(Collectors.toList());
		
		return result;
	}
}
