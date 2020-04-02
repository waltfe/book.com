package com.book.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.book.api.service.IOverLimitRecordService;

@Component
@EnableScheduling
public class OverLimitRecordRefreshJob {
	
	@Autowired
	private IOverLimitRecordService overLimitRecordService;

	/**
	 * 定时刷新逾期借阅记录
	 */
	@Scheduled(cron = "0 */1 * * * ?")
	public void run() {
		//调用service层方法批量查询插入逾期记录
		overLimitRecordService.refreshOverLimitRecord();
	}
	
}
