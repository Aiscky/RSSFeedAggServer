package com.aiscky.rss_feed_aggregator.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aiscky.rss_feed_aggregator.repository.UserRepository;

@Service("userDetailsService")
public class UserService implements UserDetailsService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails userDetails;

		log.debug(username);
		log.error(username);
		
		if ((userDetails = userRepository.findOneByUsername(username)) == null)
			throw new UsernameNotFoundException("User does not exists");
		return userDetails;
	}
}
