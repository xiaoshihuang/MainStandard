package com.xintai.xhao.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 解析的key的首字母一般默认的是小写。如果是大写的，那么就会出现解析失败的问题
 * 解决办法如下：在get方法上面添加注解@JSONField(name = "XX正确的key") 
 * @author Administrator
 */
public class A1 {
	private String UserId;
	private String Version;
	
	@JSONField(name = "UserId")
	public String getUserId() {
		return UserId;
	}
	public void setUserId(String userId) {
		UserId = userId;
	}
	
	@JSONField(name = "Version")
	public String getVersion() {
		return Version;
	}
	public void setVersion(String version) {
		Version = version;
	}

}
