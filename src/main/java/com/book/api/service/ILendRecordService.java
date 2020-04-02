package com.book.api.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.book.api.entity.LendRecord;

public interface ILendRecordService extends IService<LendRecord>{

	/**
	 * 获取借阅超时记录
	 * @return
	 */
	List<LendRecord> getOverLimitlist();

}
