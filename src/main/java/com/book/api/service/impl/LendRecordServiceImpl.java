package com.book.api.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.book.api.entity.LendRecord;
import com.book.api.mapper.LendRecordMapper;
import com.book.api.service.ILendRecordService;

@Service
public class LendRecordServiceImpl extends ServiceImpl<LendRecordMapper, LendRecord> implements ILendRecordService{

	@Override
	public List<LendRecord> getOverLimitlist() {
		return this.getBaseMapper().getOverLimitlist();
	}

}