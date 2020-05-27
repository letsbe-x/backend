package com.hulhul.server.domain.post;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.hulhul.server.domain.category.Category;
import com.hulhul.server.domain.talk.Talk;
import com.hulhul.server.domain.time.TimeEntity;
import com.hulhul.server.domain.user.User;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
//@Setter //Entity 클래스에서는 절대 Setter 메소드를 만들지 않는다. (자바빈 인스턴스 값 변경때문에라도...)
@RequiredArgsConstructor
@Entity
@Table(name = "Posts")
public class Post extends TimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Entity PK (auto_increment) : GenerationType.IDENTITY)
	@Column(name = "p_id")
	private Long id;

	// N : 1 post -> user
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "u_id", nullable = false, updatable = false)
	private User user; // 작성자

	// 1 : N post <- Conversation
	// 순서 있어야함
	// ToMany는 default가 지연로딩이므로 설정 안해도 된다.
	// CascadeType.. persist에서 영속성 관련부분 - 참조 객체 !주의
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	private List<Talk> talkList = new ArrayList<>();

	// N : 1 Post -> Category
	// CascadeType.. persist에서 영속성 관련부분 - 참조 객체 !주의
	// The unsaved transient entity must be saved in an operation prior to saving these dependent entities
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "ca_id", nullable = false) // 단방향
	private Category category;

	@Column(length = 500, nullable = false)
	private String title;

//	@Enumerated(EnumType.STRING)
//	private PostStatus status; // POST 상태

	// 연관관계 메서드
	public void addConversation(Talk conversation) {
		talkList.add(conversation);
//		Talk.setPost(this);
	}

	public void setCategory(Category category) {
		this.category = category;
//		category.changePost(this);
	}

	@Builder
	public Post(String title, User user, Category category) {
		this.title = title;
		this.user = user;
		this.setCategory(category);
	}

	// Test용 Lombok toString은 양방향 매핑때문에 무한루프 늪에 빠지더라..
	@Override
	public String toString() {
		return "Post [id=" + id + ", user=" + user + ", TalkList=" + talkList + ", category="
				+ category + ", title=" + title + "]";
	}

}
