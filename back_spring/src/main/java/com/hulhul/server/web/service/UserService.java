package com.hulhul.server.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hulhul.server.domain.user.User;
import com.hulhul.server.domain.user.UserRepo;

import java.util.List;

@Service
@Transactional(readOnly = true) // 데이터의 변경이 없는 읽기 전용 메서드에 사용, 속성 컨텍스트를 플러시 하지 않으므로 약간의 성능 향상(읽기 전용에는 다 적용)
@RequiredArgsConstructor // final, @NonNull이 붙은 필드를 파라미터로 받는 생성자를 만들어주는 에너테이션
public class UserService {

	// 객체를 필드주입이 아닌 생성자주입으로 넣는것이 좋다.
	private final UserRepo userRepo; // final 키워드를 추가하면 컴파일 시점에 Repo설정하지 않는 오류 확

	/**
	 * 회원가입
	 */
	@Transactional // 변경
	public Long doJoin(User user) {
		validateDuplicateUser(user); // 중복 유저 검증
		userRepo.save(user);
		return user.getId();
	}

	private void validateDuplicateUser(User user) {
		List<User> findUsers = userRepo.findByNickname(user.getNickname());
		if (!findUsers.isEmpty()) {
			throw new IllegalStateException("이미 존재하는 닉네임입니다.");
		}
	}

	/**
	 * 전체 회원 조회
	 */
	public List<User> findMembers() {
		return userRepo.findAll();
	}

	public User findOne(Long id) {
		return userRepo.findById(id).get();
	}

	@Transactional
	public void update(long id, User updateUser) {
		User user = userRepo.findById(id).get();
		user.update(updateUser);
	}
}
