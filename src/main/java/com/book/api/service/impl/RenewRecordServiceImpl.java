package com.book.api.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.book.api.entity.RenewRecord;
import com.book.api.mapper.RenewRecordMapper;
import com.book.api.service.IRenewRecordService;

@Service
public class RenewRecordServiceImpl extends ServiceImpl<RenewRecordMapper, RenewRecord> implements IRenewRecordService{

	@Override
	public List<RenewRecord> selectListWithLendRecord(Wrapper<RenewRecord> query) {
		return this.getBaseMapper().selectListWithLendRecord(query);
	}

}
