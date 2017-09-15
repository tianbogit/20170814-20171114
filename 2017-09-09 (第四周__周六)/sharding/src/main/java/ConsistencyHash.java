import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class ConsistencyHash {

    // 环的所有节点
    private TreeMap<Long, Object> allNodes = null;
    // 真实节点
    private List<Object> realNodes = new ArrayList();
    // 设置虚拟节点数目
    // 太多会影响性能，太少又会导致负载不均衡，一般说来，经验值是150，
    // 当然根据集群规模和负载均衡的精度需求，这个值应该根据具体情况具体对待。
    private int VIRTUAL_NODE_COUNT = 150;

    /**
     * 初始化
     */
    public void init() {

        // 5个节点
        realNodes.add("db0");
        realNodes.add("db1");
        realNodes.add("db2");
        realNodes.add("db3");
        realNodes.add("db4");

        // 构造每台真实服务器的虚拟节点
        allNodes = new TreeMap<>();
        for (int i = 0; i < realNodes.size(); i++) {
            Object nodeInfo = realNodes.get(i);
            for (int j = 0; j < VIRTUAL_NODE_COUNT; j++) {
                allNodes.put(hash(computeMd5("NODE-" + i + "-VIRTUAL-" + j), 0), nodeInfo);
                // allNodes.put(hash(nodeInfo.toString() + j), nodeInfo);
            }
        }
    }

    /**
     * 计算MD5值
     */
    public byte[] computeMd5(String k) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 not supported", e);
        }
        md5.reset();
        byte[] keyBytes = null;
        try {
            keyBytes = k.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Unknown string :" + k, e);
        }

        md5.update(keyBytes);
        return md5.digest();
    }

    /**
     * 根据2^32
     *
     * @param digest
     * @param nTime
     * @return
     */
    public long hash(byte[] digest, int nTime) {
        long rv = ((long) (digest[3 + nTime * 4] & 0xFF) << 24)
                | ((long) (digest[2 + nTime * 4] & 0xFF) << 16)
                | ((long) (digest[1 + nTime * 4] & 0xFF) << 8)
                | (digest[0 + nTime * 4] & 0xFF);

        return rv & 0xffffffffL; /* Truncate to 32-bits */
    }


    /**
     * 根据key的hash值取得节点信息
     *
     * @param hash
     * @return
     */
    public Object getNodeInfo(long hash) {
        Long key = hash;
        SortedMap<Long, Object> tailMap = allNodes.tailMap(key);
        if (!tailMap.isEmpty()) {
            key = tailMap.firstKey();
        } else {
            key = allNodes.firstKey();
        }
        return allNodes.get(key);
    }

    public static void main(String[] args) {

        ConsistencyHash consistencyHash = new ConsistencyHash();
        consistencyHash.init();


        int _0 = 0;
        int _1 = 0;
        int _2 = 0;
        int _3 = 0;
        int _4 = 0;

        Random ran = new Random();
        for (int i = 0; i < 500; i++) {
            // 随便取一个数的md5
            byte[]  ranNum = consistencyHash.computeMd5(String.valueOf(i));

            // 分配到随即的hash
            long key = consistencyHash.hash(ranNum, 2);
            // long key = consistencyHash.hash(ranNum, ran.nextInt(consistencyHash.VIRTUAL_NODE_COUNT));

            // 获取他所属节点的信息
            // System.out.println(consistencyHash.getNodeInfo(key));
            if (consistencyHash.getNodeInfo(key).equals("db0")){
                _0++;
            }else if (consistencyHash.getNodeInfo(key).equals("db1")){
                _1++;
            }else if (consistencyHash.getNodeInfo(key).equals("db2")){
                _2++;
            }else if (consistencyHash.getNodeInfo(key).equals("db3")){
                _3++;
            }else if (consistencyHash.getNodeInfo(key).equals("db4")){
                _4++;
            }else{
                System.out.println("error");
            }
        }

        // 输出每台负载情况
        System.out.println("_0 = " + _0);
        System.out.println("_1 = " + _1);
        System.out.println("_2 = " + _2);
        System.out.println("_3 = " + _3);
        System.out.println("_4 = " + _4);
    }

}