package com.aiscky.rss_feed_aggregator.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aiscky.rss_feed_aggregator.model.ItemUserState;
import com.aiscky.rss_feed_aggregator.model.RSSItem;
import com.aiscky.rss_feed_aggregator.model.User;


public interface ItemUserStateRepository extends JpaRepository<ItemUserState, Long> {
	ItemUserState findOneByUserAndItem(User user, RSSItem item);
	List<ItemUserState> findByUser(User user);
}
