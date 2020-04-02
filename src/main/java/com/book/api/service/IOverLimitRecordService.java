package com.book.api.service;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.book.api.entity.OverLimitRecord;

public interface IOverLimitRecordService extends IService<OverLimitRecord>{

	/**
	 * 联表查询借阅记录
	 * @param query
	 * @return
	 */
	List<OverLimitRecord> selectListWithLendRecord(Wrapper<OverLimitRecord> query);

	/**
	 * 更新逾期记录
	 */
	void refreshOverLimitRecord();

}
