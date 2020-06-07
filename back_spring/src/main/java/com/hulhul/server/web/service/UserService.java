package com.hulhul.server.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hulhul.server.domain.user.User;
import com.hulhul.server.domain.user.UserRepo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
	public Long doJoin(User user) throws NoSuchAlgorithmException {
		validateDuplicateEmail(user.getEmail());
		validateDuplicateNickName(user.getNickname()); // 중복 유저 검증
//		System.out.println(user.getPassword());
		user.setPassword(encryptPassword(user.getPassword()));
		userRepo.save(user);
		return user.getId();
	}

	public void validateDuplicateEmail(String email) {
		User findUser = userRepo.findByEmail(email).orElse(new User());
		if (findUser.getEmail() != null) {
			throw new IllegalStateException("이미 존재하는 이메일입니다.");
		}
	}

	public void validateDuplicateNickName(String nickname) {
		User findUsers = userRepo.findByNickname(nickname).orElseGet(User::new);
		System.out.println(findUsers);
		if (findUsers.getId() != null) {
			throw new IllegalStateException("이미 존재하는 닉네임입니다.");
		}
	}

	private String encryptPassword(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(password.getBytes());

		return byteToHex(md.digest());
	}

	private String byteToHex(byte[] input) {
		StringBuilder sb = new StringBuilder();
		for (byte i : input) {
			sb.append(String.format("%02x", i));
		}
		System.out.println(sb.toString());
		return sb.toString();
	}

	public User login(String email, String password) throws NoSuchAlgorithmException {
		User userInDB = userRepo.findByEmail(email).get();
		if (userInDB != null) {
			password = encryptPassword(password);
			if (!checkPassword(password, userInDB.getPassword())) {
				throw new IllegalStateException("아이디 혹은 비밀번호를 확인해 주세요.");
			}
		} else {
			throw new IllegalStateException("아이디 혹은 비밀번호를 확인해 주세요.");
		}

		return userInDB;

	}

	private boolean checkPassword(String password, String comparePassword) {
		return password.equals(comparePassword);
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

	public User findByEmail(String email) {
		return userRepo.findByEmail(email).get();
	}

	public User findByNickname(String nickName) {
		return userRepo.findByNickname(nickName).get();
	}

	public List<User> searchUserByNickName(String nickname) {
		return userRepo.findByNicknameLike(nickname);
	}

	@Transactional
	public void update(long id, User updateUser) {
		User user = userRepo.findById(id).get();
		user.update(updateUser);
	}
}
