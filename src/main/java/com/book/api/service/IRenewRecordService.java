package com.book.api.service;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.book.api.entity.RenewRecord;

public interface IRenewRecordService extends IService<RenewRecord>{

	/**
	 * 联表查询借阅记录
	 */
	List<RenewRecord> selectListWithLendRecord(Wrapper<RenewRecord> query);

}
