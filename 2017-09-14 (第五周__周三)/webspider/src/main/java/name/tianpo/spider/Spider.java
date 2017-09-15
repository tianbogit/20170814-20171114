package name.tianpo.spider;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.templ.FreeMarkerTemplateEngine;

/**
 * spider service
 **/
public class Spider extends AbstractVerticle {
    //监听端口
    private int portName = 8080;
    //创建 WebClient 实例
    private WebClient client;
    //创建模板
    private final FreeMarkerTemplateEngine templateEngine = FreeMarkerTemplateEngine.create();
    //目标地址
    private String targetHost="c.m.163.com";
    private String taegetUrl="/nc/article/headline/T1348654151579/0-20.html";

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        client = WebClient.create(vertx);
        //创建一个 HttpServer
        HttpServer httpServer = vertx.createHttpServer();
        Router router = Router.router(vertx);
        router.get("/").handler(this::indexHandler);
        //开始监听request
        httpServer.requestHandler(router::accept)
                .listen(portName, ar -> {
                    if (ar.succeeded()) {
                        startFuture.complete();
                    } else {
                        startFuture.fail(ar.cause());
                    }
                });

    }

    // Spider 爬取内容view
    private void indexHandler(RoutingContext context) {
        client.get(targetHost, taegetUrl).as(BodyCodec.jsonObject()).send(asyncResult -> {
            if (asyncResult.succeeded()) {
                // 抓取页面数据
                HttpResponse<JsonObject> response = asyncResult.result();
                JsonObject body = response.body();
                JsonArray array = body.getJsonArray("T1348654151579");
                JsonArray titles = new JsonArray();
                for (int i = 0; i < array.size(); i++) {
                    JsonObject item = array.getJsonObject(i);
                    String title = item.getString("title");
                    titles.add(title);
                }
                context.put("titles", titles);
                //把json内容输出到客户端
                templateEngine.render(context,
                        "templates",
                        "/index.ftl",
                        ar -> {
                            if (ar.succeeded()) {
                                context.response().putHeader("Content-Type", "text/html");
                                context.response().end(ar.result());
                            } else {
                                context.fail(ar.cause());
                            }
                        });
            } else {
                // 请求失败
                context.fail(asyncResult.cause());
            }
        });
    }
}



