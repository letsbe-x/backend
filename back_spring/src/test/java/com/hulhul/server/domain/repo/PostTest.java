package com.hulhul.server.domain.repo;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hulhul.server.domain.anonymous.AnonymousStatus;
import com.hulhul.server.domain.category.Category;
import com.hulhul.server.domain.category.CategoryRepo;
import com.hulhul.server.domain.post.Post;
import com.hulhul.server.domain.post.PostRepo;
import com.hulhul.server.domain.post.PostStatus;
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

//	@Test
	public void DI_테스트() {
		assertThat(postRepo, is(notNullValue()));
		assertThat(userRepo, is(notNullValue()));
		assertThat(categoryRepo, is(notNullValue()));
	}

//	@Test
	public void 포스트_저장_불러오기() {
		// Userset
		String email = "test1@test.com";
		String password = "test1";
		String nickname = "test1";
		User user = User.builder().email(email).password(password).nickname(nickname).build();

		// CategorySet
		String categoryName = "고민";
		Category category = Category.builder().name(categoryName).build();

		// given
		Long u_id = userRepo.save(user).getId();
		Long ca_id = categoryRepo.save(category).getId();

		// Postset
		user = userRepo.findById(u_id).get();
		category = categoryRepo.findById(ca_id).get();

		String title = "포스트 제목 작성";
		String contents = "궁금한 내용 작성";
//		PostStatus status = PostStatus.PROCEED;
		Post post = Post.builder().title(title).contents(contents).category(category).status(PostStatus.PROCEED)
				.anonymous(AnonymousStatus.Anonymous).user(user).build();

		// givien
		Long id = postRepo.save(post).getId();

		// when
//		User checkUser = userRepo.findByEmail(email);
		Category checkCategory = categoryRepo.findByName(categoryName);
		Post checkPost = postRepo.findById(id).get();

		// then
//		assertThat(checkUser.getEmail(), is(email));
		assertThat(checkCategory.getName(), is(categoryName));
		assertThat(checkPost.getTitle(), is(title));
	}

//	@Test
	public void 포스트_저장_불러오기2() {
		// Userset
		User user = userRepo.findById(1L).get();
		Category category = categoryRepo.findById(1L).get();

		String title = "포스트 제목 작성";
		String contents = "궁금한 내용 작성";
//		PostStatus status = PostStatus.PROCEED;
		Post post = Post.builder().title(title).contents(contents).category(category).user(user).build();

		// givien
		Long id = postRepo.save(post).getId();

		// when
//		User checkUser = userRepo.findByEmail(email);
//		Category checkCategory = categoryRepo.findByName(categoryName);
//		Post checkPost = postRepo.findById(id).get();
//
//		// then
//		assertThat(checkUser.getEmail(), is(email));
//		assertThat(checkCategory.getName(), is(categoryName));
//		assertThat(checkPost.getTitle(), is(title));
	}

	@Test
	public void 포스트_불러오기() {
		Post post = postRepo.findById(1L).get();
//		Post post = postRepo.findById(2L).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + 2L));

		System.out.println(post);
	}

}
