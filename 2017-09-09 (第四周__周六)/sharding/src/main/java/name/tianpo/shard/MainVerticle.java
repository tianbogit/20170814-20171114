package name.tianpo.shard;

import name.tianpo.shard.utils.ShardingUtil;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class MainVerticle {
   static String key = "10000000";
    static int dbSize = 8;
    static int tbSize = 16;
    static String dbName = "sharding";
    static String tbName = "t_sharding";

    public static void main(String args[]) {
        System.out.println(ShardingUtil.getDBTBIndex(key, dbSize, tbSize, dbName, tbName));
        testEvenlySharding();
    }

    static AtomicInteger count_0_0 = new AtomicInteger();
    static AtomicInteger count_0_1 = new AtomicInteger();
    static AtomicInteger count_0_2 = new AtomicInteger();
    static AtomicInteger count_1_0 = new AtomicInteger();
    static AtomicInteger count_1_1 = new AtomicInteger();
    static AtomicInteger count_1_2 = new AtomicInteger();
    static AtomicInteger count_2_0 = new AtomicInteger();
    static AtomicInteger count_2_1 = new AtomicInteger();
    static AtomicInteger count_2_2 = new AtomicInteger();
    static AtomicInteger other = new AtomicInteger();
    public static void testEvenlySharding(){
        int n = 10000000;
        int dbQuantity =3;
        int tbQuantity = 3;
        long start = System.currentTimeMillis();
        for(int i=0; i<n; i++){
            String shardKey = UUID.randomUUID().toString();
            int dbIndex = ShardingUtil.getDataBaseIndex(shardKey, dbQuantity, tbQuantity);
            int tbIndex = ShardingUtil.getTableIndex(shardKey, dbQuantity, tbQuantity);
            if(dbIndex == 0 && tbIndex == 0){
                count_0_0.incrementAndGet();
                continue;
            }
            if(dbIndex == 0 && tbIndex == 1) {
                count_0_1.incrementAndGet();
                continue;
            }
            if(dbIndex == 0 && tbIndex == 2) {
                count_0_2.incrementAndGet();
                continue;
            }
            if(dbIndex == 1 && tbIndex == 0) {
                count_1_0.incrementAndGet();
                continue;
            }
            if(dbIndex == 1 && tbIndex == 1) {
                count_1_1.incrementAndGet();
                continue;
            }
            if(dbIndex == 1 && tbIndex == 2) {
                count_1_2.incrementAndGet();
                continue;
            }
            if(dbIndex == 2 && tbIndex == 0) {
                count_2_0.incrementAndGet();
                continue;
            }
            if(dbIndex == 2 && tbIndex == 1) {
                count_2_1.incrementAndGet();
                continue;
            }
            if(dbIndex == 2 && tbIndex == 2) {
                count_2_2.incrementAndGet();
                continue;
            }
            other.incrementAndGet();
        }
        long end = System.currentTimeMillis();
        System.out.println("执行"+n+"次，总耗时为："+(end - start)+"毫秒,平均耗时为："+(double)(end-start)/n+"毫秒");
        System.out.println("数据库0 表0："+count_0_0.get());
        System.out.println("数据库0 表1："+count_0_1.get());
        System.out.println("数据库0 表2："+count_0_2.get());
        System.out.println("数据库1 表0："+count_1_0.get());
        System.out.println("数据库1 表1："+count_1_1.get());
        System.out.println("数据库1 表2："+count_1_2.get());
        System.out.println("数据库2 表0："+count_2_0.get());
        System.out.println("数据库2 表1："+count_2_1.get());
        System.out.println("数据库2 表2："+count_2_2.get());
        System.out.println("其他数据："+other.get());
    }
}
