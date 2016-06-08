package com.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.demo.mode.User;

@Repository
public class UserDao {

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	public User getUserByMobile(String mobile) {
		System.out.println("执行DB查询...");
		try {
			String sql = "SELECT * FROM `USER` WHERE MOBILE = ?";
			return jdbcTemplate.queryForObject(sql, new Object[] { mobile }, new RowMapper<User>() {
				@Override
				public User mapRow(ResultSet rs, int rowNum) throws SQLException {
					User user = new User();
					user.setMobile(rs.getString("mobile"));
					user.setName(rs.getString("name"));
					return user;
				}
			});
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public void updateNameByMobile(String name, String mobile) {
		jdbcTemplate.update("UPDATE USER SET NAME = ? WHERE MOBILE = ?", name, mobile);
	}
}
