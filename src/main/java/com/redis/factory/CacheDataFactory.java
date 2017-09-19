package com.redis.factory;

import java.util.Set;

import redis.clients.jedis.Jedis;

/**
 * Ȩ��ͳ�ƻ���
 * 
 * @author zhangkk
 * @date 2017��9��14��
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
	 * ����Ӧ��ͳ������
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
	 * ���»�����ͳ����Ϣ
	 * @param jedis
	 * @param key     key
	 * @param seconds  ����ʱ��
	 * @param value   ֵ
	 */
	public void updateCacheData(Jedis jedis, String key, Integer seconds, Object value) {
		long t1 = System.currentTimeMillis();
		if (value == null) {
			value = "0";
		}
		/**
		 * ���û����õĹ��ڵ�ʱ������Ϊ 0��-1ʱ������ӹ���ʱ�� 
		 */
		if(seconds != null && (seconds.intValue() != 0 || seconds.intValue() != -1)) {
			jedis.setex(key, seconds, value.toString());
		} else {
			jedis.set(key, value.toString());
		}
		long t2 = System.currentTimeMillis();
		System.out.println("����ͳ�ƽڵ�=" + (t2 - t1));
	}

	/**
	 * ɾ����������
	 * 
	 * @param key
	 */
	public void deleteCacheDataByKey(String key) {
		Jedis jedis = RedisPool.getJedis();
		jedis.del(key);
		RedisPool.closeConn(jedis);
	}

	/**
	 * ͨ��key��ȡͳ������
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
	 * ����redis����������
	 * @return
	 */
	public Set<String> getAllData() {
		Jedis jedis = RedisPool.getJedis();
		return jedis.keys("*");
	}
}
