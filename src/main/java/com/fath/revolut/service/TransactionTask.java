package com.fath.revolut.service;

import com.fath.revolut.dao.LocalAccountDAO;
import com.fath.revolut.dao.LocalMoneyTransferDAO;
import com.fath.revolut.model.AccountDto;
import com.fath.revolut.model.MoneyTransferDto;
import com.fath.revolut.model.TransactionStatus;

import java.math.BigDecimal;

public class TransactionTask implements Runnable  {

	private LocalMoneyTransferDAO localMoneyTransferDAO;
	private LocalAccountDAO localAccountDAO;
	private MoneyTransferDto dto;

	TransactionTask(MoneyTransferDto moneyTransferDto) {
		this.dto = moneyTransferDto;
		this.localAccountDAO = LocalAccountDAO.getInstance();
		this.localMoneyTransferDAO = LocalMoneyTransferDAO.getInstance();
	}

	@Override
	public void run() {
		MoneyTransferDto by = localMoneyTransferDAO.getBy(dto.getId());

		if (TransactionStatus.CREATED.equals(by.getStatus())) {
			by.setStatus(TransactionStatus.PROCESSING);

			AccountDto fromAcc = localAccountDAO.getBy(dto.getFrom());
			AccountDto toAcc = localAccountDAO.getBy(dto.getTo());
			BigDecimal transferAmount = dto.getAmount();

			if (transferAmount.compareTo(fromAcc.getBalance()) < 0) {
				fromAcc.setBalance(fromAcc.getBalance().subtract(dto.getAmount()));
				toAcc.setBalance(toAcc.getBalance().add(dto.getAmount()));

				by.setStatus(TransactionStatus.COMMITTED);
			}else {
				by.setStatus(TransactionStatus.NOT_ENOUGH_BALANCE);
			}
		}
	}
}
