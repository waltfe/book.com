package com.book.api.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.book.api.entity.OverLimitRecord;
import com.book.api.service.IOverLimitRecordService;
import com.book.common.BaseController;
import com.book.common.JsonResult;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("api/overLimitRecord")
public class OverLimitRecordController extends BaseController{


	@Autowired
	private IOverLimitRecordService overLimitRecordService;

	/**
	 * 查看逾期记录列表
	 * 
	 * @return
	 */
	@RequestMapping("list")
	public JsonResult list(@RequestBody OverLimitRecord queryData) {
		
		//判断前端传入查询条件，如果传入值非空则加入查询条件
		QueryWrapper<OverLimitRecord> query = Wrappers.query();
		query.like(StringUtils.isNoneBlank(queryData.getUsername()), "a.username", queryData.getUsername());
		query.like(StringUtils.isNoneBlank(queryData.getBookName()), "a.book_name", queryData.getBookName());
		query.eq(queryData.getStatus() != null, "b.status", queryData.getStatus());

		this.startPage();
		List<OverLimitRecord> list = overLimitRecordService.selectListWithLendRecord(query);
		PageInfo<OverLimitRecord> pageInfo = new PageInfo<>(list);

		return JsonResult.success(pageInfo);
	}

	/**
	 * 查看用户逾期记录
	 * 
	 * @return
	 */
	@RequestMapping("myList")
	public JsonResult myList(@RequestBody OverLimitRecord queryData) {

		QueryWrapper<OverLimitRecord> query = Wrappers.query();
		//添加用户ID作为查询条件
		query.eq("a.user_id", this.getUser().getId());
		//判断前端传入查询条件，如果传入值非空则加入查询条件
		query.like(StringUtils.isNoneBlank(queryData.getUsername()), "a.username", queryData.getUsername());
		query.like(StringUtils.isNoneBlank(queryData.getBookName()), "a.book_name", queryData.getBookName());
		query.eq(queryData.getStatus() != null, "b.status", queryData.getStatus());

		this.startPage();
		List<OverLimitRecord> list = overLimitRecordService.selectListWithLendRecord(query);
		PageInfo<OverLimitRecord> pageInfo = new PageInfo<>(list);

		return JsonResult.success(pageInfo);
	}
	
}
