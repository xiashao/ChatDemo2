package com.example.xinxin.chatdemo.tool;

public interface OnSocketLisenter<T> {
	/**
	 * socket请求成功后返回数据
	 * 
	 * @param object
	 */
	void OnSuccess(T object);

	/**
	 * socket请求失败后返回数据
	 * 
	 * @param object
	 */
	void OnFail(T object);
}
