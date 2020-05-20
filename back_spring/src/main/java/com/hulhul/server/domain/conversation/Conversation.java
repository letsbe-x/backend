package com.hulhul.server.domain.conversation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.hulhul.server.domain.post.Post;
import com.hulhul.server.domain.time.TimeEntity;
import com.hulhul.server.domain.user.User;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Entity
@Table(name = "Conversation")
public class Conversation extends TimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String content;

	private boolean is_privated;
	private int type;

	// Post와의 N:1
	@ManyToOne
	@JoinColumn(name = "post_id", nullable = false, updatable = false)
	private Post post;

	// 유저와의 N:1
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false, updatable = false)
	private User user;

	@Builder
	public Conversation(String content, boolean is_privated, int type, Post post, User user) {
		this.content = content;
		this.is_privated = is_privated;
		this.type = type;
		this.post = post;
		this.user = user;
	}
}
