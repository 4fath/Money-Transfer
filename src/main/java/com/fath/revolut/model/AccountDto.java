package com.fath.revolut.model;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

public class AccountDto {

	private String id;
	private BigDecimal balance;

	public AccountDto(BigDecimal balance) {
		this.id = UUID.randomUUID().toString();
		this.balance = balance.setScale(2, RoundingMode.HALF_UP);
	}

	public String getId() {
		return id;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
}
