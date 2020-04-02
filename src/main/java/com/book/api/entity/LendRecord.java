package com.book.api.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.book.util.BookConstant;

import lombok.Data;

@Data
public class LendRecord {
	
	@TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer bookId;
    private String username;
    private String bookName;
    private Date borrowTime;
    private Date returnTime;
    private Date deadline;
    private Short status;
    
    @TableField(exist = false)
    private String mobile;
    @TableField(exist = false)
    private UserInfo userInfo;//借书人信息
    
    public String getStatusName() {
    	return this.status == BookConstant.LEND_STATUS_BORROWING ? "未归还":"已归还"; 
    }

}