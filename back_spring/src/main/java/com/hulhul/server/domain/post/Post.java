package com.hulhul.server.domain.post;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

import com.hulhul.server.domain.conversation.Conversation;
import com.hulhul.server.domain.time.TimeEntity;
import com.hulhul.server.domain.user.User;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Entity
@Table(name = "Posts")
public class Post extends TimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Entity PK (auto_increment) : GenerationType.IDENTITY)
	private Long id;

	@Column(length = 500, nullable = false)
	private String title;

	private boolean is_privated; // 공개여부
	private boolean is_solved; // 해결여부

	// N : 1 post -> user
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false, updatable = false)
	private User user;

	// 1 : N post <- Conversation
	// 순서 있어야함
//	@OneToMany(mappedBy = "Post")
//	private List<Conversation> converstations = new ArrayList<>();

	@Builder
	public Post(String title, boolean is_privated, boolean is_solved, User user) {
		this.title = title;
		this.is_privated = is_privated;
		this.is_solved = is_solved;
		this.user = user;
	}
}
