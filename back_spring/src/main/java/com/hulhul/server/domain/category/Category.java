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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hulhul.server.domain.post.Post;
import com.hulhul.server.domain.user.User;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "Categories", uniqueConstraints = @UniqueConstraint(columnNames = { "name" }))
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ca_id")
	private Long id;

	private String name;
//
	// 1 : N 양방향
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	private List<Post> posts = new ArrayList<Post>();

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
