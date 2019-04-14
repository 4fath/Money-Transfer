package com.fath.revolut.dao;

import com.fath.revolut.model.MoneyTransferDto;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LocalMoneyTransferDAO implements BaseDAO<MoneyTransferDto> {

	private Map<String, MoneyTransferDto> transactionStore;

	private static LocalMoneyTransferDAO instance = null;

	private LocalMoneyTransferDAO() {
		transactionStore = new ConcurrentHashMap<>();
	}

	public static LocalMoneyTransferDAO getInstance(){
		if (instance == null) {
			instance = new LocalMoneyTransferDAO();
		}
		return instance;
	}

	@Override
	public MoneyTransferDto getBy(String id) {
		return transactionStore.get(id);
	}

	@Override
	public MoneyTransferDto persist(MoneyTransferDto body) {
		transactionStore.put(body.getId(), body);
		return body;
	}

	@Override
	public Collection<MoneyTransferDto> getAll() {
		return transactionStore.values();
	}
}
