package com.looselytyped;

import java.net.InetAddress;
import java.net.UnknownHostException;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.TemplateHandler;
import io.vertx.ext.web.templ.handlebars.HandlebarsTemplateEngine;

public class MainVerticle extends AbstractVerticle {

  private static final int PORT = 8080;
  private MongoClient client;

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    setUpMongo();
    // insertSomeData();

    Router router = Router.router(vertx);
    staticHandler(router);
    router.get("/friends.hbs") //
        .handler(this::getFriends) //
        .failureHandler(event -> System.out.println(event.failure()));
    dynamicPages(router);

    vertx.createHttpServer()//
        .requestHandler(router)//
        .listen(PORT, http -> {
          if (http.succeeded()) {
            System.out.println("HTTP server started on port 8888");
            startPromise.complete();
          } else {
            System.out.println(http.cause());
            startPromise.fail(http.cause());
          }
        });
  }

  private void staticHandler(Router router) {
    StaticHandler staticHandler = StaticHandler.create(StaticHandler.DEFAULT_WEB_ROOT);
    staticHandler.setCachingEnabled(false);
    router.route("/assets/*").handler(staticHandler);
  }

  private void dynamicPages(Router router) {
    HandlebarsTemplateEngine hbsEngine = HandlebarsTemplateEngine.create(vertx);
    hbsEngine.setMaxCacheSize(0);
    TemplateHandler templateHandler = TemplateHandler.create(hbsEngine);
    router.getWithRegex(".+\\.hbs").handler(templateHandler);
  }

  private void setUpMongo() {
    // change this when working using ./gradlew run to be mongodb://localhost:27017
    JsonObject mongoconfig = new JsonObject() //
        .put("connection_string", "mongodb://mongo:27017") //
        .put("db_name", "friends");
    client = MongoClient.create(vertx, mongoconfig);
  }

  // @Override
  // public void stop(Promise<Void> stop) {
  // client.dropCollection("friends", r -> {
  // if (r.succeeded()) {
  // System.out.println("SUCCESS: Collection dropped");
  // } else {
  // System.out.println("FAILED: Collection drop");
  // }
  // stop.complete();
  // });
  // }

  private void getFriends(RoutingContext routingContext) {
    JsonObject query = new JsonObject();
    InetAddress ip;
    String hostname;
    try {
      ip = InetAddress.getLocalHost();
      hostname = ip.getHostName();
      System.out.println("Your current IP address : " + ip);
      System.out.println("Your current Hostname : " + hostname);
      routingContext.data().put("hostname", hostname);
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
    client.find("friends", query, res -> {
      routingContext.data().put("friends", res.result());
      routingContext.next();
    });
  }

  private void insertSomeData() {
    JsonObject friend1 = new JsonObject().put("firstName", "Michelle").put("since", "2011");
    JsonObject friend2 = new JsonObject().put("firstName", "Venkat").put("since", "2010");
    JsonObject friend3 = new JsonObject().put("firstName", "Matt").put("since", "2006");

    client.save("friends", friend1, res -> {
      if (res.succeeded()) {
        System.out.println("Saved friend with id " + res.result());
      } else {
        res.cause().printStackTrace();
      }
    });
    client.save("friends", friend2, res -> {
      if (res.succeeded()) {
        System.out.println("Saved friend with id " + res.result());
      } else {
        res.cause().printStackTrace();
      }
    });
    client.save("friends", friend3, res -> {
      if (res.succeeded()) {
        System.out.println("Saved friend with id " + res.result());
      } else {
        res.cause().printStackTrace();
      }
    });
  }
}
