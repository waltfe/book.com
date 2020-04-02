package com.book.api.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.book.api.entity.LendRecord;
import com.book.api.entity.UserInfo;
import com.book.api.service.ILendRecordService;
import com.book.api.service.IUserInfoService;
import com.book.common.BaseController;
import com.book.common.JsonResult;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("api/lendRecord")
public class LendRecordController extends BaseController {

	@Autowired
	private ILendRecordService lendRecordService;
	@Autowired
	private IUserInfoService userInfoService;

	/**
	 * 查看借阅记录列表
	 * 
	 * @return
	 */
	@RequestMapping("list")
	public JsonResult list(@RequestBody LendRecord queryData) {

		//判断前端传入查询条件，如果传入值非空则加入查询条件
		LambdaQueryWrapper<LendRecord> query = Wrappers.lambdaQuery();
		query.like(StringUtils.isNoneBlank(queryData.getUsername()), LendRecord::getUsername, queryData.getUsername());
		query.like(StringUtils.isNoneBlank(queryData.getBookName()), LendRecord::getBookName, queryData.getBookName());
		query.like(queryData.getStatus() != null, LendRecord::getStatus, queryData.getStatus());

		this.startPage();
		List<LendRecord> list = lendRecordService.list(query);
		PageInfo<LendRecord> pageInfo = new PageInfo<>(list);

		return JsonResult.success(pageInfo);
	}

	/**
	 * 查看用户借阅记录
	 * 
	 * @return
	 */
	@RequestMapping("myList")
	public JsonResult myList(@RequestBody LendRecord queryData) {

		
		UserInfo user = this.getUser();
		LambdaQueryWrapper<LendRecord> query = Wrappers.lambdaQuery();
		//插入用户ID作为查询条件
		query.eq(LendRecord::getUserId, user.getId());
		//判断前端传入查询条件，如果传入值非空则加入查询条件
		query.like(StringUtils.isNoneBlank(queryData.getUsername()), LendRecord::getUsername, queryData.getUsername());
		query.like(StringUtils.isNoneBlank(queryData.getBookName()), LendRecord::getBookName, queryData.getBookName());
		query.like(queryData.getStatus() != null, LendRecord::getStatus, queryData.getStatus());

		this.startPage();
		List<LendRecord> list = lendRecordService.list(query);
		PageInfo<LendRecord> pageInfo = new PageInfo<>(list);

		return JsonResult.success(pageInfo);
	}

	/**
	 * 添加或修改借阅记录
	 * 
	 * @param bookType
	 * @return
	 */
	@RequestMapping("save")
	public JsonResult save(@RequestBody LendRecord lendRecord) {
		lendRecordService.saveOrUpdate(lendRecord);
		return JsonResult.success();
	}

	/**
	 * 通过主键删除借阅记录
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("delete")
	public JsonResult delete(int id) {
		lendRecordService.removeById(id);
		return JsonResult.success();
	}

	/**
	 * 获取详细信息
	 * 
	 * @return
	 */
	@RequestMapping("getOne")
	public JsonResult getOne(int id) {

		//查询借书记录，并查处借书人信息
		LendRecord record = lendRecordService.getById(id);
		UserInfo userInfo = userInfoService.getById(record.getUserId());
		record.setUserInfo(userInfo);

		return JsonResult.success(record);
	}

}
