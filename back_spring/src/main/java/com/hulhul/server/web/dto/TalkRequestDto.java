package com.hulhul.server.web.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.hulhul.server.domain.anonymous.AnonymousStatus;
import com.hulhul.server.domain.category.Category;
import com.hulhul.server.domain.post.Post;
import com.hulhul.server.domain.post.PostStatus;
import com.hulhul.server.domain.talk.Talk;
import com.hulhul.server.domain.time.TimeEntity;
import com.hulhul.server.domain.user.User;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class TalkRequestDto {
	private Long id;
	private String contents;

	@Enumerated(EnumType.STRING)
	private AnonymousStatus anonymous; // 유저 익명 상태

	// Save용
	@Builder
	public TalkRequestDto(String contents, AnonymousStatus anonymous) {
		this.contents = contents;
		this.anonymous = anonymous;
	}

	public Talk toEntity(User user) {
		return Talk.builder().contents(contents).anonymous(AnonymousStatus.Anonymous).user(user).build();
	}
}
