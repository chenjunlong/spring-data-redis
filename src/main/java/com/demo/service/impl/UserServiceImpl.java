package com.demo.service.impl;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.demo.dao.UserDao;
import com.demo.mode.User;
import com.demo.service.UserServices;

@CacheConfig(cacheNames = "user:cache")
@Service("userService")
public class UserServiceImpl implements UserServices {

	@Resource(name = "userDao")
	private UserDao userDao;

	@Cacheable(key = "'user:cache:'+#mobile", condition = "#mobile ne null and #mobile ne ''", unless = "#result eq '' or #result eq null")
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	@Override
	public User getUserByMobile(String mobile) {
		System.out.println("execute getUserByMobile start...");
		User user = userDao.getUserByMobile(mobile);
		System.out.println("execute getUserByMobile end...");
		return user;
	}

	@CacheEvict(key = "'user:cache:'+#mobile")
	// allEntries=true 清空该集合全部缓存
	// @CacheEvict(key = "'user:cache'+#mobile",allEntries=true)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	@Override
	public void updateNameByMobile(String name, String mobile) {
		userDao.updateNameByMobile(name, mobile);
	}
}