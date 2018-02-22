package com.aiscky.rss_feed_aggregator.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aiscky.rss_feed_aggregator.model.RSSChannel;
import com.aiscky.rss_feed_aggregator.model.RSSItem;

@Repository
public interface RSSItemRepository extends JpaRepository<RSSItem, Long> {
	List<RSSItem> findByTitle(String title);
	List<RSSItem> findByChannel(RSSChannel channel);
}
