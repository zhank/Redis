package com.redis.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Explain:Redis连接池
 */
public final class RedisPool {

	private static final Logger logger = LoggerFactory.getLogger(RedisPool.class);

	private static JedisPool jedisPool = null;
		
	/**
	 * 初始化Redis连接池
	 */
	static {
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			/*
			 * 注意： 在高版本的jedis
			 * jar包，比如本版本2.9.0，JedisPoolConfig没有setMaxActive和setMaxWait属性了
			 * 这是因为高版本中官方废弃了此方法，用以下两个属性替换。 maxActive ==> maxTotal maxWait==>
			 * maxWaitMillis
			 */
			JedisDBCfg cfg = JedisDBCfg.getInstance();
			config.setMaxTotal(cfg.MAX_TOTAL);
			config.setMaxIdle(cfg.MAX_IDLE);
			config.setMaxWaitMillis(cfg.MAX_WAIT_MILLIS);
			config.setTestOnBorrow(cfg.TEST_ON_BORROW);
			jedisPool = new JedisPool(config, cfg.IP, cfg.PORT, cfg.TIMEOUT);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	/**
	 * 获取Jedis实例
	 * 
	 * @return
	 */
	public synchronized static Jedis getJedis() {
		try {
			if (jedisPool != null) {
				Jedis jedis = jedisPool.getResource();
				return jedis;
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	public static void closeConn(final Jedis jedis) {
		if (jedis != null) {
			jedis.close();
			// jedisPool.returnResource(jedis);
			// jedis.close()取代jedisPool.returnResource(jedis)方法将3.0版本开始
			// jedis.close();
		}
	}
}