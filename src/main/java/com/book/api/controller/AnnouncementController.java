package com.book.api.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.book.api.entity.Announcement;
import com.book.api.service.IAnnouncementService;
import com.book.common.BaseController;
import com.book.common.JsonResult;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("api/announcement")
public class AnnouncementController extends BaseController {

	@Autowired
	private IAnnouncementService announcementService;

	/**
	 * 查看公告列表
	 * 
	 * @return
	 */
	@RequestMapping("list")
	public JsonResult list() {

		//根据创建时间倒序查询
		this.startPage();
		List<Announcement> list = announcementService.list(Wrappers.<Announcement>query().orderByDesc("create_time"));
		PageInfo<Announcement> pageInfo = new PageInfo<>(list);

		return JsonResult.success(pageInfo);
	}

	/**
	 * 添加或修改公告
	 * 
	 * @param announcement
	 * @return
	 */
	@RequestMapping("save")
	public JsonResult save(@RequestBody Announcement announcement) {

		//如果为新添加记录，则设置创建时间
		if (announcement.getId() == null) {
			announcement.setCreateTime(new Date());
		}
		announcementService.saveOrUpdate(announcement);
		return JsonResult.success();
	}

	/**
	 * 通过主键删除公告
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("delete")
	public JsonResult delete(int id) {
		announcementService.removeById(id);
		return JsonResult.success();
	}

	/**
	 * 获取详细信息
	 * 
	 * @return
	 */
	@RequestMapping("getOne")
	public JsonResult getOne(int id) {
		Announcement record = announcementService.getById(id);
		return JsonResult.success(record);
	}

}
