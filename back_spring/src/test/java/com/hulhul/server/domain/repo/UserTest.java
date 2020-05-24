package com.hulhul.server.domain.repo;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.aspectj.lang.annotation.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hulhul.server.domain.user.User;
import com.hulhul.server.domain.user.UserRepo;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserTest {

	@Autowired
	UserRepo userRepo;
	
	@Test
	public void DI_테스트() {
		assertThat(userRepo, is(nullValue()));
	}

	//TODO : 더미데이터 추가하는법 찾아볼것
	@Before(value = "")
	public void 유저_더미_추가() {
		userRepo.save(new User("testA@test.com", "test", "testA"));
		userRepo.save(new User("testB@test.com", "test", "testB"));
		userRepo.save(new User("testC@test.com", "test", "testC"));
		userRepo.save(new User("testD@test.com", "test", "testD"));
	}

	@Test
	public void 유저_저장_불러오기() {
		// set
		String email = "test1";
		String password = "test1";
		String nickname = "test1";

		// given
		Long id = userRepo.save(User.builder().email(email).password(password).nickname(nickname).build()).getId();

		// when
		User user = userRepo.findById(id).get();

		// then
		assertThat(user.getEmail(), is(email));
		assertThat(user.getPassword(), is(password));
	}

	@Test
	public void 유저_검색() {
		// set
		String email = "test1";
		String password = "test1";
		String nickname = "test1";
		
		// given
		Long id = userRepo.save(User.builder().email(email).password(password).nickname(nickname).build()).getId();

		// set
		String query = "test%";

		// when
		List<User> userList = userRepo.findByNicknameLike(query);

		// then
		System.out.println(userList);
		assertThat(userList.size(), is(1));
	}
}