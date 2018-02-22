package com.aiscky.rss_feed_aggregator.test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.aiscky.rss_feed_aggregator.Application;
import com.aiscky.rss_feed_aggregator.model.RSSChannel;
import com.aiscky.rss_feed_aggregator.model.RSSItem;
import com.aiscky.rss_feed_aggregator.repository.RSSChannelRepository;
import com.aiscky.rss_feed_aggregator.repository.RSSItemRepository;
import com.aiscky.rss_feed_aggregator.service.ChannelMapperService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class PersistenceTest {
    
	private final String TESTING_RSS_FILE = "/testing/rss.xml";
	
	private MockMvc mockMvc;
	
	@Autowired
    private WebApplicationContext webApplicationContext;
	
	@Autowired
	ChannelMapperService channelParser;
	
	@Autowired
	RSSChannelRepository channelRepository;

	@Autowired
	RSSItemRepository itemRepository;
	
	@Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        
        itemRepository.deleteAllInBatch();
        channelRepository.deleteAllInBatch();
	}
	
	@Test public void assertingSavedEntityQueryableTitle() throws IOException {
		RSSChannel channel = this.channelParser.mapChannelFromLocalUrl(TESTING_RSS_FILE);
		
		channelRepository.save(channel);
		channel = channelRepository.findByTitle("W3Schools Home Page").get(0);
		
		assertNotEquals(channel, null);
	}

	@Test public void assertingSavedEntityQueryableChannelObj() throws IOException {
		RSSChannel channel = this.channelParser.mapChannelFromLocalUrl(TESTING_RSS_FILE);
		
		for (int i = 0; i < channel.getItems().size(); i++) {
			channel.getItems().get(i).setChannel(channel);
		}
		
		channelRepository.save(channel);
		List<RSSItem> items = itemRepository.findByChannel(channel);
		
		
		assertEquals(2, items.size());
	}
}