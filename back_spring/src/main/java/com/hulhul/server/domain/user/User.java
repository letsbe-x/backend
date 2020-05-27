package com.hulhul.server.domain.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.hulhul.server.domain.post.Post;
import com.hulhul.server.domain.talk.Talk;
import com.hulhul.server.domain.time.TimeEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "Users", uniqueConstraints = @UniqueConstraint(columnNames = { "email", "nickname" }))
public class User extends TimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Entity PK (auto_increment) : GenerationType.IDENTITY)
	@Column(name = "u_id")
	private Long id;

	@Column(length = 50, nullable = false, unique = true)
	private String email;
	private String password;
	private String nickname;
	private boolean is_email;

	// ToMany default Value = Fetch.LAZY
	// 1:N
	@OneToMany(mappedBy = "user")
	private List<Post> posts = new ArrayList<>();

	// ToMany default Value = Fetch.LAZY
	// 1:N
	@OneToMany(mappedBy = "user")
	private List<Talk> talkList = new ArrayList<>();

	private int score;

	@Builder
	public User(String email, String password, String nickname) {
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.score = 0;
		this.is_email = false;
	}

	public void changePost(Post post) {
		this.posts.add(post);
	}

	public boolean matchId(Long id) {
		return this.id == id;
	}

	public boolean matchEmail(String email) {
		return this.email.equals(email);
	}

	public boolean matchPassword(String password) {
		return this.password.equals(password);
	}

	public void update(User updateUser) {
		this.password = updateUser.password;
		this.nickname = updateUser.nickname;
	}

	// TODO : 이메일 확인 추가
}
