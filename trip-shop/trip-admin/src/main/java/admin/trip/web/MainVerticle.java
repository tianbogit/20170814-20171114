package admin.trip.web;

import admin.trip.web.db.TripDbVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;

/**
 * 创建并发布MainVerticle
 * @author tianpo
 */
public class MainVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) throws Exception {

        Future<String> dbVerticleDeployment = Future.future();
        vertx.deployVerticle(new TripDbVerticle(), dbVerticleDeployment.completer());

        dbVerticleDeployment.compose(id -> {
            Future<String> httpVerticleDeployment = Future.future();
            vertx.deployVerticle( "admin.trip.web.http.HttpServerVerticle",
                    new DeploymentOptions().setInstances(1),
                    httpVerticleDeployment.completer());
            return httpVerticleDeployment;
        }).setHandler(ar -> {
            if (ar.succeeded()) {
                startFuture.complete();
            } else {
                startFuture.fail(ar.cause());
            }
        });
    }
}