package com.book.api.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.book.api.entity.BookType;
import com.book.api.entity.BookcaseInfo;
import com.book.api.service.IBookTypeService;
import com.book.api.service.IBookcaseInfoService;
import com.book.common.BaseController;
import com.book.common.JsonResult;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("api/bookType")
public class BookTypeController extends BaseController {

	@Autowired
	private IBookTypeService bookTypeService;
	@Autowired
	private IBookcaseInfoService bookcaseInfoService;

	/**
	 * 获取书本类别列表
	 * 
	 * @return
	 */
	@RequestMapping("list")
	public JsonResult list(String name) {

		this.startPage();
		//判断前端传入查询条件，如果传入值非空则加入查询条件
		LambdaQueryWrapper<BookType> query = Wrappers.lambdaQuery();
		query.like(StringUtils.isNoneBlank(name), BookType::getName, name);
		List<BookType> list = bookTypeService.selectListWithLocation(query);

		PageInfo<BookType> pageInfo = new PageInfo<>(list);
		return JsonResult.success(pageInfo);

	}
	
	/**
	 * 获取所有类型
	 * @return
	 */
	@RequestMapping("getAllType")
	public JsonResult AllType() {
		List<BookType> list = bookTypeService.list();
		return JsonResult.success(list);
	}

	/**
	 * 添加或修改书本类型
	 * 
	 * @param bookType
	 * @return
	 */
	@RequestMapping("save")
	public JsonResult save(@RequestBody BookType bookType) {
		
		if(bookType.getId() != null) {
			//清空储存地点
			bookcaseInfoService.remove(Wrappers.<BookcaseInfo>lambdaQuery().eq(BookcaseInfo::getBookTypeId, bookType.getId()));
		}
		bookTypeService.saveOrUpdate(bookType);
		//重新保存储存地点
		List<BookcaseInfo> bookcaseList = bookType.getBookcaseList();
		for (BookcaseInfo bookcaseInfo : bookcaseList) {
			bookcaseInfo.setId(null);
			bookcaseInfo.setBookTypeId(bookType.getId());
		}
		bookcaseInfoService.saveOrUpdateBatch(bookType.getBookcaseList());
		return JsonResult.success();
	}

	/**
	 * 获取详细信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("getOne")
	public JsonResult getOne(int id) {
		
		//查询书本类型及储存位置
		BookType record = bookTypeService.getById(id);
		List<BookcaseInfo> bookcaseList = bookcaseInfoService
				.list(Wrappers.<BookcaseInfo>lambdaQuery().eq(BookcaseInfo::getBookTypeId, record.getId()));
		record.setBookcaseList(bookcaseList);
		return JsonResult.success(record);
	}

	/**
	 * 通过主键删除书本类型
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("delete")
	public JsonResult delete(int id) {
		bookTypeService.removeById(id);
		return JsonResult.success();
	}

}
