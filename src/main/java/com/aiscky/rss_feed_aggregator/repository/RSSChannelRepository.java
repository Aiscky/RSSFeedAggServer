package com.aiscky.rss_feed_aggregator.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aiscky.rss_feed_aggregator.model.RSSChannel;

@Repository
public interface RSSChannelRepository extends JpaRepository<RSSChannel, Long> {
	List<RSSChannel> findByTitle(String title);
	RSSChannel findOneByLink(String link);
}
