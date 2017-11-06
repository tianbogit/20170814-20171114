package admin.trip.web.db.TicketDal;

import admin.trip.web.db.Enums.TripToDb;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.asyncsql.AsyncSQLClient;
import io.vertx.serviceproxy.ProxyHelper;

import java.util.HashMap;

@ProxyGen
public interface TripTicketDal {

    static TripTicketDal create(AsyncSQLClient client, HashMap<TripToDb, String> sqlQueries, Handler<AsyncResult<TripTicketDal>> readyHandler) {
        return  new TripTicketDalImp(client,sqlQueries,readyHandler);
    }
    static TripTicketDal createProxy(Vertx vertx, String address) {
        return ProxyHelper.createProxy(TripTicketDal.class,vertx,address);
    }


}
