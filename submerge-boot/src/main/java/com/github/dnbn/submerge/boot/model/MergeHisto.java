package com.github.dnbn.submerge.boot.model;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class MergeHisto {
	
	private String id;
	private Instant date;
	private MergeHistoItem item1;
	private MergeHistoItem item2;
	
	public MergeHisto() {
		super();
	}

	public MergeHisto(MergeHistoItem item1, MergeHistoItem item2) {
		super();
		this.id = UUID.randomUUID().toString();
		this.date = ZonedDateTime.now(ZoneId.of("Europe/Paris")).toInstant();
		this.item1 = item1;
		this.item2 = item2;
	}

	@DynamoDbPartitionKey
	@DynamoDbAttribute(value = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@DynamoDbAttribute(value = "date")
	public Instant getDate() {
		return date;
	}

	public void setDate(Instant date) {
		this.date = date;
	}

	@DynamoDbAttribute(value = "item1")
	public MergeHistoItem getItem1() {
		return item1;
	}

	public void setItem1(MergeHistoItem item1) {
		this.item1 = item1;
	}

	@DynamoDbAttribute(value = "item2")
	public MergeHistoItem getItem2() {
		return item2;
	}

	public void setItem2(MergeHistoItem item2) {
		this.item2 = item2;
	}
	
	
}
