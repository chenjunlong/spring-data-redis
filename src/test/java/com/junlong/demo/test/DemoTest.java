package com.junlong.demo.test;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.demo.mode.User;
import com.demo.service.UserServices;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class DemoTest {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Resource(name = "deque")
	private Deque<String> deque;
	@Resource(name = "map")
	private Map<String, String> map;
	@Resource(name = "userService")
	private UserServices userService;
	private ApplicationContext content;

	@Test
	public void set() {
		redisTemplate.opsForValue().set("mykey1", "helloworld!!!");
	}

	@Test
	public void get() {
		String value = redisTemplate.opsForValue().get("mykey1");
		System.out.println(value);
	}

	@Test
	public void multi() {
		long start = System.currentTimeMillis();
		List<Object> result = redisTemplate.execute(new SessionCallback<List<Object>>() {
			@Override
			public <K, V> List<Object> execute(RedisOperations<K, V> operations) throws DataAccessException {
				operations.multi();
				for (int i = 0; i < 100000; i++) {
					String key = "key" + i;
					String value = "value" + i;
					redisTemplate.opsForValue().set(key, value);
					// if (i == 1) {
					// throw new RuntimeException("redis exec exception...");
					// }
				}
				// 10s后提交
				sleep();
				return operations.exec();
			}
		});
		long end = System.currentTimeMillis();
		System.out.println("result:" + result + ",millis:" + (end - start));
	}

	@Test
	public void noMulti() {
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			redisTemplate.opsForValue().set("key" + i, "value" + i);
		}
		// 10s后提交
		sleep();
		long end = System.currentTimeMillis();
		System.out.println("millis:" + (end - start));
	}

	@Test
	public void pipelined() {
		long start = System.currentTimeMillis();
		stringRedisTemplate.executePipelined(new RedisCallback<Object>() {
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection stringRedisConn = (StringRedisConnection) connection;
				for (int i = 0; i < 100000; i++) {
					stringRedisConn.set("key" + i, "value" + i);
					// if (i == 10) {
					// throw new RuntimeException("redis exec exception...");
					// }
				}
				// 10s后提交
				sleep();
				return null;
			}
		});
		long end = System.currentTimeMillis();
		System.out.println("millis:" + (end - start));
	}

	@Test
	public void redisLuaScript() {
		DefaultRedisScript<String> redisScript = new DefaultRedisScript<String>();
		ClassPathResource resource = new ClassPathResource("/redisScript/demo.lua");
		redisScript.setScriptSource(new ResourceScriptSource(resource));
		redisScript.setResultType(String.class);
		List<String> keys = new ArrayList<String>();
		keys.add("mykey1");
		keys.add("mykey2");
		String result = redisTemplate.execute(redisScript, keys, new Object[] { "123", "456" });
		System.out.println(result);
	}

	@Test
	public void redisQueue() {
		deque.push("test1");
	}

	@Test
	public void redisMap() {
		map.put("key1", "v1");
	}

	@Test
	public void getUser() {
		User user = userService.getUserByMobile("13121771001");
		if(user != null){
			System.out.println("UserInfo:" + user.toString());
		}
	}
	
	@Test
	public void updateUser(){
		userService.updateNameByMobile("testxxxx","13121771001");
	}
	
	@Test
	public void publish(){
		redisTemplate.convertAndSend("demo:message", "This is a new message...");
	}
	
	public void sleep() {
		boolean flag = true;
		int i = 10;
		while (flag) {
			try {
				TimeUnit.SECONDS.sleep(1);
				if (i == 1) {
					flag = false;
				}
				System.out.println(i + "s后立即引爆，请尽快撤离...");
				i--;
			} catch (InterruptedException e) {
			}
		}
	}
}
