package com.aiscky.rss_feed_aggregator.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aiscky.rss_feed_aggregator.auth.model.JsonResponse;
import com.aiscky.rss_feed_aggregator.model.RSSChannel;
import com.aiscky.rss_feed_aggregator.repository.RSSChannelRepository;
import com.aiscky.rss_feed_aggregator.service.ChannelMapperService;

@RestController
@RequestMapping("/api/channel")
public class ChannelController {
	
	@Autowired
	ChannelMapperService channelMapper;
	
	@Autowired
	RSSChannelRepository channelRepository;
	
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
		
		try {
			channel = channelMapper.mapChannelFromUrl(link);
		} catch (IOException e) {
			return new JsonResponse(false, String.format(failureFormat, link));
		}
		
		for (int i = 0; i < channel.getItems().size(); i++) {
			channel.getItems().get(i).setChannel(channel);
		}
		channelRepository.save(channel);
		
		return new JsonResponse(true, String.format(successFormat, link));
	}
}
