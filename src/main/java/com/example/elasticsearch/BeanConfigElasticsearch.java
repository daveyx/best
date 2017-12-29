package com.example.elasticsearch;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.example.service")
public class BeanConfigElasticsearch {

	@Bean
	public IAdminElasticsearchService adminElasticsearchService() {
		return new AdminElasticsearchService();
	}

	@Bean
	public IElasticsearchAccessService elasticsearchAccessService() {
		return new ElasticsearchAccessService();
	}

}
