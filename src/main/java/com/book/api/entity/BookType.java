package com.book.api.entity;

import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

@Data
public class BookType {
	
	@TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    
    @TableField(exist = false)
    private String location;
    @TableField(exist = false)
    private List<BookcaseInfo> bookcaseList;//书籍储存点列表

}