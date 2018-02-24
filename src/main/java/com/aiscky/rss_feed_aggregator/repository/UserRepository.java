package com.aiscky.rss_feed_aggregator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aiscky.rss_feed_aggregator.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findOneByUsername(String username);
}
