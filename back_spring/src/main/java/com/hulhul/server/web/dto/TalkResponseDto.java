package com.hulhul.server.web.dto;

import java.time.LocalDateTime;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hulhul.server.domain.anonymous.AnonymousStatus;
import com.hulhul.server.domain.post.Post;
import com.hulhul.server.domain.post.PostStatus;
import com.hulhul.server.domain.talk.Talk;
import com.hulhul.server.domain.time.TimeEntity;
import com.hulhul.server.web.util.AnonymousNickNameUtils;

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
	private Long u_id;
	private String contents;
	private String nickName;

	@Enumerated(EnumType.STRING)
	private AnonymousStatus anonymous; // 유저 익명 상태

	private LocalDateTime createdAt;

	private LocalDateTime modifiedAt;

	private boolean isHost;

	@Builder
	public TalkResponseDto(Talk talk) {
		this.u_id = talk.getUser().getId();
		this.contents = talk.getContents();
		this.anonymous = talk.getAnonymous();
		this.nickName = doAnonymous(talk);
		this.createdAt = talk.getCreatedAt();
		this.modifiedAt = talk.getModifiedAt();
		this.isHost = (talk.getUser().getId() == talk.getPost().getUser().getId()) ? true : false;
	}

	// TODO : 닉네임 변경 - 비공개인경우
	// TODO : HASHSET RECODE
	public String doAnonymous(Talk talk) {
		// talk_id / user_id
		if (talk.getAnonymous() == anonymous) {
			this.u_id = -1L;
			return AnonymousNickNameUtils.getNick(talk.getUser().getId(), talk.getPost().getId());
		} else
			return talk.getUser().getNickname();
	}

//	public String HashNickName(Talk talk) {
//		Long p = talk.getUser().getId();
//		Long q = talk.getPost().getId();
//		
//		
//	}

}
