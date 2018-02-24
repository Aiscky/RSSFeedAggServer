package com.aiscky.rss_feed_aggregator.auth.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@EnableResourceServer
@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	private final String RESOURCE_ID = "rss_resource";
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID);
    }

	@Override
	public void configure(HttpSecurity http) throws Exception {
		log.debug("WENT INTO MAPPING CONFIGURER RESOURCE SERVER !");
		http
//			.anonymous().and()
			.authorizeRequests()
			.anyRequest().permitAll();
//				.antMatchers("/api/**").authenticated()
			
	}
}
