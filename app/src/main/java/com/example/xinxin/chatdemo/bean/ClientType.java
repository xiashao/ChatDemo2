package com.example.xinxin.chatdemo.bean;

/**
 * 发送和接收端的类型
 * @author sixgod
 *
 */
public enum ClientType {
	
	Test(0), iPad(1), Worker(2), SYS(3), MCMS(4),Manager(5),Other(6),Android(7);
	private int _value;

	private ClientType(int value) {
		_value = value;
	}

	public int value() {
		return _value;
	}
}
