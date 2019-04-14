package com.fath.revolut;

import com.fath.revolut.resource.AccountResource;
import com.fath.revolut.resource.MoneyTransferResource;
import io.vertx.core.Vertx;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.server.vertx.VertxRequestHandler;
import org.jboss.resteasy.plugins.server.vertx.VertxResteasyDeployment;

public class App {

	private static final Logger logger = Logger.getLogger(App.class);

	public static void main(String[] args) {

		BasicConfigurator.configure();

		Vertx vertx = Vertx.vertx();
		VertxResteasyDeployment deployment = new VertxResteasyDeployment();
		deployment.start();

		deployment.getRegistry().addPerInstanceResource(MoneyTransferResource.class);
		deployment.getRegistry().addPerInstanceResource(AccountResource.class);

		// Start the front end server using the Jax-RS controller
		vertx.createHttpServer()
				.requestHandler(new VertxRequestHandler(vertx, deployment))
				.listen(8080, ar-> logger.info("Server started on port "+ ar.result().actualPort()));
	}
}
