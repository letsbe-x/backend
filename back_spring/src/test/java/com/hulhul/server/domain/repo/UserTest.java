package com.hulhul.server.domain.repo;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

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
public class UserTest {

	@Autowired
	UserRepo userRepo;

	@Test
	public void 유저_저장_불러오기() {
		// set
		String email = "test";
		String password = "test";
		String nickname = "test";

		// given
		userRepo.save(User.builder().email(email).password(password).nickname(nickname).build());

		// when
		List<User> userList = userRepo.findAll();

		// then
		User user = userList.get(0);
		assertThat(user.getEmail(), is(email));
		assertThat(user.getPassword(), is(password));
	}
}