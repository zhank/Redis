package com.redis.main;

import java.util.Iterator;
import java.util.Set;

import com.redis.factory.AuthorCacheDataFactory;

/**
 *  测试redis
 * @author zhangkk
 * @date  2017年9月15日
 */
public class test {
	public static void main(String[] args) {
		
		//redis
		AuthorCacheDataFactory authorCache = AuthorCacheDataFactory.getInstance();
		//获取所有key
		 Set<String> set = authorCache.getAllData();
		 Iterator it = set.iterator();
		
		 //遍历key 就能获取所有value
	}
}
