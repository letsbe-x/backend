package com.hulhul.server.domain.talk;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TalkRepo extends JpaRepository<Talk, Long> {
	// c : create
	// r : read
	// u : update
	// d : delete
	// repository : 저장소
	// <T, ID> : <타입, pk 자료형>
}
