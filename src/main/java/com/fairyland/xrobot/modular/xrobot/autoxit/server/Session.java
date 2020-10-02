package com.fairyland.xrobot.modular.xrobot.autoxit.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

import com.fairyland.xrobot.modular.xrobot.autoxit.server.core.Options;

public class Session implements Serializable {
	// private final static Logger log = LoggerFactory.getLogger(Session.class);

	private static final long serialVersionUID = 8269505210699191217L;
	private String id;
	private Map<String, SessionChannel> sessionChannelsMap = new ConcurrentHashMap<String, SessionChannel>();

	public Session() {

	}

	public Session(String id, String token, String phone, String account, String account1, 
			String status, Channel channel) {
		this.id = id;
		SessionChannel sessionChannel = new SessionChannel(id, token, phone, account, account1, status,
				channel);
		addSessionChannel(sessionChannel);
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public boolean addSessionChannel(SessionChannel sessionChannel) {
		if (StringUtils.isEmpty(getId()))
			return false;
		if (getId().equals(sessionChannel.getId())) {
			sessionChannelsMap.put(sessionChannel.getChannel().id().asShortText(), sessionChannel);
			return true;
		}
		return false;
	}

	public List<Channel> getChannels() {
		List<Channel> list = new ArrayList<Channel>();
		for (String key : sessionChannelsMap.keySet()) {
			SessionChannel sessionChannel = sessionChannelsMap.get(key);
			if (sessionChannel != null) {
				if (sessionChannel.getChannel() != null)
					list.add(sessionChannel.getChannel());
			}
		}
		return list;
	}

	public List<SessionChannel> getSessionChannels() {
		List<SessionChannel> list = new ArrayList<SessionChannel>();
		for (String key : sessionChannelsMap.keySet()) {
			SessionChannel sessionChannel = sessionChannelsMap.get(key);
			if (sessionChannel != null) {
				list.add(sessionChannel);
			}
		}
		return list;
	}

	public SessionChannel getSessionChannel(String nid) {
		SessionChannel sessionChannel = sessionChannelsMap.get(nid);
		return sessionChannel;
	}

	public boolean updateSessionChannel(SessionChannel sessionChannel) {
		if (sessionChannelsMap.get(sessionChannel.getChannel().id().asShortText()) != null) {
			sessionChannelsMap.put(sessionChannel.getChannel().id().asShortText(), sessionChannel);
			return true;
		}
		return false;
	}

	public boolean write(Object msg) {
		boolean success = false;
		if (sessionChannelsMap.size() > 0) {
			for (String key : sessionChannelsMap.keySet()) {
				SessionChannel sessionChannel = sessionChannelsMap.get(key);
				if (sessionChannel != null) {
					if (sessionChannel.getChannel() != null && sessionChannel.isActive()) {
						// sessionChannel.getChannel().writeAndFlush(msg).awaitUninterruptibly(5000);
						ChannelFuture future = sessionChannel.getChannel().writeAndFlush(msg);
						future.awaitUninterruptibly(Options.WriteAndFlush.WAIT_TIMEOUT);
						if (future.isSuccess())
							success = true;
					}
				}
			}
		}
		return success;
	}

	public List<String> getStatus() {
		List<String> list = new ArrayList<String>();
		if (sessionChannelsMap != null && sessionChannelsMap.size() > 0) {
			for (String key : sessionChannelsMap.keySet()) {
				SessionChannel sessionChannel = sessionChannelsMap.get(key);
				if (sessionChannel != null) {
					list.add(sessionChannel.getStatus());
				}
			}
		}
		return list;
	}
	
	
	public SessionChannel getSessionChannelByClientId(String clientId) {
		if (sessionChannelsMap != null && sessionChannelsMap.size() > 0) {
			for (String key : sessionChannelsMap.keySet()) {
				SessionChannel sessionChannel = sessionChannelsMap.get(key);
				if (sessionChannel != null) {
					String channelClientId = sessionChannel.getChannel().attr(Options.CLIENT_ID).get();
					if(StringUtils.isNotBlank(channelClientId)) {
						if(channelClientId.equals(clientId))
							return sessionChannel;
					}
				}
			}
		}
		return null;
	}

	

	public boolean isActive() {
		if (sessionChannelsMap != null && sessionChannelsMap.size() > 0) {
			for (String key : sessionChannelsMap.keySet()) {
				SessionChannel sessionChannel = sessionChannelsMap.get(key);
				if (sessionChannel != null && sessionChannel.isActive()) {
					return true;
				}
			}
		}
		return false;
	}

	public int getSessionChannelCount() {
		return sessionChannelsMap.size();
	}

	public void close() {
		for (String key : sessionChannelsMap.keySet()) {
			SessionChannel sessionChannel = sessionChannelsMap.get(key);
			if (sessionChannel != null) {
				sessionChannelsMap.remove(key);
				sessionChannel.close();
			}
		}
	}

	public void close(String nid) {
		SessionChannel sessionChannel = sessionChannelsMap.get(nid);
		if (sessionChannel != null) {
			sessionChannelsMap.remove(nid);
			sessionChannel.close();
		}
	}

	public int hashCode() {

		return (id + sessionChannelsMap).hashCode();
	}

	public boolean equals(Object o) {

		if (o instanceof Session) {
			return hashCode() == o.hashCode();
		}
		return false;
	}
	/*
	 * public String toString() { return JSON.toJSONString(Session.this); }
	 */

}