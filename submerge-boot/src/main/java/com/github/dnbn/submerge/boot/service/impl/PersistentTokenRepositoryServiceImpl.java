package com.github.dnbn.submerge.boot.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.github.dnbn.submerge.boot.model.PersistentLogin;
import com.github.dnbn.submerge.boot.model.User;
import com.github.dnbn.submerge.boot.service.UserService;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

public class PersistentTokenRepositoryServiceImpl implements PersistentTokenRepository {

	@Autowired
	private DynamoDbEnhancedClient dynamodbClient;

	@Autowired
	private transient UserService userService;

	@Override
	public void createNewToken(PersistentRememberMeToken token) {

		PersistentLogin persistentLogin = new PersistentLogin();
		User user = this.userService.findByName(token.getUsername());
		persistentLogin.setSeries(token.getSeries());
		persistentLogin.setToken(token.getTokenValue());
		persistentLogin.setLogin(user.getLogin());
		persistentLogin.setLastUsed(token.getDate().toInstant());

		getTable().putItem(persistentLogin);
	}

	@Override
	public PersistentRememberMeToken getTokenForSeries(String seriesId) {

		PersistentRememberMeToken rememberMeToken = null;
		PersistentLogin persistentLogin = getPersistantLoginsFromSeriesId(seriesId);
		if (persistentLogin != null) {
			rememberMeToken = new PersistentRememberMeToken(persistentLogin.getLogin(), persistentLogin.getSeries(),
					persistentLogin.getToken(), Date.from(persistentLogin.getLastUsed()));
		}
		return rememberMeToken;
	}

	@Override
	public void removeUserTokens(String username) {
		User user = this.userService.findByName(username);
		if (user != null) {
			QueryConditional queryConditional = QueryConditional
					.keyEqualTo(Key.builder()
							.partitionValue(username)
							.build());

			DynamoDbTable<PersistentLogin> table = getTable();
			table.index("login-index")
				.query(queryConditional)
				.stream()
				.flatMap(page -> page.items().stream())
				.forEach(table::deleteItem);
		}
	}

	@Override
	public void updateToken(String seriesId, String tokenValue, Date lastUsed) {

		PersistentLogin persistentLogin = getPersistantLoginsFromSeriesId(seriesId);
		if (persistentLogin != null) {
			persistentLogin.setToken(tokenValue);
			persistentLogin.setLastUsed(lastUsed.toInstant());
			getTable().updateItem(persistentLogin);
		}
	}

	private PersistentLogin getPersistantLoginsFromSeriesId(String seriesId) {
		return getTable().getItem(Key.builder().partitionValue(seriesId).build());
	}

	private DynamoDbTable<PersistentLogin> getTable() {
		return dynamodbClient.table("submerge-logins", TableSchema.fromBean(PersistentLogin.class));
	}

}
