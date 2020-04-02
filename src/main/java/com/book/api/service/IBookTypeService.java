package com.book.api.service;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.book.api.entity.BookType;

public interface IBookTypeService extends IService<BookType>{

	/**
	 * 连表查出存放地点
	 * @param query
	 * @return
	 */
	List<BookType> selectListWithLocation(Wrapper<BookType> query);
	
}
