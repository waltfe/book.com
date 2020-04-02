package com.book.api.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.book.api.entity.UserInfo;
import com.book.api.service.IUserInfoService;
import com.book.common.BaseController;
import com.book.common.JsonResult;
import com.book.common.LogicException;
import com.book.util.BookConstant;
import com.book.util.ValidatorUtil;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("api/userInfo")
public class UserInfoController extends BaseController {

	@Autowired
	private IUserInfoService userSerivce;

	/**
	 * 查看用户列表
	 * 
	 * @return
	 */
	@RequestMapping("list")
	public JsonResult list(String username) {

		this.startPage();
		LambdaQueryWrapper<UserInfo> query = Wrappers.lambdaQuery();
		//判断前端传入用户名作为查询条件，如果传入值非空则加入查询条件
		query.like(StringUtils.isNotBlank(username), UserInfo::getUsername, username);
		List<UserInfo> list = userSerivce.list(query);
		PageInfo<UserInfo> pageInfo = new PageInfo<>(list);

		return JsonResult.success(pageInfo);
	}

	@RequestMapping("delete")
	public JsonResult delete(int id) {
		userSerivce.removeById(id);
		return JsonResult.success();
	}

	/**
	 * 登录
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping("login")
	public JsonResult login(String username, String password) {

		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			throw new LogicException("用户名/密码不能为空!");
		}
		//根据传入用户名、密码查询数据库
		UserInfo userInfo = userSerivce.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUsername, username)
				.eq(UserInfo::getPassword, password));

		if (userInfo != null) {
			this.getSession().setAttribute(BookConstant.USER_SESSION_KEY, userInfo);
			return JsonResult.success(userInfo);
		} else {
			return JsonResult.fail("用户名或密码错误!");
		}

	}

	/**
	 * 注册
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("register")
	public JsonResult register(@RequestBody UserInfo user) {

		String username = user.getUsername();
		String mobile = user.getMobile();

		//使用数据校验工具类校验传入字段的格式
		if (!ValidatorUtil.isUsername(username)) {
			throw new LogicException("用户名格式不正确!");
		}
		if (!ValidatorUtil.isPassword(user.getPassword())) {
			throw new LogicException("密码格式不正确!");
		}
		if (!ValidatorUtil.isMobile(mobile)) {
			throw new LogicException("手机号码格式不正确!");
		}

		// 查询同样用户名数量
		int count = userSerivce.count(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUsername, username));

		if (count > 0) {
			throw new LogicException("用户名已被注册!");
		}

		// 查询同样手机号码数量
		count = userSerivce.count(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getMobile, mobile));

		if (count > 0) {
			throw new LogicException("手机号码已被注册!");
		}

		// 设置职位并插入数据
		user.setRole(BookConstant.ROLE_USER);
		userSerivce.save(user);

		return JsonResult.success(user);

	}

	/**
	 * 注销
	 * 
	 * @return
	 */
	@RequestMapping("logout")
	public JsonResult logout() {
		//清除session中存储的用户信息
		this.getSession().setAttribute(BookConstant.USER_SESSION_KEY, null);
		return JsonResult.success();
	}

	/**
	 * 修改用户状态
	 * 
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping("setUserStatus")
	public JsonResult setUserStatus(int id, short status) {

		UserInfo user = userSerivce.getById(id);
		user.setStatus(status);
		userSerivce.updateById(user);

		return JsonResult.success();
	}

}
