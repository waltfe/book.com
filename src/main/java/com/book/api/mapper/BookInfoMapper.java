package com.book.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.book.api.entity.BookInfo;

public interface BookInfoMapper extends BaseMapper<BookInfo>{

	@Select("select a.*,(select GROUP_CONCAT(location) from bookcase_info b "
			+ "where b.book_type_id = a.book_type_id ) as location from book_info a ${ew.customSqlSegment}")
	List<BookInfo> selectListWithLocation(@Param(Constants.WRAPPER) Wrapper<BookInfo> query);
	
	@Select("select a.*,"
			+ "(select name from book_type b where b.id = a.book_type_id) as book_type, "
			+ "(select GROUP_CONCAT(location) from bookcase_info c where c.book_type_id = a.book_type_id) as location "
			+ "from book_info a where id = #{id}")
	BookInfo getByIdWithTypeAndLocation(@Param("id") int id);

}
