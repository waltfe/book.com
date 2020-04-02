package com.book.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.book.api.entity.BookcaseInfo;
import com.book.api.service.IBookcaseInfoService;
import com.book.common.BaseController;
import com.book.common.JsonResult;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("api/bookcaseInfo")
public class BookcaseInfoController extends BaseController {

	@Autowired
	private IBookcaseInfoService bookcaseInfoService;

	/**
	 * 查看书本储存列表
	 * 
	 * @return
	 */
	@RequestMapping("list")
	public JsonResult list() {

		this.startPage();
		List<BookcaseInfo> list = bookcaseInfoService.list();
		PageInfo<BookcaseInfo> pageInfo = new PageInfo<>(list);

		return JsonResult.success(pageInfo);
	}

	/**
	 * 添加或修改书本存储记录
	 * 
	 * @param bookType
	 * @return
	 */
	@RequestMapping("save")
	public JsonResult save(@RequestBody BookcaseInfo bookcaseInfo) {
		bookcaseInfoService.saveOrUpdate(bookcaseInfo);
		return JsonResult.success();
	}

	/**
	 * 通过主键删除书本储存点
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("delete")
	public JsonResult delete(int id) {
		bookcaseInfoService.removeById(id);
		return JsonResult.success();
	}

}
