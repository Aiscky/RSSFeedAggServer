package com.aiscky.rss_feed_aggregator.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class RSSItem {

	@Id
	@GeneratedValue
	private long id;
	
	@Column
	private String title;

	@Column
	private String link;
	
	@Column(columnDefinition="text")
	private String description;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "rss_channel_id")
	private RSSChannel channel;

	@Column
	@Temporal(TemporalType.DATE)
	private Date pubDate;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public RSSChannel getChannel() {
		return channel;
	}

	public void setChannel(RSSChannel channel) {
		this.channel = channel;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}
}
