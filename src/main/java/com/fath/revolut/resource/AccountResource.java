package com.fath.revolut.resource;

import com.fath.revolut.model.AccountDto;
import com.fath.revolut.model.ErrorDto;
import com.fath.revolut.service.AccountService;
import io.vertx.core.json.Json;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.Collection;

@Path("/api/v1/account")
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {

	private AccountService accountService;

	public AccountResource() {
		this.accountService = AccountService.getInstance();
	}

	@GET
	public Response getAccounts() {
		Collection<AccountDto> accounts = accountService.getAll();
		if (! accounts.isEmpty()) {
			return Response.status(200)
					.entity(Json.encodePrettily(accounts))
					.build();
		}
		return Response.status(404)
				.entity(Json.encodePrettily(ErrorDto.builder()
						.httpCode(404)
						.message("no account found").build()))
				.build();
	}

	@GET
	@Path("/{id}")
	public Response getAccount(@PathParam("id") String id) {
		AccountDto by = accountService.getBy(id);
		if (by != null) {
			return Response.status(200)
					.entity(Json.encodePrettily(by))
					.build();
		}
		return Response.status(404)
				.entity(Json.encodePrettily(ErrorDto.builder()
						.httpCode(404)
						.message("no account related with id")
						.build()))
				.build();
	}

	@POST
	public Response addAccount(@NotNull AccountRequest request) {
		if (request.getBalance() == null || request.getBalance().compareTo(BigDecimal.ZERO) <= 0) {
			return Response.status(400)
					.entity(Json.encodePrettily(ErrorDto.builder()
							.httpCode(400)
							.message("Request must contains a valid \"balance\" value.")
							.build()))
					.build();
		}
		AccountDto dto = accountService.create(request);
		return Response.status(200)
				.entity(Json.encodePrettily(dto))
				.build();
	}

}
