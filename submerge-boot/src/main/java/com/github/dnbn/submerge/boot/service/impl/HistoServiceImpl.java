package com.github.dnbn.submerge.boot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.dnbn.submerge.api.subtitle.config.SimpleSubConfig;
import com.github.dnbn.submerge.boot.model.MergeHisto;
import com.github.dnbn.submerge.boot.model.MergeHistoItem;
import com.github.dnbn.submerge.boot.pages.bean.model.UserSubConfigBean;
import com.github.dnbn.submerge.boot.service.HistoService;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Service
public class HistoServiceImpl implements HistoService {

	@Value("${histo.enabled}")
	private boolean histoEnabled;
	
	private final DynamoDbEnhancedClient dynamodbClient;

	public HistoServiceImpl(DynamoDbEnhancedClient dynamodbClient) {
		this.dynamodbClient = dynamodbClient;
	}

	@Override
	public void trace(SimpleSubConfig one, SimpleSubConfig two, UserSubConfigBean config) {
		if (histoEnabled) {
			MergeHistoItem item1 = createHistoLine(one, config);
			MergeHistoItem item2 = createHistoLine(two, config);

			MergeHisto histo = new MergeHisto(item1, item2);
			
			DynamoDbTable<MergeHisto> table = dynamodbClient.table("submerge-histo", TableSchema.fromBean(MergeHisto.class));
			table.putItem(histo);
		}
	}

	private static MergeHistoItem createHistoLine(SimpleSubConfig input, UserSubConfigBean config) {

		MergeHistoItem histo = new MergeHistoItem();
		
		histo.setFileName(input.getSub().getFileName());
		histo.setFontName(input.getFontconfig().getName());

		return histo;
	}
}