package com.pedro.sistemapedido.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pedro.sistemapedido.model.MyUserDetails;
import com.pedro.sistemapedido.model.User;
import com.pedro.sistemapedido.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService{

	@Autowired
	UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUserName(username);
		
		if(user == null) {
			throw new UsernameNotFoundException("Username ou password não encontrado");
		}
		
		return new MyUserDetails(user);
	}
}
