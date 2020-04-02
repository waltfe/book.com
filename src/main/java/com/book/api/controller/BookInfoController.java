package com.book.api.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.book.api.entity.BookInfo;
import com.book.api.service.IBookInfoService;
import com.book.common.BaseController;
import com.book.common.JsonResult;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("api/bookInfo")
public class BookInfoController extends BaseController {

	@Autowired
	private IBookInfoService bookInfoService;

	/**
	 * 查看书本列表
	 * 
	 * @return
	 */
	@RequestMapping("list")
	public JsonResult list(@RequestBody BookInfo bookInfo) {
		
		this.startPage();
		
		//判断前端传入查询条件，如果传入值非空则加入查询条件
		LambdaQueryWrapper<BookInfo> query = Wrappers.<BookInfo>lambdaQuery();
		query.like(StringUtils.isNotBlank(bookInfo.getName()), BookInfo::getName, bookInfo.getName());
		query.like(StringUtils.isNotBlank(bookInfo.getAuthor()), BookInfo::getAuthor, bookInfo.getAuthor());
		query.eq(bookInfo.getBookTypeId() != null, BookInfo::getBookTypeId, bookInfo.getBookTypeId());
		query.eq(StringUtils.isNotBlank(bookInfo.getLanguage()), BookInfo::getLanguage, bookInfo.getLanguage());
		
		List<BookInfo> list = bookInfoService.selectListWithLocation(query);
		PageInfo<BookInfo> pageInfo = new PageInfo<>(list);

		return JsonResult.success(pageInfo);
	}

	/**
	 * 添加或修改书本
	 * 
	 * @param bookType
	 * @return
	 */
	@RequestMapping("save")
	public JsonResult save(@RequestBody BookInfo bookInfo) {
		bookInfoService.saveOrUpdate(bookInfo);
		return JsonResult.success();
	}

	/**
	 * 通过主键删除书本
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("delete")
	public JsonResult delete(int id) {
		bookInfoService.removeById(id);
		return JsonResult.success();
	}
	
	/**
	 * 获取详细信息
	 * @return
	 */
	@RequestMapping("getOne")
	public JsonResult getOne(int id) {
		BookInfo record = bookInfoService.getById(id);
		return JsonResult.success(record);
	}
	
	/**
	 * 获取详细信息联表查询书目类型,储存位置
	 * @return
	 */
	@RequestMapping("getDetail")
	public JsonResult getDetail(int id) {
		BookInfo record = bookInfoService.getByIdWithTypeAndLocation(id);
		return JsonResult.success(record);
	}
	
	/**
	 * 借书
	 * @param bookId
	 * @param borrowDays
	 * @return
	 */
	@RequestMapping("borrow")
	public JsonResult borrow(int bookId, int borrowDays) {
		bookInfoService.borrow(bookId,borrowDays);
		return JsonResult.success();
	}
	
	/**
	 * 还书
	 * @param bookId
	 * @return
	 */
	@RequestMapping("returnBook")
	public JsonResult returnBook(int lendRecordId) {
		bookInfoService.returnBook(lendRecordId);
		return JsonResult.success();
	}

}
