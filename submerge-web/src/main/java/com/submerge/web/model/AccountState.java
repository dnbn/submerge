package com.submerge.web.model;

public enum AccountState {

	LOCKED(3), DISABLED(2), ENABLED(1);

	private int id;

	AccountState(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static AccountState getById(int id) {

		for (AccountState state : AccountState.values()) {
			if (state.id == id) {
				return state;
			}
		}

		return null;
	}
}
