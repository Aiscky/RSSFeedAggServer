package com.aiscky.rss_feed_aggregator.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Type;


@Table(name = "itemuserstate", uniqueConstraints={
	    @UniqueConstraint(columnNames = {"item_id", "user_id"})
}) 
@Entity
public class ItemUserState {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column(columnDefinition = "TINYINT(1)")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean readState;

	@Column(columnDefinition = "TINYINT(1)")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean favorite;
	
	@ManyToOne
	@JoinColumn(name = "item_id")
	RSSItem item;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	User user;

	public boolean isRead() {
		return readState;
	}

	public void setRead(boolean read) {
		this.readState = read;
	}

	public boolean isFavorite() {
		return favorite;
	}

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}

	public RSSItem getItem() {
		return item;
	}

	public void setItem(RSSItem item) {
		this.item = item;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
