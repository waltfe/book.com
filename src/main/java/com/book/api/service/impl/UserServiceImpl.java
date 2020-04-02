package com.book.api.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.book.api.entity.UserInfo;
import com.book.api.mapper.UserInfoMapper;
import com.book.api.service.IUserInfoService;

@Service
public class UserServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService{

}
