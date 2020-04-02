package com.book.api.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

@Data
public class Announcement {
	
	@TableId(type = IdType.AUTO)
    private Integer id;
    private String content;
    private Date createTime;
    
}