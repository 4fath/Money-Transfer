package com.fath.revolut.service;

import com.fath.revolut.dao.LocalAccountDAO;
import com.fath.revolut.model.AccountDto;

import com.fath.revolut.resource.AccountRequest;

import java.util.Collection;

public class AccountService {

	private LocalAccountDAO dao;

	private static AccountService instance = null;

	private AccountService() {
		this.dao = LocalAccountDAO.getInstance();
	}

	public static AccountService getInstance() {
		if (instance == null){
			instance = new AccountService();
		}
		return instance;
	}

	public AccountDto create(AccountRequest request) {
		AccountDto dto = new AccountDto(request.getBalance());
		return dao.persist(dto);
	}

	public AccountDto getBy(String id) {
		return dao.getBy(id);
	}

	public Collection<AccountDto> getAll() {
		return dao.getAll();
	}

}
