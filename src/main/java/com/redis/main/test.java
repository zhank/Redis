package com.redis.main;

import java.util.Iterator;
import java.util.Set;

import com.redis.factory.AuthorCacheDataFactory;

/**
 *  ����redis
 * @author zhangkk
 * @date  2017��9��15��
 */
public class test {
	public static void main(String[] args) {
		
		//redis
		AuthorCacheDataFactory authorCache = AuthorCacheDataFactory.getInstance();
		//��ȡ����key
		 Set<String> set = authorCache.getAllData();
		 Iterator it = set.iterator();
		
		 //����key ���ܻ�ȡ����value
	}
}
