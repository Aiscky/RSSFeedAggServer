package com.aiscky.rss_feed_aggregator.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class RSSChannel {

	@Id
	@GeneratedValue
	private long id;
	
	@Column
	private String title;
	
	@Column(columnDefinition="text")
	private String description;
	
	@Column
	private String link;
	
	@Column
	@Temporal(TemporalType.DATE)
	private Date pubDate;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastBuildDate;

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "channel_user", joinColumns = @JoinColumn(name = "channel_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "user_username", referencedColumnName = "username"))
	public List<User> users;
	
	@OneToMany(mappedBy = "channel", cascade = CascadeType.ALL)
	@JacksonXmlElementWrapper(useWrapping = false)
	@JacksonXmlProperty(localName = "item")
	private List<RSSItem> items;
	
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

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public Date getLastBuildDate() {
		return lastBuildDate;
	}

	public void setLastBuildDate(Date lastBuildDate) {
		this.lastBuildDate = lastBuildDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<RSSItem> getItems() {
		return items;
	}

	public void setItems(List<RSSItem> items) {
		this.items = items;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append("{\n");
		stringBuilder.append("\ttitle: ");
		stringBuilder.append(this.title);
		stringBuilder.append("\n\tlink: ");
		stringBuilder.append(this.link);
		stringBuilder.append("\n\tdescription: ");
		stringBuilder.append(this.description);
		stringBuilder.append("\n}");
		
		return stringBuilder.toString();
	}

	public RSSChannel() {
		this.items = new ArrayList<RSSItem>();
		this.users = new ArrayList<User>();
	}
}
