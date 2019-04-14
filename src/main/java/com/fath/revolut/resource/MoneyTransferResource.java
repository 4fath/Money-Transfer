package com.fath.revolut.resource;

import com.fath.revolut.model.ErrorDto;
import com.fath.revolut.model.MoneyTransferDto;
import com.fath.revolut.service.MoneyTransferService;
import org.apache.log4j.Logger;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/v1/money_transfer")
@Produces(MediaType.APPLICATION_JSON)
public class MoneyTransferResource {

	private static final Logger logger = Logger.getLogger(MoneyTransferResource.class);


	private MoneyTransferService moneyTransferService;

	public MoneyTransferResource() {
		this.moneyTransferService = MoneyTransferService.getInstance();
	}

	@GET
	public Response getAll() {
		return moneyTransferService.getAll();
	}

	@GET
	@Path("/{id}")
	public Response getById(@PathParam("id") String id) {
		if (id == null || id.isEmpty()) {
			logger.info(String.format("Invalid id param:%s | null or empty.",id));
			return Response.status(400)
					.entity(ErrorDto.builder()
							.httpCode(400)
							.message("Must contains a valid id")
							.build())
					.build();
		}
		return moneyTransferService.getBy(id);
	}

	@POST
	public Response create(@NotNull MoneyTransferDto requestDto) {
		if (requestDto.getFrom() == null || requestDto.getTo() == null || requestDto.getAmount() == null) {
			logger.info(String.format("Invalid requestDto from:%s, to:%s, amount:%s | null or empty.",
					requestDto.getFrom(), requestDto.getTo(), requestDto.getAmount()));
			return Response.status(400)
					.entity(ErrorDto.builder()
							.httpCode(400)
							.message("Must contains a valid request.")
							.build())
					.build();
		}
		return moneyTransferService.create(requestDto);
	}
}
