package com.hulhul.server.domain.category;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Entity
@Table(name = "Category")
public class Category {
	@Id
	private Long id;

	@Column(length = 100, nullable = false)
	private String categoryName;

	public Category(Long id, String categoryName) {
		super();
		this.id = id;
		this.categoryName = categoryName;
	}

}
