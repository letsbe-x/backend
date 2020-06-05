package com.hulhul.server.domain.anonymous;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.swing.event.ChangeEvent;

public class AnonymousEntity {
	@Enumerated(EnumType.STRING)
	private AnonymousStatus anonymous; // 유저 익명 상태
	
	public void setStatus(AnonymousStatus anonymous) {
		this.anonymous = anonymous;
	}
	
	
}
