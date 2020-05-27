package com.hulhul.server.domain.talk;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "Talks")
public class Talk extends TimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "c_id")
	private Long id;

//	@Lob	//LOB(Large Object)
	@Column(columnDefinition = "TEXT", nullable = false)
	private String content;

//	@Enumerated(EnumType.STRING)
//	private ContentType type; // fileType;

	// 유저와의 N:1
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "u_id", nullable = false)
	private User user;
	
	// Post와의 N:1
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "p_id", nullable = false)
	private Post post;


	@Builder
	public Talk(String content, Post post, User user) {
		this.content = content;
		this.user = user;
		this.post = post;
	}

	// Test용 Lombok toString은 양방향 매핑때문에 무한루프 늪에 빠지더라..
	@Override
	public String toString() {
		return "Talk [c_id=" + id + ", content=" + content + ", post=" + post + ", user=" + user + "]";
	}

}
