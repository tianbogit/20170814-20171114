package name.tianpo.shard.utils;

import com.sun.istack.internal.logging.Logger;

public class ShardingUtil {
    private static Logger logger = Logger.getLogger(ShardingUtil.class);

    public static String getDBTBIndex(String key, int dbSize, int tbSize, String dbName, String tbName){
        int dbIndex = ShardingUtil.getDataBaseIndex(key, dbSize, tbSize);
        int tbIndex = ShardingUtil.getTableIndex(key, dbSize, tbSize);

        String result = dbName+"_"+CommonUtil.completionNumberFormat(dbIndex)+"."+tbName+"_"+CommonUtil.completionNumberFormat(tbIndex);

        return "路由规则为：--> "+result;
    }

    /**
     *
     * @Title: getRouteName
     * @Description: 根据源名称 设置符合路由规则的名称
     * @param @param index
     * @param @param srcName
     * @param @return    设定文件
     * @return String    返回类型
     * @throws
     */
    public static String getRouteName(int index, String srcName) {
        String targetName = srcName + "_" + CommonUtil.completionNumberFormat(index);
        return targetName;
    }

    /**
     *
     * @Title: getDataBaseIndex
     * @Description: 根据分库规则计算出数据源索引  TODO 可根据自己的规则进行定制
     * @param @param shardKey
     * @param @param dbQuantity
     * @param @param tbQuantity
     * @param @return    设定文件
     * @return int    返回类型
     * @throws
     */
    public static int getDataBaseIndex(Object shardKey, int dbQuantity, int tbQuantity) {
        Long routeKey = CommonUtil.crc32(CommonUtil.toByteArray(shardKey));
        // shardKey % tbSize / dbSize
        return (int) ((routeKey % tbQuantity) / dbQuantity);
    }

    /**
     *
     * @Title: getTbIndex
     * @Description: 根据分片规则计算出数据库表索引  TODO 可根据自己的规则进行定制
     * @param @param shardKey 路由键值
     * @param @param dbQuantity 数据库数量
     * @param @param tbQuantity 数据表数量
     * @param @param shardMode 是否分片
     * @param @return    设定文件
     * @return int    返回类型
     * @throws
     */
    public static int getTableIndex(Object shardKey, int dbQuantity, int tbQuantity) {
        Long routeKey = CommonUtil.crc32(CommonUtil.toByteArray(shardKey));
        // shardKey % tbSize % dbSize
        return (int) (routeKey % tbQuantity % dbQuantity);

    }

}
