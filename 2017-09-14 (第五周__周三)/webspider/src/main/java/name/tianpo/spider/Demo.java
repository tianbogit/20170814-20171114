package name.tianpo.spider;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;

/**
 *
 * a simple demo
 **/
public class Demo {
    public void SpiderDemo(){
        WebClient client = WebClient.create(Vertx.vertx());
        client.get(80, "www.xiaohongshu.com", "/")
                .send(ar -> {
                    if (ar.succeeded()) {
                        // Obtain response
                        HttpResponse<Buffer> response = ar.result();

                        System.out.println("Received response with status code" + response.statusCode());
                    } else {
                        System.out.println("Something went wrong " + ar.cause().getMessage());
                    }
                });
    }
}
