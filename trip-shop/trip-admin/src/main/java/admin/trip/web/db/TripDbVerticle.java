package admin.trip.web.db;

import admin.trip.web.db.Enums.TripToDb;
import admin.trip.web.db.TicketDal.TripTicketDal;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.asyncsql.AsyncSQLClient;
import io.vertx.ext.asyncsql.MySQLClient;
import io.vertx.serviceproxy.ProxyHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

/**
 * 创建TripDbVerticle
 * @author tianpo
 */
public class TripDbVerticle extends AbstractVerticle {
    private static final Logger LOGGER =  LoggerFactory.getLogger(TripDbVerticle.class);
    private AsyncSQLClient client;

    public static final String CONFIG_TICKETDB_QUEUE = "ticket.queue";

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        HashMap<TripToDb, String> sqlQueries = loadSqlQueries();
        //执行数据库
        JsonObject mySQLClientConfig = new JsonObject()
                .put("host", "101.200.154.139")
                .put("port", 3306)
                .put("maxPoolSize", 20)
                .put("username", "root")
                .put("password", "123456")
                .put("database", "trip");
        client = MySQLClient.createShared(vertx, mySQLClientConfig);
        //返回实现类参数
        Future petFuture = Future.future();
        TripTicketDal.create(client, sqlQueries, ready -> {
            if (ready.succeeded()) {
                ProxyHelper.registerService(TripTicketDal.class, vertx, ready.result(), CONFIG_TICKETDB_QUEUE); // <1>
                petFuture.complete();
                //    startFuture.complete();
            } else {
                //    startFuture.fail(ready.cause());
                petFuture.fail(ready.cause());
            }
        });
    }

    private HashMap<TripToDb, String> loadSqlQueries() throws IOException {
        //获取数据库文件信息
        InputStream queryInputStream = getClass().getResourceAsStream("/TripSql.properties");
        Properties properties = new Properties();
        try {
            //把获取的文件信息放入新建的文件对象
            properties.load(queryInputStream);
            queryInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        HashMap<TripToDb, String> sqlQueries = new HashMap<>();

        //查询taobao全部商品条数
        sqlQueries.put(TripToDb.Get_Ticket_Count,properties.getProperty("Get_Ticket_Count"));
        return sqlQueries;
    }
}
