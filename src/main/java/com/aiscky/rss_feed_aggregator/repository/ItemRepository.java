package com.aiscky.rss_feed_aggregator.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aiscky.rss_feed_aggregator.model.RSSItem;

public interface ItemRepository extends JpaRepository<RSSItem, Long>{
	List<RSSItem> findByIdInOrderByPubDateDesc(List<Long> id);
}
