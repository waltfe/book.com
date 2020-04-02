package com.book.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.book.api.entity.RenewRecord;

public interface RenewRecordMapper extends BaseMapper<RenewRecord>{

	@Select("select a.*,b.* from renew_record a left join lend_record b on a.lend_record_id = b.id ${ew.customSqlSegment}")
	List<RenewRecord> selectListWithLendRecord(@Param(Constants.WRAPPER) Wrapper<RenewRecord> query);

}
