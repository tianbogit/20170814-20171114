package Aoyou.tianpo;

import io.vertx.core.*;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;

import io.vertx.ext.web.handler.StaticHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.templ.FreeMarkerTemplateEngine;


public class HttpServerVerticle extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpServerVerticle.class);
    private static int portName = 8080;
    private static String CONFIG_Aoyou_QUEUE = "aoyou.tianpo.queue";

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);
        router.post().handler(BodyHandler.create());
        router.get("/").handler(this::indexView);
        router.get("/register").handler(this::registerView);
        router.post("/login").handler(this::indexHandler);
        router.post("/register").handler(this::registerHandler);

        server.requestHandler(router::accept)
                .listen(portName, ar -> {
                    if (ar.succeeded()) {
                        startFuture.complete();
                    } else {
                        LOGGER.error("Could not start a HTTP server", ar.cause());
                        startFuture.fail(ar.cause());
                    }
                });
    }

    // tag::indexView[]
    private final FreeMarkerTemplateEngine templateEngine = FreeMarkerTemplateEngine.create();

    private void indexView(RoutingContext context) {
        templateEngine.render(context, "templates", "/index.ftl", ar -> {
            if (ar.succeeded()) {
                context.response().putHeader("Content-Type", "text/html");
                context.response().end(ar.result());
            } else {
                context.fail(ar.cause());
            }
        });
    }
    // end::indexView[]

    // tag::indexHandler[]
    private void indexHandler(RoutingContext context) {
        DeliveryOptions option = new DeliveryOptions().addHeader("action", "login");
        vertx.eventBus().send(CONFIG_Aoyou_QUEUE,context.getBodyAsJson(),option, reply -> {
            if (reply.succeeded()) {
                context.response().putHeader("Content-Type", "text/plain");
                context.response().end("success");
            } else {
                context.fail(reply.cause());
            }
        });
    }
    // end::indexHandler[]

    private void registerView(RoutingContext context) {
        templateEngine.render(context, "templates", "/register.ftl",
                ar -> {
                    if (ar.succeeded()) {
                        context.response().putHeader("Content-Type", "text/html");
                        context.response().end(ar.result());
                    } else {
                        context.fail(ar.cause());
                    }
                });
    }

    private void registerHandler(RoutingContext context) {
        DeliveryOptions optionUserReg = new DeliveryOptions().addHeader("action", "register_user_reg");
        JsonObject newUser = context.getBodyAsJson();
        /*暂时没做并行处理*/
        context.response().putHeader("Content-Type", "text/plain");
        vertx.eventBus().send(CONFIG_Aoyou_QUEUE, newUser, optionUserReg,
                replyUserReg -> {
                    if (replyUserReg.succeeded()) {

                        DeliveryOptions optionsUserLogin = new DeliveryOptions().addHeader("action", "register_user_login");
                        vertx.eventBus().send(CONFIG_Aoyou_QUEUE, newUser, optionsUserLogin,
                                replyUserLogin -> {
                                    context.response().end(replyUserLogin.succeeded() ? "ok" : "failed");
                                });
                    } else {
                        context.response().end("failed");
                    }
                });
    }
}
