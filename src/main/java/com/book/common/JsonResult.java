package com.book.common;

public class JsonResult {

	private int code;
	private Object data;
	private String msg;

	public JsonResult(int errcode, Object data, String msg) {
		super();
		this.code = errcode;
		this.data = data;
		this.msg = msg;
	}

	public static JsonResult success(Object data, String msg) {
		return new JsonResult(200, data, msg);
	}

	public static JsonResult success(Object data) {
		return new JsonResult(200, data, null);
	}

	public static JsonResult success() {
		return new JsonResult(200, null, null);
	}

	public static JsonResult fail(int errcode, String msg) {
		return new JsonResult(errcode, null, msg);
	}

	public static JsonResult fail(String msg) {
		return new JsonResult(500, null, msg);
	}

	public static JsonResult fail() {
		return new JsonResult(500, null, null);
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
