package com.hulhul.server.domain.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hulhul.server.domain.time.TimeEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Users", uniqueConstraints = @UniqueConstraint(columnNames = { "email", "nickname" }))
public class User extends TimeEntity implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Entity PK (auto_increment) : GenerationType.IDENTITY)
	@Column(name = "u_id")
	private Long id;

	@Column(length = 50, nullable = false, unique = true)
	private String email;
	private String password;
	private String nickname;
	private boolean is_email;

	// ToMany default Value = Fetch.LAZY
	// 1:N
//	@JsonIgnore
//	@OneToMany(mappedBy = "user")
//	private List<Post> posts = new ArrayList<>();

	// ToMany default Value = Fetch.LAZY
	// 1:N
//	@JsonIgnore
//	@OneToMany(mappedBy = "user")
//	private List<Talk> talkList = new ArrayList<>();

	private int score;

//	@Builder
	public User(String email, String password, String nickname) {
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.score = 0;
		this.is_email = false;
	}

//	@Builder
//	public User(String email, String password) {
//		this.email = email;
//		this.password = password;
//	}

//	public void changePost(Post post) {
////		this.posts.add(post);
//	}

	public boolean matchId(Long id) {
		return this.id == id;
	}

//	public boolean matchEmail(String email) {
//		return this.email.equals(email);
//	}

//	public boolean matchPassword(String password) {
//		return this.password.equals(password);
//	}

	public void update(User updateUser) {
		this.password = updateUser.password;
		this.nickname = updateUser.nickname;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" + password + ", nickname=" + nickname
				+ ", is_email=" + is_email + ", score=" + score + "]";
	}

	/*
	 * Security
	 */
	@ElementCollection(fetch = FetchType.EAGER)
	@Builder.Default
	private List<String> roles = new ArrayList<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
	}

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	// TODO : 이메일 확인 추가
}
