package com.hulhul.server.domain.repo;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hulhul.server.domain.post.Post;
import com.hulhul.server.domain.post.PostRepo;
import com.hulhul.server.domain.user.User;
import com.hulhul.server.domain.user.UserRepo;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PostTest {

	@Autowired
	PostRepo postRepo;

	@Test
	public void 포스트_저장_불러오기() {
		// set
		String email = "test1";
		String password = "test1";
		String nickname = "test1";

		// set
		String title = "123";
		boolean isPrivated = true;
		boolean isSolved = false;
		User user = new User(email, password, nickname);

		// givien
//		PostRepo.save(Post.builder().title(title).is_privated(isPrivated).is_solved(isSolved).build());

		// when
		List<Post> postList = postRepo.findAll();

		// then
		Post post = postList.get(0);
		assertThat(post.getTitle(), is(title));
		assertThat(post.is_privated(), is(isPrivated));
	}
}