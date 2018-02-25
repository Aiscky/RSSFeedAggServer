package com.aiscky.rss_feed_aggregator.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
import com.aiscky.rss_feed_aggregator.model.RSSItem;
import com.aiscky.rss_feed_aggregator.model.User;
import com.aiscky.rss_feed_aggregator.repository.ItemRepository;
import com.aiscky.rss_feed_aggregator.repository.ItemUserStateRepository;

@RestController
@RequestMapping("/api/item")
public class ItemController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	ItemUserStateRepository itemUserRepository;

	@Autowired
	ItemRepository itemRepository;
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<RSSItem> getAllItems(@RequestParam(required=false) Boolean read, @RequestParam(required=false) Boolean favorite) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		List<Long> idList = new ArrayList<Long>();
		List<RSSItem> itemList = new ArrayList<RSSItem>();
		List<ItemUserState> itemUserStates = itemUserRepository.findByUser(user);
		
		if (read != null) {
			itemUserStates = itemUserStates.stream().
				    filter(p -> (p.isRead() == read.booleanValue())).
				    collect(Collectors.toList());
		}
		
		if (favorite != null) {
			itemUserStates = itemUserStates.stream().
				    filter(p -> (p.isFavorite() == favorite.booleanValue())).
				    collect(Collectors.toList());
		}		
		
		itemUserStates.forEach((temp) -> {
			itemList.add(temp.getItem());
		});
		
		Collections.sort(itemList, new Comparator<RSSItem>() {
		    @Override
		    public int compare(RSSItem o1, RSSItem o2) {
		        return o2.getPubDate().compareTo(o1.getPubDate());
		    }
		});
		
		return itemList;
	}
	
	@RequestMapping(value = "/{id}/read", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse setReadState(@PathVariable long id, @RequestParam Boolean read) {
		String successFormat = "%s successfully read";
		String failureFormat = "%s is not is user channel or doesn't exists";
		
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		ItemUserState itemUser = itemUserRepository.findOneByUserAndItem(user, itemRepository.findOne(id));
		itemUser.setRead(read);
		itemUserRepository.save(itemUser);
		
		return new JsonResponse(true, String.format(successFormat, Long.toString(id)));
	}
	
	@RequestMapping(value = "/{id}/favorite", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse setFavoriteState(@PathVariable long id, @RequestParam Boolean favorite) {
		String successFormat = "%s successfully favorite";
		String failureFormat = "%s is not is user channel or doesn't exists";
		
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		ItemUserState itemUser = itemUserRepository.findOneByUserAndItem(user, itemRepository.findOne(id));
		itemUser.setFavorite(favorite);
		itemUserRepository.save(itemUser);		
		return new JsonResponse(true, String.format(successFormat, Long.toString(id)));
	}
	
	@RequestMapping(value = "/{id}/read", method = RequestMethod.GET)
	@ResponseBody
	public String getReadState(@PathVariable long id) {
		String responseFormat = "{\"read\" : \"%s\"}";
		
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Boolean read = itemUserRepository.findOneByUserAndItem(user, itemRepository.findOne(id)).isRead();
		
		return String.format(responseFormat, read.toString());
	}
	
	@RequestMapping(value = "/{id}/favorite", method = RequestMethod.GET)
	@ResponseBody
	public String getFavoriteState(@PathVariable long id) {
		String responseFormat = "{\"favorite\" : \"%s\"}";
		
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Boolean favorite = itemUserRepository.findOneByUserAndItem(user, itemRepository.findOne(id)).isFavorite();
		
		return String.format(responseFormat, favorite.toString());
	}
}
