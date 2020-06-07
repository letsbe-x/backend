package com.hulhul.server.domain.category;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Formula;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hulhul.server.domain.post.Post;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "categories", uniqueConstraints = @UniqueConstraint(columnNames = { "name" }))
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ca_id")
	private Long id;

	private String name;
//
	// 1 : N 양방향
	@JsonIgnore // 양방향 연관관계라면 하나는 해줘야한다. 안그럼 무한루프~
	@OneToMany(mappedBy = "category", cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	private List<Post> posts = new ArrayList<Post>();

	@JsonIgnore
	@Formula("(select count(*) from posts p where p.ca_id = ca_id)")
	private int countOfPosts;

	@Builder
	public Category(String name) {
		this.name = name;
	}

	// Test용 Lombok toString은 양방향 매핑때문에 무한루프 늪에 빠지더라..
	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + "]";
	}

}
