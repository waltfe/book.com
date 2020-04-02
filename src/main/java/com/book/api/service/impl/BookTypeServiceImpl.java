package com.book.api.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.book.api.entity.BookType;
import com.book.api.mapper.BookTypeMapper;
import com.book.api.service.IBookTypeService;

@Service
public class BookTypeServiceImpl extends ServiceImpl<BookTypeMapper, BookType> implements IBookTypeService{

	@Override
	public List<BookType> selectListWithLocation(Wrapper<BookType> query) {
		return this.getBaseMapper().selectListWithLocation(query);
	}
	
}