package com.book.api.service;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.book.api.entity.BookInfo;

public interface IBookInfoService extends IService<BookInfo>{

	/**
	 * 联表查询位置信息
	 * @param query
	 * @return
	 */
	List<BookInfo> selectListWithLocation(Wrapper<BookInfo> query);

	/**
	 * 联表查询类型&位置
	 * @param id
	 * @return
	 */
	BookInfo getByIdWithTypeAndLocation(int id);

	/**
	 * 借书
	 * @param bookId
	 * @param borrowDays
	 */
	void borrow(int bookId, int borrowDays);

	/**
	 * 还书
	 * @param lendRecordId
	 */
	void returnBook(int lendRecordId);

}
