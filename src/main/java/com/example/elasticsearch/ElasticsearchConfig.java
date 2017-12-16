package com.example.elasticsearch;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.example.elasticsearch.repo")
@ComponentScan(basePackages = { "com.example.elasticsearch..service" })
public class ElasticsearchConfig {

	@Value("${elasticsearch.home:/usr/local/Cellar/elasticsearch/2.3.2}")
	private String elasticsearchHome;

	private static Logger LOGGER = LoggerFactory.getLogger(ElasticsearchConfig.class);

	@Bean
	public ElasticsearchTemplate elasticsearchTemplate() throws UnknownHostException {
	return new ElasticsearchTemplate(client());
	}

	@Bean
	public Client client() throws UnknownHostException {
		Settings settings =Settings.builder().put("cluster.name", "elasticsearch").build();
		PreBuiltTransportClient client2 = new PreBuiltTransportClient(settings);
		client2.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
//		Client client = new PreBuiltTransportClient(settings).addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
	return client2;
	}

//	@Bean
//	public ElasticsearchTemplate elasticsearchTemplate() throws UnknownHostException {
//	return new ElasticsearchTemplate(client());
//	}
//
//	@Bean
//	public Client client() throws UnknownHostException {
//		Settings settings =Settings.builder().put("cluster.name", "elasticsearch").build();
//		Client client = new PreBuiltTransportClient(settings).addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
//	return client;
//	}

//	@Bean
//	public ElasticsearchTemplate elasticsearchTemplate() throws UnknownHostException {
//		return new ElasticsearchTemplate(nodeClient());
//	}
//
//	@Bean
//	public TransportClient nodeClient() throws UnknownHostException {
//		Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
//		TransportClient client = TransportClient.builder().settings(settings).build();
//		client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
//		return client;
//
//	}

	// @Bean
	// public Client client() {
	// NodeClient
	// try {
	// final Path tmpDir =
	// Files.createTempDirectory(Paths.get(System.getProperty("java.io.tmpdir")),
	// "elasticsearch_data");
	// LOGGER.debug(tmpDir.toAbsolutePath().toString());
	//
	//// final Settings.Builder elasticsearchSettings =
	// Settings.settingsBuilder().put("http.enabled", "false")
	//// .put("path.data", tmpDir.toAbsolutePath().toString()).put("path.home",
	// elasticsearchHome);
	//
	// return new
	// NodeBuilder().local(false).clusterName("elasticsearch").node().client();
	// } catch (final IOException ioex) {
	// LOGGER.error("Cannot create temp dir", ioex);
	// throw new RuntimeException();
	// }
	// }
	//
	// @Bean
	// public ElasticsearchOperations elasticsearchTemplate() {
	// return new ElasticsearchTemplate(client());
	//// return new
	// ElasticsearchTemplate(nodeBuilder().local(true).node().client());
	// }
}