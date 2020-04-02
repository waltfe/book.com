package com.book.api.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.book.api.entity.Announcement;
import com.book.api.mapper.AnnouncementMapper;
import com.book.api.service.IAnnouncementService;

@Service
public class AnnouncementServiceImpl extends ServiceImpl<AnnouncementMapper, Announcement> implements IAnnouncementService{

}