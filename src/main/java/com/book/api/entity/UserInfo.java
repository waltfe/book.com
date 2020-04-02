package com.book.api.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.book.util.BookConstant;

import lombok.Data;

@Data
public class UserInfo {

	@TableId(type = IdType.AUTO)
	private Integer id;
	private String username;
	private String password;
	private String mobile;
	private Integer role;
	private Date registerTime;
	private Short status;

	public String getRoleName() {
		return this.role == BookConstant.ROLE_ADMIN ? "管理员" : "用户";
	}

	public String getStatusName() {
		return this.status == BookConstant.USER_STATUS_NORAML ? "正常" : "冻结";
	}

}