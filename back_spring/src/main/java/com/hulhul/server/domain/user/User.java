package com.hulhul.server.domain.user;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hulhul.server.domain.time.TimeEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Entity
@Table(name = "Users")
public class User extends TimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Entity PK (auto_increment) : GenerationType.IDENTITY)
	private Long id;

	@Column(length = 50, nullable = false, unique = true)
	private String email;
	private String password;
	private String nickname;
	private int score;
	private boolean is_email;
	
	@Builder
	public User(String email, String password, String nickname) {
		this.email = email;
		this.password = password;
		this.nickname = nickname;

		this.score = 0;
		this.is_email = false;
	}
}
