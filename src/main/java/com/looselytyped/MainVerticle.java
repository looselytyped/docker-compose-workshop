package com.looselytyped;


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
    dynamicPages(router);

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
    StaticHandler staticHandler = StaticHandler.create();
    staticHandler.setCachingEnabled(false);
    router.route("/assets/*").handler(staticHandler);
  }

  private void dynamicPages(Router router) {
    HandlebarsTemplateEngine hbsEngine = HandlebarsTemplateEngine.create();
    hbsEngine.setMaxCacheSize(0);
    TemplateHandler templateHandler = TemplateHandler.create(hbsEngine);
    router.getWithRegex(".+\\.hbs").handler(templateHandler);
  }
}
