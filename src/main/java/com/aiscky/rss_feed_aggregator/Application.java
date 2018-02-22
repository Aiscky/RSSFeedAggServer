package com.aiscky.rss_feed_aggregator;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aiscky.rss_feed_aggregator.service.ChannelMapperService;

@Controller
@EnableAutoConfiguration
@PropertySource(value = "classpath:application.yml")
@ComponentScan({"com.aiscky.rss_feed_aggregator.service", "com.aiscky.rss_feed_aggregator.controller"})
public class Application {

	@Autowired
	ChannelMapperService service;
	
    @RequestMapping("/")
    @ResponseBody
    String home() throws IOException {
//    	service.mapChannelFromLocalUrl("/testing/rss.xml");
    	
        return "Hello World!";
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}