package com.aiscky.rss_feed_aggregator.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RSSDocument {

	private String version;
	private RSSChannel channel;
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public RSSChannel getChannel() {
		return channel;
	}
	public void setChannel(RSSChannel channel) {
		this.channel = channel;
	}
}
