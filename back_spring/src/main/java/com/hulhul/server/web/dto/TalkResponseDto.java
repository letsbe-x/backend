package com.hulhul.server.web.dto;

import java.time.LocalDateTime;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.hulhul.server.domain.anonymous.AnonymousStatus;
import com.hulhul.server.domain.post.Post;
import com.hulhul.server.domain.post.PostStatus;
import com.hulhul.server.domain.talk.Talk;
import com.hulhul.server.domain.time.TimeEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TalkResponseDto {
	private Long id;
	private String contents;
	private String nickName;

	@Enumerated(EnumType.STRING)
	private AnonymousStatus anonymous; // 유저 익명 상태
	
	
	private LocalDateTime createdAt;
	
	private LocalDateTime modifiedAt;

	// TODO : 닉네임 변경 - 비공개인경우
	@Builder
	public TalkResponseDto(Talk talk) {
		this.id = talk.getId();
		this.contents = talk.getContents();
		this.anonymous = talk.getAnonymous();
		this.nickName = doAnonymous(talk);
		this.createdAt = talk.getCreatedAt();
		this.modifiedAt = talk.getModifiedAt();
	}

	// TODO : HASHSET RECODE
	public String doAnonymous(Talk talk) {
		// talk_id / user_id
		if (talk.getAnonymous() == anonymous)
			return "익명";
		else
			return talk.getUser().getNickname();
	}

}
