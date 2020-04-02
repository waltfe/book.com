package com.book.api.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.book.util.BookConstant;

import lombok.Data;

@Data
public class OverLimitRecord {

	@TableId(type = IdType.AUTO)
	private Integer id;
	private Integer lendRecordId;
	private String bookName;
	private Integer userId;
	private String username;
	private String mobile;
	
	@TableField(exist = false)
	private Short status;
	@TableField(exist = false)
	private Date borrowTime;
	@TableField(exist = false)
	private Date deadline;
	@TableField(exist = false)
	private Date returnTime;
	
	public String getStatusName() {
    	return this.status == BookConstant.LEND_STATUS_BORROWING ? "未归还":"已归还"; 
    }
	
}
