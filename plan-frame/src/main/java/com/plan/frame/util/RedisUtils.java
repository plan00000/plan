package com.plan.frame.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author Huangry
 * @Description: Redis连接操作
 * @Date 2019-02-18
 */
@Service
public class RedisUtils {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private JedisPool jedisPool;

//    final static GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
//    private static final JedisPool jedisPool = new JedisPool(poolConfig,"10.50.1.206", 6379,3000,"gpsserver");
    //spring配置使用
    //private static final JedisPool jedisPool = (JedisPool) RedisConfig.getBeanById("jedisPool");

    /**
     * 成功,"OK"
     */
    private static final String SUCCESS_OK = "OK";
    /**
     * 成功,1L
     */
    private static final Long SUCCESS_STATUS_LONG = 1L;
    /**
     * 只用key不存在时才设置。Only set the key if it does not already exist
     */
    private static final String NX = "NX";
    /**
     * XX -- 只有key存在时才设置。和NX相反。Only set the key if it already exist.
     */
    private static final String XX = "XX";
    /**
     * EX|PX, 时间单位，EX是秒，PX是毫秒。expire time units: EX = seconds; PX = milliseconds
     */
    private static final String EX = "EX";
    /**
     * EX|PX, 时间单位，EX是秒，PX是毫秒。expire time units: EX = seconds; PX = milliseconds
     */
    //private static final String PX = "PX";

    /**
     * 写入缓存
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 写入缓存设置时效时间
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime ,TimeUnit timeUnit) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, timeUnit);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 批量删除对应的value
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     * @param pattern
     */
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0){
            redisTemplate.delete(keys);
        }
    }
    /**
     * 删除对应的value
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }
    /**
     * 判断缓存中是否有对应的value
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }
    /**
     * 读取缓存
     * @param key
     * @return
     */
    public Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 哈希 添加
     * @param key
     * @param hashKey
     * @param value
     */
    public void hmSet(String key, Object hashKey, Object value){
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.put(key,hashKey,value);
    }

    /**
     * 哈希获取数据
     * @param key
     * @param hashKey
     * @return
     */
    public Object hmGet(String key, Object hashKey){
        HashOperations<String, Object, Object>  hash = redisTemplate.opsForHash();
        return hash.get(key,hashKey);
    }

    /**
     * 列表添加
     * @param k
     * @param v
     */
    public void lPush(String k,Object v){
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPush(k,v);
    }

    /**
     * 列表获取
     * @param k
     * @param l
     * @param l1
     * @return
     */
    public List<Object> lRange(String k, long l, long l1){
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.range(k,l,l1);
    }

    /**
     * 集合添加
     * @param key
     * @param value
     */
    public void add(String key,Object value){
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(key,value);
    }

    /**
     * 集合获取
     * @param key
     * @return
     */
    public Set<Object> setMembers(String key){
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.members(key);
    }

    /**
     * 有序集合添加
     * @param key
     * @param value
     * @param scoure
     */
    public void zAdd(String key,Object value,double scoure){
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        zset.add(key,value,scoure);
    }

    /**
     * 有序集合获取
     * @param key
     * @param scoure
     * @param scoure1
     * @return
     */
    public Set<Object> rangeByScore(String key,double scoure,double scoure1){
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.rangeByScore(key, scoure, scoure1);
    }

    /**************************** redis 列表List start***************************/

    /**
     * 将一个值插入到列表头部，value可以重复，返回列表的长度
     * @param key
     * @param value String
     * @return 返回List的长度
     */
    public Long lpush(String key, String value){
        Jedis jedis = jedisPool.getResource();
        Long length = jedis.lpush(key, value);
        jedis.close();
        return length;
    }

    /**
     * 将多个值插入到列表头部，value可以重复
     * @param key
     * @param values String[]
     * @return 返回List的数量size
     */
    public Long lpush(String key, String[] values){
        Jedis jedis = jedisPool.getResource();
        Long size = jedis.lpush(key, values);
        jedis.close();
        //System.out.println(result);
        return size;
    }

    /**
     * 获取List列表
     * @param key
     * @param start long，开始索引
     * @param end long， 结束索引
     * @return List<String>
     */
    public List<String> lrange(String key, long start, long end){
        Jedis jedis = jedisPool.getResource();
        List<String> list = jedis.lrange(key, start, end);
        jedis.close();
        return list;
    }

    /**
     * 通过索引获取列表中的元素
     * @param key
     * @param index，索引，0表示最新的一个元素
     * @return String
     */
    public String lindex(String key, long index){
        Jedis jedis = jedisPool.getResource();
        String str = jedis.lindex(key, index);
        jedis.close();
        return str;
    }

    /**
     * 获取列表长度，key为空时返回0
     * @param key
     * @return Long
     */
    public Long llen(String key){
        Jedis jedis = jedisPool.getResource();
        Long length = jedis.llen(key);
        jedis.close();
        return length;
    }

    /**
     * 在列表的元素前或者后插入元素，返回List的长度
     * @param key
     * @param where LIST_POSITION
     * @param pivot 以该元素作为参照物，是在它之前，还是之后（pivot：枢轴;中心点，中枢;[物]支点，支枢;[体]回转运动。）
     * @param value
     * @return Long
     */
    public Long linsert(String key, BinaryClient.LIST_POSITION where, String pivot, String value){
        Jedis jedis = jedisPool.getResource();
        Long length = jedis.linsert(key, where, pivot, value);
        jedis.close();
        return length;
    }

    /**
     * 将一个或多个值插入到已存在的列表头部，当成功时，返回List的长度；当不成功（即key不存在时，返回0）
     * @param key
     * @param value String
     * @return Long
     */
    public Long lpushx(String key, String value){
        Jedis jedis = jedisPool.getResource();
        Long length = jedis.lpushx(key, value);
        jedis.close();
        return length;
    }

    /**
     * 将一个或多个值插入到已存在的列表头部，当成功时，返回List的长度；当不成功（即key不存在时，返回0）
     * @param key
     * @param values String[]
     * @return Long
     */
    public Long lpushx(String key, String[] values){
        Jedis jedis = jedisPool.getResource();
        Long length = jedis.lpushx(key, values);
        jedis.close();
        return length;
    }

    /**
     * 移除列表元素，返回移除的元素数量
     * @param key
     * @param count，标识，表示动作或者查找方向
     * <li>当count=0时，移除所有匹配的元素；</li>
     * <li>当count为负数时，移除方向是从尾到头；</li>
     * <li>当count为正数时，移除方向是从头到尾；</li>
     * @param value 匹配的元素
     * @return Long
     */
    public Long lrem(String key, long count, String value){
        Jedis jedis = jedisPool.getResource();
        Long length = jedis.lrem(key, count, value);
        jedis.close();
        return length;
    }

    /**
     * 通过索引设置列表元素的值，当超出索引时会抛错。成功设置返回true
     * @param key
     * @param index 索引
     * @param value
     * @return boolean
     */
    public boolean lset(String key, long index, String value){
        Jedis jedis = jedisPool.getResource();
        String statusCode = jedis.lset(key, index, value);
        jedis.close();
        if(SUCCESS_OK.equalsIgnoreCase(statusCode)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。
     * @param key
     * @param start
     * <li>可以为负数（-1是列表的最后一个元素，-2是列表倒数第二的元素。）</li>
     * <li>如果start大于end，则返回一个空的列表，即列表被清空</li>
     * @param end
     * <li>可以为负数（-1是列表的最后一个元素，-2是列表倒数第二的元素。）</li>
     * <li>可以超出索引，不影响结果</li>
     * @return boolean
     */
    public boolean ltrim(String key, long start, long end){
        Jedis jedis = jedisPool.getResource();
        String statusCode = jedis.ltrim(key, start, end);
        jedis.close();
        if(SUCCESS_OK.equalsIgnoreCase(statusCode)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 移出并获取列表的第一个元素，当列表不存在或者为空时，返回Null
     * @param key
     * @return String
     */
    public String lpop(String key){
        Jedis jedis = jedisPool.getResource();
        String value = jedis.lpop(key);
        jedis.close();
        return value;
    }

    /**
     * 移除并获取列表最后一个元素，当列表不存在或者为空时，返回Null
     * @param key
     * @return String
     */
    public String rpop(String key){
        Jedis jedis = jedisPool.getResource();
        String value = jedis.rpop(key);
        jedis.close();
        return value;
    }

    /**
     * 在列表中的尾部添加一个个值，返回列表的长度
     * @param key
     * @param value
     * @return Long
     */
    public Long rpush(String key, String value){
        Jedis jedis = jedisPool.getResource();
        Long length = jedis.rpush(key, value);
        jedis.close();
        return length;
    }

    /**
     * 在列表中的尾部添加多个值，返回列表的长度
     * @param key
     * @param values
     * @return Long
     */
    public Long rpush(String key, String[] values){
        Jedis jedis = jedisPool.getResource();
        Long length = jedis.rpush(key, values);
        jedis.close();
        return length;
    }

    /**
     * 仅当列表存在时，才会向列表中的尾部添加一个值，返回列表的长度
     * @param key
     * @param value
     * @return Long
     */
    public Long rpushx(String key, String value){
        Jedis jedis = jedisPool.getResource();
        Long length = jedis.rpushx(key, value);
        jedis.close();
        return length;
    }

    /**
     * 移除列表的最后一个元素，并将该元素添加到另一个列表并返回
     * @param sourceKey 源列表的key，当源key不存在时，结果返回Null
     * @param targetKey 目标列表的key，当目标key不存在时，会自动创建新的
     * @return String
     */
    public String rpopLpush(String sourceKey, String targetKey){
        Jedis jedis = jedisPool.getResource();
        String value = jedis.rpoplpush(sourceKey, targetKey);
        jedis.close();
        return value;
    }

    /**
     * 移出并获取列表的【第一个元素】， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
     * @param timeout 单位为秒
     * @param keys
     * <li>当有多个key时，只要某个key值的列表有内容，即马上返回，不再阻塞。</li>
     * <li>当所有key都没有内容或不存在时，则会阻塞，直到有值返回或者超时。</li>
     * <li>当超期时间到达时，keys列表仍然没有内容，则返回Null</li>
     * @return List<String>
     */
    public List<String> blpop(int timeout, String... keys){
        Jedis jedis = jedisPool.getResource();
        List<String> values = jedis.blpop(timeout, keys);
        jedis.close();
        return values;
    }

    /**
     * 移出并获取列表的【最后一个元素】， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
     * @param timeout 单位为秒
     * @param keys
     * <li>当有多个key时，只要某个key值的列表有内容，即马上返回，不再阻塞。</li>
     * <li>当所有key都没有内容或不存在时，则会阻塞，直到有值返回或者超时。</li>
     * <li>当超期时间到达时，keys列表仍然没有内容，则返回Null</li>
     * @return List<String>
     */
    public List<String> brpop(int timeout, String... keys){
        Jedis jedis = jedisPool.getResource();
        List<String> values = jedis.brpop(timeout, keys);
        jedis.close();
        return values;
    }


    public List<String> getkeys(String contentvalue){

        Jedis jedis = jedisPool.getResource();
        jedis.select(15);
        Set<String> set = jedis.keys(contentvalue+"*");
        List<String> values = new ArrayList<>();
        for (String key : set) {
            values.add(set.toString());
        }
        jedis.close();
        return values;
    }


    /**
     * 从列表中弹出列表最后一个值，将弹出的元素插入到另外一个列表中并返回它；
     * 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
     * @param sourceKey 源列表的key，当源key不存在时，则会进行阻塞
     * @param targetKey 目标列表的key，当目标key不存在时，会自动创建新的
     * @param timeout 单位为秒
     * @return String
     */
    public String brpopLpush(String sourceKey, String targetKey, int timeout){
        Jedis jedis = jedisPool.getResource();
        String value = jedis.brpoplpush(sourceKey, targetKey, timeout);
        jedis.close();
        return value;
    }

    public Object[] getkeys(String contentvalue, int index){
        Jedis jedis = jedisPool.getResource();
        jedis.select(index);
        Set<String> set = jedis.keys(contentvalue + "*");
        jedis.close();
        if(set.size() > 0){
            return set.toArray();
        } else {
            return null;
        }
    }

    /**
     * 获取List列表
     * @param key
     * @param  index int 库索引
     * @return List<String>
     */
    public Map<String, String> hgetAll(String key, int index){
        Jedis jedis = jedisPool.getResource();
        jedis.select(index);
        Map<String, String> map = jedis.hgetAll(key);
        jedis.close();
        return map;
    }

    public Long hlen(String key, int index){
        Jedis jedis = jedisPool.getResource();
        jedis.select(index);
        Long length = jedis.hlen(key);
        jedis.close();
        return length;
    }

    /**************************** redis 列表List end***************************/
}