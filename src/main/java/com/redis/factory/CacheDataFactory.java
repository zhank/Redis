package com.redis.factory;

import java.util.Set;

import redis.clients.jedis.Jedis;

/**
 * 权限统计缓存
 * 
 * @author zhangkk
 * @date 2017年9月14日
 */
public class CacheDataFactory {

	//private static final Logger logger = LoggerFactory.getLogger(CacheDataFactory.class);

	private CacheDataFactory() {
		
	}

	private static class SingletonHolder {
		private static final CacheDataFactory INSTANCE = new CacheDataFactory();
	}

	public static final CacheDataFactory getInstance() {
		return SingletonHolder.INSTANCE;
	}

	/**
	 * @param orgId
	 * @return
	 */
	public String getKey(String key) {
		return key;
	}

	/**
	 * 更新应用统计数据
	 */
	public void updateCacheData(String key, Object value) {
		updateCacheData(key, -1, value);
	}
	public void updateCacheData(String key, Integer seconds, Object value) {
		Jedis jedis = RedisPool.getJedis();
		updateCacheData(jedis, key, seconds, value);
		RedisPool.closeConn(jedis);
	}

	/**
	 * 更新缓存中统计信息
	 * @param jedis
	 * @param key     key
	 * @param seconds  过期时间
	 * @param value   值
	 */
	public void updateCacheData(Jedis jedis, String key, Integer seconds, Object value) {
		long t1 = System.currentTimeMillis();
		if (value == null) {
			value = "0";
		}
		/**
		 * 当用户设置的过期的时间设置为 0、-1时，遇添加过期时间 
		 */
		if(seconds != null && (seconds.intValue() != 0 || seconds.intValue() != -1)) {
			jedis.setex(key, seconds, value.toString());
		} else {
			jedis.set(key, value.toString());
		}
		long t2 = System.currentTimeMillis();
		System.out.println("更新统计节点=" + (t2 - t1));
	}

	/**
	 * 删除缓存数据
	 * 
	 * @param key
	 */
	public void deleteCacheDataByKey(String key) {
		Jedis jedis = RedisPool.getJedis();
		jedis.del(key);
		RedisPool.closeConn(jedis);
	}

	/**
	 * 通过key获取统计数据
	 * 
	 * @param key
	 * @return
	 */
	public Object getCacheDataByKey(String key) {
		Jedis jedis = RedisPool.getJedis();
		Object count = jedis.get(key);
		RedisPool.closeConn(jedis);
		return count;
	}
	
	/**
	 * 返回redis里所有数据
	 * @return
	 */
	public Set<String> getAllData() {
		Jedis jedis = RedisPool.getJedis();
		return jedis.keys("*");
	}
}
