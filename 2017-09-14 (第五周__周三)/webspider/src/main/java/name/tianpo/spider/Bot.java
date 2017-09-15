package name.tianpo.spider;

import io.vertx.core.*;


public class Bot extends AbstractVerticle {
    @Override
    public void start(Future<Void> startFuture) throws Exception {
        Future<String> dbVerticleDeployment = Future.future();
        vertx.deployVerticle(new Spider(), dbVerticleDeployment.completer());

        dbVerticleDeployment.compose(id -> {
            Future<String> httpVerticleDeployment = Future.future();
            vertx.deployVerticle(
                    "name.tianpo.spider.Spider",
                    new DeploymentOptions().setInstances(2),
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