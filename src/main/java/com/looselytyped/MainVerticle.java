package com.looselytyped;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.TemplateHandler;
import io.vertx.ext.web.templ.HandlebarsTemplateEngine;

public class MainVerticle extends AbstractVerticle {

  private static final int PORT = 8080;

  @Override
  public void start(Future<Void> startFuture) throws Exception {
    Router router = Router.router(vertx);

    staticHandler(router);
    // Create the HTTP server and pass the "accept" method to the request
    // handler.
    HttpServer server = vertx.createHttpServer();
    server.requestHandler(router::accept);
    server.listen(PORT, result -> {
      if (result.succeeded()) {
        startFuture.complete();
      } else {
        startFuture.fail(result.cause());
      }
    });
  }

  private void staticHandler(Router router) {
    StaticHandler handler = StaticHandler.create();
    router.route().handler(handler);
  }
}
