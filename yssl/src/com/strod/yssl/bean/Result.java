package com.strod.yssl.bean;

/**
 * base result
 * @author user
 *
 */
public class Result {

	protected int ret_code;
	protected String ret_msg;

	public Result() {
		super();
	}

	public Result(int ret_code, String ret_msg) {
		super();
		this.ret_code = ret_code;
		this.ret_msg = ret_msg;
	}

	public int getRet_code() {
		return ret_code;
	}

	public void setRet_code(int ret_code) {
		this.ret_code = ret_code;
	}

	public String getRet_msg() {
		return ret_msg;
	}

	public void setRet_msg(String ret_msg) {
		this.ret_msg = ret_msg;
	}

	@Override
	public String toString() {
		return "Retsult [ret_code=" + ret_code + ", ret_msg=" + ret_msg + "]";
	}

}
