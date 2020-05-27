package com.hulhul.server.domain.repo;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hulhul.server.domain.category.Category;
import com.hulhul.server.domain.category.CategoryRepo;
import com.hulhul.server.domain.post.Post;
import com.hulhul.server.domain.post.PostRepo;
import com.hulhul.server.domain.talk.Talk;
import com.hulhul.server.domain.talk.TalkRepo;
import com.hulhul.server.domain.user.User;
import com.hulhul.server.domain.user.UserRepo;

@RunWith(SpringRunner.class)
@SpringBootTest
//@Transactional
public class TalkTest {

	@Autowired
	CategoryRepo categoryRepo;

	@Autowired
	UserRepo userRepo;

	@Autowired
	PostRepo postRepo;

	@Autowired
	TalkRepo talkRepo;

	@Test
	public void DI_테스트() {
		assertThat(postRepo, is(notNullValue()));
		assertThat(userRepo, is(notNullValue()));
		assertThat(categoryRepo, is(notNullValue()));
		assertThat(talkRepo, is(notNullValue()));
	}
	
	@Test
	public void 대화_저장_불러오기() {
		// Userset
		String email = "test1@test.com";
		String password = "test1";
		String nickname = "test1";
		User user = User.builder().email(email).password(password).nickname(nickname).build();
		
		//LAZY 문제;;; User가 안만들어졌네;;!
		
		// CategorySet
		String categoryName = "고민";
		Category category = Category.builder().name(categoryName).build();

		// PostSet
		String title = "123";
//		PostStatus status = PostStatus.PROCEED;
		Post post = Post.builder().title(title).category(category).user(user).build();

		// TalkSet
		String content = "content";
		Talk talk = Talk.builder().content(content).user(user).post(post).build();

		// givien
		Long id = talkRepo.save(talk).getId();

		// when
		Talk checkTalk = talkRepo.findById(id).get();

		// then
		assertThat(checkTalk.getContent(), is(content));
		assertThat(checkTalk.getUser().getEmail(), is(email));
		assertThat(checkTalk.getPost().getTitle(), is(title));
	}

}
