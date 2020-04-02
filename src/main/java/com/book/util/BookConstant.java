package com.book.util;

public interface BookConstant {

	public static final String USER_SESSION_KEY = "user_info";
	
	//用户职位
	public static final int ROLE_ADMIN = 1; //管理员
	public static final int ROLE_USER = 2; //用户
	
	//用户状态
	public static final int USER_STATUS_NORAML = 1;//正常
	public static final int USER_STATUS_FROZEN = 2;//冻结
	
	//续借状态
	public static final short RENEW_STATUS_APPROVING = 1; //审核中
	public static final short RENEW_STATUS_ACCEPTED = 2; //已通过
	public static final short RENEW_STATUS_NOT_ACCEPTED = 3; //不通过
	
	//书本状态
	public static final short BOOK_STATUS_NOT_BORROWING = 0; //未借出
	public static final short BOOK_STATUS_BORROWING = 1; //已借出
	
	//借书状态
	public static final short LEND_STATUS_BORROWING = 1; //未还
	public static final short LEND_STATUS_RETURNED = 2; //已还
	
}
