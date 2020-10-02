package com.fairyland.xrobot.modular.xrobot.autoxit.server;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import io.netty.channel.Channel;

public class SessionChannel implements Serializable {
	private static final long serialVersionUID = 8269505210699191258L;

	private String id;
	private String token;
	private String phone;
	private String account;
	private String account1;
	private String status;
	private long createTime;
	private Channel channel;

	public SessionChannel() {
		this.createTime = System.currentTimeMillis();
	}

	public SessionChannel(String id, String token, String phone, String account,String account1, String status,
			Channel channel) {
		this.id = id;
		this.token = token;
		this.phone = phone;
		this.account = account;
		this.account1 = account1;
		this.status = status;
		this.createTime = System.currentTimeMillis();
		this.channel = channel;

	}
	

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return this.token;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
	public String getAccount() {
		return account;
	}

	
	public void setAccount1(String account1) {
		this.account1 = account1;
	}
	
	public String getAccount1() {
		return account1;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getCreateTime() {
		return this.createTime;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public Channel getChannel() {
		return this.channel;
	}

	public boolean isActive() {
		if (channel != null && channel.isActive())
			return true;
		return false;
	}

	public void close() {
		if (channel != null) {
			channel.close();
		}
	}

	public Map<String, Object> getSignature() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", this.id);
		map.put("token", this.token);
		map.put("phone", this.phone);
		map.put("account", this.account);
		map.put("account1", this.account1);
		map.put("createTime", this.createTime);
		return map;
	}

	public int hashCode() {
		return (id + token + phone + account + account1 + status + createTime + channel.toString()).hashCode();
	}

	public boolean equals(Object o) {

		if (o instanceof Session) {
			return hashCode() == o.hashCode();
		}
		return false;
	}
}
