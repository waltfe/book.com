package com.book.api.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.book.api.entity.BookcaseInfo;
import com.book.api.mapper.BookcaseInfoMapper;
import com.book.api.service.IBookcaseInfoService;

@Service
public class BookcaseInfoServiceImpl extends ServiceImpl<BookcaseInfoMapper, BookcaseInfo> implements IBookcaseInfoService{

}