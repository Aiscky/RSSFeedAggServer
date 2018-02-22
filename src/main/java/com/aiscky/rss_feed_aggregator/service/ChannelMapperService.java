package com.aiscky.rss_feed_aggregator.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.springframework.stereotype.Service;

import com.aiscky.rss_feed_aggregator.model.RSSChannel;
import com.aiscky.rss_feed_aggregator.model.RSSDocument;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

@Service
public class ChannelMapperService {
	public RSSChannel mapChannelFromUrl(String url) throws IOException {
		InputStream inputStream = new URL(url).openStream();
		
		return this.mapChannelFromXmlInput(inputStream);
	}
	
	public RSSChannel mapChannelFromLocalUrl(String localUri) throws IOException {
		InputStream inputStream = this.getClass().getResourceAsStream(localUri);
		
		return this.mapChannelFromXmlInput(inputStream);
	}
	
	public RSSChannel mapChannelFromXmlInput(InputStream inputStream) throws IOException {
		
		XmlMapper xmlMapper = new XmlMapper();
		
		RSSDocument doc = xmlMapper.readValue(inputStream, RSSDocument.class);
		
		if (doc == null)
			return null;
		return doc.getChannel();
	}
}
