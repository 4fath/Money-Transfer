package com.fath.revolut.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
public class MoneyTransferDto {

	private String id;
	private String from;
	private String to;

	private BigDecimal amount;
	private TransactionStatus status;

	public MoneyTransferDto() {
		this.id = UUID.randomUUID().toString();
	}
}
