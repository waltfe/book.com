package com.book.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

@Data
public class BookcaseInfo {

	@TableId(type = IdType.AUTO)
	private Integer id;
	private Integer bookTypeId;
	private String location;

}