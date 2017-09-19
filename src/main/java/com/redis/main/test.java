package com.redis.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jasig.cas.client.validation.Cas10TicketValidationFilter;

import com.alibaba.fastjson.JSON;
import com.redis.entity.TbUser;
import com.redis.factory.CacheDataFactory;
import com.redis.factory.DbConnection;

/**
 * 测试redis
 * 
 * @author zhangkk
 * @date 2017年9月15日
 */
public class test {
	public static void main(String[] args) {
		Connection conn = DbConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet result = null;
		CacheDataFactory authorCache = CacheDataFactory.getInstance();
		try {
			// 往数据库中添加测试数据
			// insetTestDataToDB(conn);

			// 从数据库取数据添加去redis
			 //insertDataToRedis(conn, result);

			boolean isSearchDB = true; // 是否查询数据库
			if (isSearchDB) {
				// 查询数据库
				searchUserFromDB(conn, ps, result);
			} else {
				// 查询redis
				searchUserFromRedis(authorCache);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeResultSet(result);
			DbConnection.closeConnection(conn);
		}
	}

	/**
	 * 查询数据
	 * 
	 * @param conn
	 * @param ps
	 * @param result
	 * @param t1
	 * @throws Exception
	 */
	public static void searchUserFromDB(Connection conn, PreparedStatement ps, ResultSet result)
			throws Exception {
		// 查询数据库userPhone 10086_20000的用户;
		String sql = "SELECT * FROM TB_USER";
		ps = conn.prepareStatement(sql);
	//ps.setString(1, "20011");
		Long t1 = System.currentTimeMillis();
		
		result = ps.executeQuery();
		
		Long t2 = System.currentTimeMillis();
		System.out.println("查询数据库耗时:" + (t2 - t1));
		int count = 0;
		while (result.next()) {
			count++;
			/*TbUser user = new TbUser();
			user.setUserId(result.getInt("USER_ID"));
			user.setUserName(result.getString("USER_NAME"));
			user.setUserPwd(result.getString("USER_PWD"));
			user.setUserPhone(result.getString("USER_PHONE"));
			user.setUserAge(result.getInt("USER_AGE"));*/
			//printInfo(user);
		}
		System.out.println("查询条数：" + count);
	}

	/**
	 * 查询数据从redis
	 * 
	 * @param t1
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public static void searchUserFromRedis(CacheDataFactory authorCache) throws SQLException {
		Long t1 = System.currentTimeMillis();
		
		Set<String> cacheData = authorCache.getAllData();
		
		Long t2 = System.currentTimeMillis();
		System.out.println("查询redis耗时:" + (t2 - t1));
		System.out.println("查询条数：" + cacheData.size());
		
		/*if (cacheData != null) {

			// redis里存储的是字符串，用json解析   alibaba的json
			Map<String, Object> userMap = (Map<String, Object>) JSON.parse(cacheData.toString());
			TbUser user = new TbUser();
			if (userMap.get("USER_ID") != null) {
				user.setUserId(Integer.parseInt(userMap.get("USER_ID").toString()));
			}
			user.setUserName(userMap.get("USER_NAME").toString());
			user.setUserPwd(userMap.get("USER_PWD").toString());
			user.setUserPhone(userMap.get("USER_PHONE").toString());
			if (userMap.get("USER_AGE") != null) {
				user.setUserAge(Integer.parseInt(userMap.get("USER_AGE").toString()));
			}
			printInfo(user);
		}*/
	}

	/**
	 * 输出
	 * 
	 * @throws SQLException
	 */
	public static void printInfo(TbUser user) throws SQLException {
		String info = "序列号：" + user.getUserId() + "	用户名:" + user.getUserName() + " 	密码:" + user.getUserPwd()
				+ "	联系方式:" + user.getUserPhone() + "	年龄:" + user.getUserAge();
		System.out.println(info);
	}

	/**
	 * 插入测试数据
	 * 
	 * @param conn
	 */
	public static void insetTestDataToDB(Connection conn) {
		PreparedStatement ps = null;
		try {
			String sql = "insert into tb_user (user_name, user_pwd, user_phone, user_age) values(?,?,?,?)";
			ps = conn.prepareStatement(sql);// 实例化预编译语句
			for (int i = 0; i < 1000000; i++) {
				ps.setString(1, "圆通" + i);
				ps.setString(2, "123567");
				ps.setString(3, 10086 + "_" + i);
				ps.setInt(4, i);
				boolean isSuccfully = ps.execute();
				if (isSuccfully) {
					System.out.println("插入成功;第" + "条！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(conn);
		}
	}

	/**
	 * 添加数据进入redis
	 */
	public static void insertDataToRedis(Connection conn, ResultSet result) {
		PreparedStatement ps = null;
		try {
			String sql = "select * from tb_user";

			// 实例化预编译语句
			ps = conn.prepareStatement(sql);
			result = ps.executeQuery();

			// 计数
			int count = 0;
			while (result.next()) {
				int userId = result.getInt("USER_ID");
				String userName = result.getString("USER_NAME");
				String userPwd = result.getString("USER_PWD");
				String userPhone = result.getString("USER_PHONE");
				int userAge = result.getInt("USER_AGE");

				// 人员userId当作redis的key，防止重复
				String key = userId + "";

				// 封闭Map当作value
				Map<String, Object> userMap = new HashMap<String, Object>();
				userMap.put("USER_ID", userId);
				userMap.put("USER_NAME", userName);
				userMap.put("USER_PWD", userPwd);
				userMap.put("USER_PHONE", userPhone);
				userMap.put("USER_AGE" + "", userAge);

				CacheDataFactory cache = CacheDataFactory.getInstance();
				cache.updateCacheData(key, null, JSON.toJSON(userMap));
				System.out.println("当前执行条数：" + (++count));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(conn);
		}

	}
}
