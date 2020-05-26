package com.hulhul.server.domain.conversation;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hulhul.server.domain.user.User;

public interface ConversationRepo extends JpaRepository<Conversation, Long> {
	// c : create
	// r : read
	// u : update
	// d : delete
	// repository : 저장소
	// <T, ID> : <타입, pk 자료형>
}
