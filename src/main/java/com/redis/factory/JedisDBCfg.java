package com.redis.factory;

/**
 * jedis数据库相关的配置
 */
public class JedisDBCfg {

	/**
	 * Redis服务器IP
	 */
	public String IP = "127.0.0.1";

	/**
	 * Redis的端口号
	 */
	public int PORT = 6379;

	/**
	 * 访问密码
	 */
	public String AUTH = "123456";

	/**
	 * 可用连接实例的最大数目，默认为8；
	 * 如果赋值为-1，则表示不限制，如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)
	 */
	public int MAX_TOTAL = 1024;

	/**
	 * 控制一个pool最多有多少个状态为idle(空闲)的jedis实例，默认值是8
	 */
	public int MAX_IDLE = 1000;

	/**
	 * 等待可用连接的最大时间，单位是毫秒，默认值为-1，表示永不超时。 如果超过等待时间，则直接抛出JedisConnectionException
	 */
	public int MAX_WAIT_MILLIS = 10000;

	public int TIMEOUT = 10000;

	/**
	 * 在borrow(用)一个jedis实例时，是否提前进行validate(验证)操作； 如果为true，则得到的jedis实例均是可用的
	 */
	public boolean TEST_ON_BORROW = true;

	private static JedisDBCfg m_instance;

	public static JedisDBCfg getInstance() {
		if (m_instance == null) {
			m_instance = new JedisDBCfg();
		}
		return m_instance;
	}

	public String getCaption() {
		return "jedis数据库配置";
	}
}
