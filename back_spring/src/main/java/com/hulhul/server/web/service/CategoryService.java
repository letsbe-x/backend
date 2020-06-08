package com.hulhul.server.web.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hulhul.server.domain.category.Category;
import com.hulhul.server.domain.category.CategoryRepo;
import com.hulhul.server.domain.post.Post;
import com.hulhul.server.domain.talk.Talk;
import com.hulhul.server.domain.talk.TalkRepo;
import com.hulhul.server.web.dto.PostResponseDto;
import com.hulhul.server.web.dto.TalkResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true) // 데이터의 변경이 없는 읽기 전용 메서드에 사용, 속성 컨텍스트를 플러시 하지 않으므로 약간의 성능 향상(읽기 전용에는 다 적용)
@RequiredArgsConstructor // final, @NonNull이 붙은 필드를 파라미터로 받는 생성자를 만들어주는 에너테이션
public class CategoryService {

	// 객체를 필드주입이 아닌 생성자주입으로 넣는것이 좋다.
	private final CategoryRepo categoryRepo;
	
	private final TalkService talkService;

	public List<Category> getCategoryList() {
		return categoryRepo.findAll();
	}

	public Category getCategory(Long category_id) {
		return categoryRepo.findById(category_id).get();
	}

	public List<PostResponseDto> getPosts(Category category) {
		int postSize = category.getCountOfPosts();

		List<Post> posts = category.getPosts();
		List<PostResponseDto> result = new ArrayList<PostResponseDto>();
		Set<Integer> pickRandomSet = randomPick(postSize);

		for (Integer idx : pickRandomSet) {
			Post post = posts.get(idx);
			List<TalkResponseDto> talks = talkService.getTalkResponseDtoList(post); 
			PostResponseDto temp = PostResponseDto.builder().post(post).build();
			temp.setTalks(talks);
			
			result.add(temp);
		}
		
		System.out.println(result);

//		List<PostResponseDto> result = posts.stream()
//                .map(post -> PostResponseDto.builder().post(post).build())
//                .collect(Collectors.toList());

		return result;
	}

	// 랜덤 최대 12개
	final static int RANDOM_MAX_SIZE = 12;

	public Set<Integer> randomPick(int size) {
		Set<Integer> pickRandomSet = new HashSet<Integer>();

		// 랜덤 시드
		long seed = System.currentTimeMillis();
		Random rand = new Random(seed);

		int requiredSize = (size < RANDOM_MAX_SIZE) ? size : RANDOM_MAX_SIZE;

		// 12개
		while (pickRandomSet.size() != requiredSize) {
			pickRandomSet.add(rand.nextInt(size));
		}
		return pickRandomSet;
	}
}
