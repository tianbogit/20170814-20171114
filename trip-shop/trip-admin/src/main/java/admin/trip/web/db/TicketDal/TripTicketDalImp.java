package admin.trip.web.db.TicketDal;

import admin.trip.web.db.Enums.TripToDb;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.asyncsql.AsyncSQLClient;

import java.util.HashMap;

public class TripTicketDalImp implements TripTicketDal {
    private static final Logger LOGGER =  LoggerFactory.getLogger(TripTicketDalImp.class);

    private final HashMap<TripToDb, String> sqlQueries;
    private AsyncSQLClient client;


    TripTicketDalImp(AsyncSQLClient client, HashMap<TripToDb, String> sqlQueries, Handler<AsyncResult<TripTicketDal>> readyHandler) {
        this.client = client;
        this.sqlQueries = sqlQueries;
        readyHandler.handle(Future.succeededFuture(this));
    }
}
