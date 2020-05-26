package com.hulhul.server.domain.category;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hulhul.server.domain.user.User;

public interface CategoryRepo extends JpaRepository<Category, Long> {
	// c : create
	// r : read
	// u : update
	// d : delete
	// repository : 저장소
	// <T, ID> : <타입, pk 자료형>
	public Category findByName(String name);

}
