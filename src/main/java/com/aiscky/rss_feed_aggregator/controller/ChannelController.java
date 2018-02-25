package com.aiscky.rss_feed_aggregator.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aiscky.rss_feed_aggregator.auth.model.JsonResponse;
import com.aiscky.rss_feed_aggregator.model.ItemUserState;
import com.aiscky.rss_feed_aggregator.model.RSSChannel;
import com.aiscky.rss_feed_aggregator.model.User;
import com.aiscky.rss_feed_aggregator.repository.ItemUserStateRepository;
import com.aiscky.rss_feed_aggregator.repository.RSSChannelRepository;
import com.aiscky.rss_feed_aggregator.service.ChannelMapperService;

@RestController
@RequestMapping("/api/channel")
public class ChannelController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	ChannelMapperService channelMapper;
	
	@Autowired
	RSSChannelRepository channelRepository;
	
	@Autowired
	ItemUserStateRepository itemUserRepository;
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<RSSChannel> getAllChannels() {
		return channelRepository.findAll();
	}
	
	@RequestMapping("/{channel_id}")
	@ResponseBody
	public RSSChannel getChannel(@PathVariable String channel_id) {
		Long id = Long.parseLong(channel_id);
		
		RSSChannel channel = channelRepository.findOne(id);
		
		return channel;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse addChannelFeed(@RequestParam String link) {
		String successFormat = "%s successfully added";
		String failureFormat = "%s does not exists or is not valid";
		RSSChannel channel;
		
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		try {
			channel = channelMapper.mapChannelFromUrl(link);
		} catch (IOException e) {
			return new JsonResponse(false, String.format(failureFormat, link));
		}
		
		List<ItemUserState> itemUserStates = new ArrayList<ItemUserState>();
		for (int i = 0; i < channel.getItems().size(); i++) {
			ItemUserState itemUserState = new ItemUserState();
			
			itemUserState.setItem(channel.getItems().get(i));
			itemUserState.setUser(user);
			itemUserState.setRead(false);
			itemUserState.setFavorite(false);
			
			itemUserStates.add(itemUserState);
			
			channel.getItems().get(i).setChannel(channel);
		}
		List<User> users = channel.getUsers();
		users.add(user);
		
		channel.setUsers(users);
		channelRepository.save(channel);
		itemUserRepository.save(itemUserStates);
		
		return new JsonResponse(true, String.format(successFormat, link));
	}
}
