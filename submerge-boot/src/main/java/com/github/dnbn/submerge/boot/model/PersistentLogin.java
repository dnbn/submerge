package com.github.dnbn.submerge.boot.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;

@DynamoDbBean
public class PersistentLogin implements Serializable {

	@Serial
	private static final long serialVersionUID = 2620410829429044895L;

	private String series;
	private String login;
	private String token;
	private Instant lastUsed;

	public PersistentLogin() {

	}

	public PersistentLogin(String series, String login, String token, Instant lastUsed) {
		this.series = series;
		this.login = login;
		this.token = token;
		this.lastUsed = lastUsed;
	}

	@DynamoDbPartitionKey
	@DynamoDbAttribute(value = "series")
	public String getSeries() {
		return this.series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	@DynamoDbSecondaryPartitionKey(indexNames = "login-index")
	@DynamoDbAttribute(value = "login")
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@DynamoDbAttribute(value = "token")
	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Instant getLastUsed() {
		return this.lastUsed;
	}

	public void setLastUsed(Instant lastUsed) {
		this.lastUsed = lastUsed;
	}
}
