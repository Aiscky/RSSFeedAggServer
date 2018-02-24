package com.aiscky.rss_feed_aggregator.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aiscky.rss_feed_aggregator.auth.model.JsonResponse;
import com.aiscky.rss_feed_aggregator.model.User;
import com.aiscky.rss_feed_aggregator.repository.UserRepository;

@RestController
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public JsonResponse registration(@Valid @RequestBody User user) {
		userRepository.save(user);
		return new JsonResponse(true, "User succesfully registered");
	}
}
