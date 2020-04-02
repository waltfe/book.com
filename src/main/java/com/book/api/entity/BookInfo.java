package com.book.api.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

@Data
public class BookInfo {

	@TableId(type = IdType.AUTO)
	private Integer id;
	private String name;
	private String author;
	private String isbn;
	private String language;
	private BigDecimal price;
	private Integer bookTypeId;
	private Short status;
	private String introduction;
	
	@TableField(exist = false)
	private String bookType;
	@TableField(exist = false)
	private String location;

}