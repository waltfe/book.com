package com.book.api.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.book.api.entity.BookInfo;
import com.book.api.entity.LendRecord;
import com.book.api.entity.UserInfo;
import com.book.api.mapper.BookInfoMapper;
import com.book.api.service.IBookInfoService;
import com.book.api.service.ILendRecordService;
import com.book.common.LogicException;
import com.book.util.BookConstant;
import com.book.util.SpringUtil;

@Service
public class BookInfoServiceImpl extends ServiceImpl<BookInfoMapper, BookInfo> implements IBookInfoService {

	@Autowired
	private ILendRecordService lendRecordService;

	@Override
	public List<BookInfo> selectListWithLocation(Wrapper<BookInfo> query) {
		return this.getBaseMapper().selectListWithLocation(query);
	}

	@Override
	public BookInfo getByIdWithTypeAndLocation(int id) {
		return this.getBaseMapper().getByIdWithTypeAndLocation(id);
	}

	@Override
	@Transactional
	public void borrow(int bookId, int borrowDays) {
		// 查询书本是否已被借出
		BookInfo book = this.getById(bookId);
		if (book.getStatus() != BookConstant.BOOK_STATUS_NOT_BORROWING) {
			throw new LogicException("该书籍已被借出！");
		}

		// 修改书本状态
		book.setStatus(BookConstant.BOOK_STATUS_BORROWING);
		this.updateById(book);

		UserInfo user = (UserInfo) SpringUtil.getSession().getAttribute(BookConstant.USER_SESSION_KEY);
		
		// 添加借书记录
		LendRecord lendRecord = new LendRecord();
		lendRecord.setUsername(user.getUsername());
		lendRecord.setBookName(book.getName());
		lendRecord.setUserId(user.getId());
		lendRecord.setBookId(bookId);
		lendRecord.setStatus(BookConstant.LEND_STATUS_BORROWING);
		Date now = new Date();
		lendRecord.setBorrowTime(now);
		Date deadline = new Date(now.getTime() + (borrowDays * 24 * 60 * 60 * 1000l));
		lendRecord.setDeadline(deadline);

		lendRecordService.save(lendRecord);
	}

	@Override
	@Transactional
	public void returnBook(int lendRecordId) {
		
		LendRecord lendRecord = lendRecordService.getById(lendRecordId);
		lendRecord.setReturnTime(new Date());
		lendRecord.setStatus(BookConstant.LEND_STATUS_RETURNED);
		lendRecordService.updateById(lendRecord);
		
		Integer bookId = lendRecord.getBookId();
		BookInfo bookInfo = this.getById(bookId);
		bookInfo.setStatus(BookConstant.BOOK_STATUS_NOT_BORROWING);
		this.updateById(bookInfo);
	}

}