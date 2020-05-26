package com.hulhul.server.domain.repo;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hulhul.server.domain.category.Category;
import com.hulhul.server.domain.category.CategoryRepo;
import com.hulhul.server.domain.post.Post;
import com.hulhul.server.domain.post.PostRepo;
import com.hulhul.server.domain.user.User;
import com.hulhul.server.domain.user.UserRepo;

@RunWith(SpringRunner.class)
@SpringBootTest
//@Transactional
public class PostTest {

	@Autowired
	CategoryRepo categoryRepo;

	@Autowired
	UserRepo userRepo;

	@Autowired
	PostRepo postRepo;

//	@After
//	public void cleanup() {
//		postRepo.deleteAll();
//	}

	@Test
	public void DI_테스트() {
		assertThat(postRepo, is(notNullValue()));
	}

	@Test
	public void 포스트_저장_불러오기() {
		// Userset
		String email = "test1@test.com";
		String password = "test1";
		String nickname = "test1";
		User user = new User(email, password, nickname);

		// CategorySet
		String categoryName = "고민";
		Category category = new Category(categoryName);

		// Postset
		String title = "123";
//		PostStatus status = PostStatus.PROCEED;
		Post post = Post.builder().title(title).category(category).user(user).build();

		// givien
		Long id = postRepo.save(post).getId();

		// when
		User checkUser = userRepo.findByEmail(email);
		Category checkCategory = categoryRepo.findByName(categoryName);
		Post checkPost = postRepo.findById(id).get();

		// then
		assertThat(checkUser.getEmail(), is(email));
		assertThat(checkCategory.getName(), is(categoryName));
		assertThat(checkPost.getTitle(), is(title));
	}

}
