package com.book.api.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.book.api.entity.LendRecord;
import com.book.api.entity.RenewRecord;
import com.book.api.service.ILendRecordService;
import com.book.api.service.IRenewRecordService;
import com.book.common.BaseController;
import com.book.common.JsonResult;
import com.book.util.BookConstant;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("api/renewRecord")
public class RenewRecordController extends BaseController {

	@Autowired
	private IRenewRecordService renewRecordService;
	@Autowired
	private ILendRecordService lendRecordService;

	/**
	 * 查看续借记录
	 * 
	 * @return
	 */
	@RequestMapping("list")
	public JsonResult list(@RequestBody RenewRecord queryData) {

		//判断前端传入查询条件，如果传入值非空则加入查询条件
		QueryWrapper<RenewRecord> query = Wrappers.query();
		query.like(StringUtils.isNotBlank(queryData.getBookName()), "b.book_name", queryData.getBookName());
		query.like(StringUtils.isNotBlank(queryData.getUsername()), "b.username", queryData.getUsername());
		query.eq(queryData.getStatus() != null, "a.status", queryData.getStatus());

		this.startPage();
		List<RenewRecord> list = renewRecordService.selectListWithLendRecord(query);
		PageInfo<RenewRecord> pageInfo = new PageInfo<>(list);

		return JsonResult.success(pageInfo);
	}

	/**
	 * 查看我的续借记录
	 * 
	 * @return
	 */
	@RequestMapping("myList")
	public JsonResult myList(@RequestBody RenewRecord queryData) {

		QueryWrapper<RenewRecord> query = Wrappers.query();
		//添加用户ID作为查询条件
		query.eq("b.user_id", this.getUser().getId());
		//判断前端传入查询条件，如果传入值非空则加入查询条件
		query.like(StringUtils.isNotBlank(queryData.getBookName()), "b.book_name", queryData.getBookName());
		query.like(StringUtils.isNotBlank(queryData.getUsername()), "b.username", queryData.getUsername());
		query.eq(queryData.getStatus() != null, "a.status", queryData.getStatus());
		this.startPage();
		List<RenewRecord> list = renewRecordService.selectListWithLendRecord(query);
		PageInfo<RenewRecord> pageInfo = new PageInfo<>(list);

		return JsonResult.success(pageInfo);

	}

	/**
	 * 申请续借
	 * 
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping("renew")
	public JsonResult renew(int lendRecordId, int renewDays) {

		//查询借书记录对应的续借记录
		LambdaQueryWrapper<RenewRecord> query = Wrappers.lambdaQuery();
		query.eq(RenewRecord::getLendRecordId, lendRecordId);
		int count = renewRecordService.count(query);
		//如果对应的续借记录数量大于0，则不通过
		if (count > 0) {
			return JsonResult.fail("续借失败:一本书只能续借一次！");
		}

		// 生成续借记录并保存
		RenewRecord renewRecord = new RenewRecord();
		renewRecord.setLendRecordId(lendRecordId);
		renewRecord.setRenewDays(renewDays);
		renewRecord.setStatus(BookConstant.RENEW_STATUS_APPROVING);
		renewRecordService.save(renewRecord);

		return JsonResult.success();
	}

	/**
	 * 审核续借申请
	 * 
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping("approve")
	public JsonResult approve(int id, short status) {

		RenewRecord renewRecord = renewRecordService.getById(id);
		renewRecord.setStatus(status);

		// 如果审核状态为通过,添加租借时间
		if (status == BookConstant.RENEW_STATUS_ACCEPTED) {
			//获取借书记录
			LendRecord lendRecord = lendRecordService.getById(renewRecord.getLendRecordId());
			//计算出新的还书时间
			Date newDeadline = new Date(
					lendRecord.getDeadline().getTime() + (renewRecord.getRenewDays() * 24 * 60 * 60 * 1000l));
			lendRecord.setDeadline(newDeadline);
			//跟新借书记录
			lendRecordService.updateById(lendRecord);
		}

		renewRecordService.updateById(renewRecord);

		return JsonResult.success();
	}

}
