package com.fath.revolut.service;

import com.fath.revolut.dao.LocalMoneyTransferDAO;
import com.fath.revolut.model.AccountDto;
import com.fath.revolut.model.ErrorDto;
import com.fath.revolut.model.MoneyTransferDto;
import com.fath.revolut.model.TransactionStatus;

import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class MoneyTransferService {

	private LocalMoneyTransferDAO dao;
	private AccountService accountService;

	private Executor executor;

	private static MoneyTransferService instance = null;

	private MoneyTransferService() {
		this.dao = LocalMoneyTransferDAO.getInstance();
		this.accountService = AccountService.getInstance();

		this.executor = Executors.newFixedThreadPool(ForkJoinPool.getCommonPoolParallelism());
	}

	public static MoneyTransferService getInstance() {
		if (instance == null) {
			instance = new MoneyTransferService();
		}
		return instance;
	}

	public Response create(MoneyTransferDto body) {
		// check parallel
		AccountDto from = accountService.getBy(body.getFrom());
		AccountDto to = accountService.getBy(body.getTo());

		if (from == null || to == null) {
			return Response.status(400)
					.entity(ErrorDto.builder()
							.httpCode(400)
							.message("'from' and 'to' account must be valid.")
							.build())
					.build();
		}

		if (body.getAmount().compareTo(from.getBalance()) > 0) {
			return Response.status(400)
					.entity(ErrorDto.builder()
							.httpCode(400)
							.message("Account does not enough balance for this operation")
							.build())
					.build();
		}

		body.setStatus(TransactionStatus.CREATED);
		dao.persist(body);
		executor.execute(new TransactionTask(body));

		return Response.status(200)
				.entity(body)
				.build();
	}

	public Response getBy(String id) {
		MoneyTransferDto by = dao.getBy(id);
		if (by == null) {
			return Response.status(404)
					.entity(ErrorDto.builder()
							.httpCode(404)
							.message("No transaction found, please check input")
							.build())
					.build();
		}
		return Response.status(200)
				.entity(by)
				.build();
	}

	public Response getAll() {
		Collection<MoneyTransferDto> all = dao.getAll();
		if (all == null || all.isEmpty()) {
			return Response.status(404)
					.entity(ErrorDto.builder()
							.httpCode(404)
							.message("No transaction found, please check input")
							.build())
					.build();
		}
		return Response.status(200)
				.entity(all)
				.build();
	}
}
