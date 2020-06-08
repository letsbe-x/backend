package com.hulhul.server.web.service;

import java.util.NoSuchElementException;

import javax.persistence.NoResultException;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hulhul.server.domain.user.UserRepo;
import com.hulhul.server.exception.CUserNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserDetailServiceImpl implements UserDetailsService {

	private final UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String nickName) throws UsernameNotFoundException {
		return userRepo.findByNickname(nickName).orElseThrow(CUserNotFoundException::new);
	}
}