package Aoyou.tianpo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLConnection;

import java.util.UUID;

public class AoyouDatabaseVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(AoyouDatabaseVerticle.class);
    private static String CONFIG_Aoyou_QUEUE = "aoyou.tianpo.queue";
    private JDBCClient dbClient;

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        /*
        * 后续迁移到配置中
        * */
        dbClient = JDBCClient.createShared(vertx, new JsonObject()
                .put("url", "jdbc:mysql://192.168.111.136:3306/Aoyou")
                .put("driver_class", "com.mysql.jdbc.Driver")
                .put("max_pool_size", 30)
                .put("user", "root")
                .put("password", "123456")
        );
        vertx.eventBus().consumer(CONFIG_Aoyou_QUEUE, this::onMessage);
        startFuture.complete();
    }

    public void onMessage(Message<JsonObject> message) {
        if (!message.headers().contains("action")) {
            LOGGER.error("No action header specified for message with headers {} and body {}",
                    message.headers(), message.body().encodePrettily());
            return;
        }
        String action = message.headers().get("action");
        switch (action) {
            case "login":
                Login(message);
            case "register_user_reg":
                RegisterUserReg(message);
                break;
            case "register_user_login":
                RegisterUserLogin(message);
                break;
            default:
                break;
        }
        message.reply("OK");
    }

    private void Login(Message<JsonObject> message) {
        dbClient.getConnection(ar -> {
            if (ar.failed()) {
                LOGGER.error("Could not open a database connection", ar.cause());
            } else {
                SQLConnection connection = ar.result();
                JsonObject request = message.body();
                JsonArray data = new JsonArray()
                        .add(request.getString("username"))
                        .add(request.getString("pwd"));
                connection.queryWithParams("select count(1) from Users_Login where name=? and pwd=?", data, ms -> {
                    connection.close();
                    //LOGGER.debug( ms.result().getNumRows());
                    if (ms.succeeded() && ms.result().getNumRows() > 0) {
                        message.reply("ok");
                    } else {
                        reportQueryError(message, ms.cause());
                    }
                });
            }
        });
    }

    private void RegisterUserReg(Message<JsonObject> message) {
        dbClient.getConnection(ar -> {
            if (ar.failed()) {
                LOGGER.error("Could not open a database connection", ar.cause());
            } else {
                SQLConnection connection = ar.result();
                JsonObject request = message.body();
                JsonArray data = new JsonArray()
                        .add(request.getString("username"))
                        .add(request.getString("sex"))
                        .add(request.getString("email"));
                connection.updateWithParams("Insert into Users_Reg(name,sex,email)  VALUES(?,?,?)", data, ms -> {
                    connection.close();
                    if (ms.succeeded()) {
                        message.reply("ok");
                    } else {
                        reportQueryError(message, ms.cause());
                    }
                });
            }
        });
    }

    private void RegisterUserLogin(Message<JsonObject> message) {
        dbClient.getConnection(ar -> {
            if (ar.failed()) {
                LOGGER.error("Could not open a database connection", ar.cause());
            } else {
                SQLConnection connection = ar.result();
                JsonObject request = message.body();

                JsonArray data = new JsonArray()
                        .add(request.getString("username"))
                        .add(request.getString("pwd"))
                        .add(request.getString("sex"))
                        .add(request.getString("email"));
                connection.updateWithParams("Insert into Users_Login(name,pwd,sex,email)  VALUES(?,?,?,?)", data, ms -> {
                    connection.close();
                    if (ms.succeeded()) {
                        message.reply("ok");
                    } else {
                        reportQueryError(message, ms.cause());
                    }
                });
            }
        });
    }

    public enum ErrorCodes {
        NO_ACTION_SPECIFIED,
        BAD_ACTION,
        DB_ERROR
    }

    private void reportQueryError(Message<JsonObject> message, Throwable cause) {
        LOGGER.error("Database query error", cause);
        message.fail(ErrorCodes.DB_ERROR.ordinal(), cause.getMessage());
    }
}
