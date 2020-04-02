package com.book.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.book.api.entity.LendRecord;
import com.book.api.entity.OverLimitRecord;
import com.book.api.mapper.OverLimitRecordMapper;
import com.book.api.service.ILendRecordService;
import com.book.api.service.IOverLimitRecordService;

@Service
public class OverLimitRecordServiceImpl extends ServiceImpl<OverLimitRecordMapper, OverLimitRecord> implements IOverLimitRecordService{

	@Autowired
	private ILendRecordService lendRecordService;
	@Autowired
	private IOverLimitRecordService overLimitRecordService;
	
	@Override
	public List<OverLimitRecord> selectListWithLendRecord(Wrapper<OverLimitRecord> query) {
		return this.getBaseMapper().selectListWithLendRecord(query);
	}

	@Override
	@Transactional
	public void refreshOverLimitRecord() {
		
		//查询出逾期借阅记录
		List<LendRecord> lendRecords = lendRecordService.getOverLimitlist();
		//遍历生成逾期记录对象
		List<OverLimitRecord> overLimitRecords = new ArrayList<>();
		for (LendRecord lendRecord : lendRecords) {
			OverLimitRecord overLimitRecord = new OverLimitRecord();
			overLimitRecord.setUsername(lendRecord.getUsername());
			overLimitRecord.setMobile(lendRecord.getMobile());
			overLimitRecord.setBookName(lendRecord.getBookName());
			overLimitRecord.setLendRecordId(lendRecord.getId());
			overLimitRecord.setUserId(lendRecord.getUserId());
			overLimitRecords.add(overLimitRecord);
		}
		//批量插入逾期记录
		overLimitRecordService.saveBatch(overLimitRecords);
		
	}

}