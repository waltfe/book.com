package com.book.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.book.api.entity.BookType;

public interface BookTypeMapper extends BaseMapper<BookType>{

	@Select("select a.*,(select GROUP_CONCAT(location) from bookcase_info b "
			+ "where b.book_type_id = a.id ) as location from book_type a ${ew.customSqlSegment}")
	List<BookType> selectListWithLocation(@Param(Constants.WRAPPER) Wrapper<BookType> query);

}
