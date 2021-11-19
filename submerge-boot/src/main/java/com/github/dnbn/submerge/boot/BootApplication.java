package com.github.dnbn.submerge.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@SpringBootApplication
public class BootApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootApplication.class, args);

	}

	@Bean
	public DynamoDbEnhancedClient dynamodbClient() {
		return DynamoDbEnhancedClient.builder()
		        .dynamoDbClient(DynamoDbClient.builder()
		        		.region(Region.EU_WEST_3)
		        		.build())
		        .build();
	}
}
