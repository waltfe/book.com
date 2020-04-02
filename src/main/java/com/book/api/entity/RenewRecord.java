package com.book.api.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.book.util.BookConstant;

import lombok.Data;

@Data
public class RenewRecord {

	@TableId(type = IdType.AUTO)
	private Integer id;
	private Integer lendRecordId;
	private Integer renewDays;
	private Short status;

	@TableField(exist = false)
	private String username;
	@TableField(exist = false)
	private String bookName;
	@TableField(exist = false)
	private Date borrowTime;
	@TableField(exist = false)
	private Date deadline;

	public String getStatusName() {
		switch (status) {
		case BookConstant.RENEW_STATUS_APPROVING:
			return "审核中";
		case BookConstant.RENEW_STATUS_ACCEPTED:
			return "已通过";
		case BookConstant.RENEW_STATUS_NOT_ACCEPTED:
			return "不通过";
		default:
			return "";
		}
	}

}
