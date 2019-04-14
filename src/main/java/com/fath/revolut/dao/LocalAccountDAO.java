package com.fath.revolut.dao;

import com.fath.revolut.model.AccountDto;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LocalAccountDAO implements BaseDAO<AccountDto> {

	private Map<String, AccountDto> accountStore;

	private static LocalAccountDAO instance = null;

	private LocalAccountDAO() {
		accountStore = new ConcurrentHashMap<>();
	}

	public static LocalAccountDAO getInstance(){
		if (instance == null) {
			instance = new LocalAccountDAO();
		}
		return instance;
	}

	@Override
	public AccountDto getBy(String id) {
		return accountStore.get(id);
	}

	@Override
	public AccountDto persist(AccountDto body) {
		accountStore.put(body.getId(), body);
		return body;
	}

	@Override
	public Collection<AccountDto> getAll() {
		return accountStore.values();
	}
}
