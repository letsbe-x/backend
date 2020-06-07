package com.hulhul.server.domain.post;

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

import com.hulhul.server.domain.anonymous.AnonymousStatus;
import com.hulhul.server.domain.category.Category;
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
@Table(name = "Posts")
public class Post extends TimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Entity PK (auto_increment) : GenerationType.IDENTITY)
	@Column(name = "p_id")
	private Long id;

	// N : 1 post -> user
	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name = "u_id", nullable = false, updatable = false) // 단방향
	private User user; // 작성자

	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name = "sovled_id") // 단방향
	private User solver; // 작성자

	// 1 : N post <- Conversation
	// 순서 있어야함
	// ToMany는 default가 지연로딩이므로 설정 안해도 된다.
	// CascadeType.. persist에서 영속성 관련부분 - 참조 객체 !주의
//	@JsonIgnore // 양방향 연관관계라면 하나는 해줘야한다. 안그럼 무한루프~
//	@OneToMany(mappedBy = "post", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
//	private List<Talk> talkList = new ArrayList<>();

	// N : 1 Post -> Category
	// CascadeType.. persist에서 영속성 관련부분 - 참조 객체 !주=GenerationType의
	// The unsaved transient entity must be saved in an operation prior to saving
	// these dependent entities
	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name = "ca_id", nullable = false)
	private Category category;

	@Column(length = 500, nullable = false)
	private String title;

	@Column(length = 3000, nullable = false)
	private String contents;

	@Enumerated(EnumType.STRING)
	private PostStatus status; // POST 상태

	@Enumerated(EnumType.STRING)
	private AnonymousStatus anonymous; // 유저 익명 상태

	@Builder
	public Post(String title, String contents, Category category, User user, PostStatus status,
			AnonymousStatus anonymous) {
		this.title = title;
		this.contents = contents;
		this.user = user;
		this.category = category;
		this.status = status;
		this.anonymous = anonymous;
	}

	public Post setUpdate(Category category, String contents, AnonymousStatus anonymous) {
		this.category = category;
		this.contents = contents;
		this.anonymous = anonymous;
		return this;
	}

	public void setSolvedUser(User solver) {
		this.solver = solver;
	}

	// Test용 Lombok toString은 양방향 매핑때문에 무한루프 늪에 빠지더라..
	@Override
	public String toString() {
		return "Post [id=" + id + ", title=" + title + "contents=" + contents + "]";
	}

}
