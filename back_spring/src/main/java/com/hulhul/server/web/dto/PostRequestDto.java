package com.hulhul.server.web.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.hulhul.server.domain.anonymous.AnonymousStatus;
import com.hulhul.server.domain.category.Category;
import com.hulhul.server.domain.post.Post;
import com.hulhul.server.domain.post.PostStatus;
import com.hulhul.server.domain.time.TimeEntity;
import com.hulhul.server.domain.user.User;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class PostRequestDto {
	private Long id;
	private String title;
	private String contents;
	private Long category_id;

	@Enumerated(EnumType.STRING)
	private PostStatus status; // POST 상태

	@Enumerated(EnumType.STRING)
	private AnonymousStatus anonymous; // 유저 익명 상태

	// Save용
	@Builder
	public PostRequestDto(String title, String contents, AnonymousStatus anonymous, Long category_id) {
		this.title = title;
		this.contents = contents;
		this.status = PostStatus.PROCEED;
		this.anonymous = anonymous;
		this.category_id = category_id;
	}

	public Post toEntity(User user, Category category) {
		return Post.builder().title(title).contents(contents).category(category).status(PostStatus.PROCEED)
				.anonymous(this.anonymous).user(user).build();
	}
}
