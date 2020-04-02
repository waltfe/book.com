package com.book.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.book.api.entity.LendRecord;

public interface LendRecordMapper extends BaseMapper<LendRecord>{

	@Select("select a.*,(select mobile from user_info where id = a.user_id) as mobile "
			+ "from lend_record a where return_time is null "
			+ "and deadline <= DATE_SUB(sysdate(),INTERVAL 1 DAY) "
			+ "and not exists(select 1 from over_limit_record b where a.id = b.lend_record_id)")
	List<LendRecord> getOverLimitlist();

}
