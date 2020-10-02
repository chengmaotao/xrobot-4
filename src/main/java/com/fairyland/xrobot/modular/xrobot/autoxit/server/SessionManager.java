package com.fairyland.xrobot.modular.xrobot.autoxit.server;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fairyland.xrobot.modular.xrobot.autoxit.server.core.Options;

import io.netty.channel.Channel;

public class SessionManager {
	private final static Logger log = LoggerFactory.getLogger(SessionManager.class);
	protected Map<String, Session> sessionsMap = new ConcurrentHashMap<String, Session>();

	private static SessionManager instance = null;

	private SessionManager() {
	}

	public static SessionManager getInstance() {
		if (instance == null) {
			synchronized (SessionManager.class) {
				if (instance == null) {
					instance = new SessionManager();
				}
			}
		}
		return instance;
	}

	public String getChannelSessionId(Channel channel) {
		return channel.attr(Options.SESSION_ID).get();
	}

	public String getChannelClientId(Channel channel) {
		return channel.attr(Options.CLIENT_ID).get();
	}
	
	public void setChannelSessionId(Channel channel, String sessionId) {
		channel.attr(Options.SESSION_ID).set(sessionId);
	}
	
	public void setChannelClientId(Channel channel, String sessionId) {
		channel.attr(Options.CLIENT_ID).set(sessionId);
	}
	
//	public void setChannelAuth(Channel channel, String sessionId,String clientId) {
//		channel.attr(Options.SESSION_ID).set(sessionId);
//		channel.attr(Options.CLIENT_ID).set(clientId);
//	}
//	

	public synchronized void addSession(Session session) {
		if (null == session) {
			return;
		}
		if (exist(session.getId())) {
			Session currentSession = getSession(session.getId());
			List<SessionChannel> list = session.getSessionChannels();
			for (int i = 0; i < list.size(); i++)
				currentSession.addSessionChannel(list.get(i));
			sessionsMap.put(currentSession.getId(), currentSession);
		} else {
			sessionsMap.put(session.getId(), session);
		}

	}

	public synchronized void updateSession(Session session) {
		if (null == session) {
			return;
		}
		sessionsMap.put(session.getId(), session);

	}

	public boolean updateStatus(Channel channel, String status) {
		String sessionId = getChannelSessionId(channel);
		if (StringUtils.isNotBlank(sessionId)) {
			try {
				Session session = getSession(sessionId);
				SessionChannel sessionChannel = session.getSessionChannel(channel.id().asShortText());
				sessionChannel.setStatus(status);
				return session.updateSessionChannel(sessionChannel);
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}

	public String getSessionStatus(String sessionId) {
		if (StringUtils.isNotBlank(sessionId)) {
			try {
				Session session = getSession(sessionId);
				return session.getStatus().get(0);
			} catch (Exception e) {
			}
		}
		return "0";
	}

	public Map<String, Object> getSignature(Channel channel) {
		String sessionId = getChannelSessionId(channel);
		if (StringUtils.isNotBlank(sessionId)) {
			try {
				Session session = getSession(sessionId);
				SessionChannel sessionChannel = session.getSessionChannel(channel.id().asShortText());
				return sessionChannel.getSignature();
			} catch (Exception e) {
				return null;
			}
		} else {
			return null;
		}
	}

	public synchronized void removeSession(String sessionId) {
		try {
			Session session = getSession(sessionId);
			if (session != null) {
				session.close();
				sessionsMap.remove(sessionId);
			}
		} catch (Exception e) {

		}
	}

	public synchronized void removeSession(String sessionId, String nid) {
		try {
			Session session = getSession(sessionId);
			if (session != null) {
				SessionChannel sessionChannel = session.getSessionChannel(nid);
				if (sessionChannel != null) {
					session.close(nid);
				}
				if (session.getSessionChannelCount() == 0 || session.isActive() == false) {
					sessionsMap.remove(sessionId);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {

		}
	}
	
	public synchronized void removeChannel(String sessionId, String clientId) {
		try {
			Session session = getSession(sessionId);
			if (session != null) {
				SessionChannel sessionChannel = session.getSessionChannelByClientId(clientId);
				if (sessionChannel != null) {
					session.close(sessionChannel.getChannel().id().asShortText());
				}
				if (session.getSessionChannelCount() == 0 || session.isActive() == false) {
					sessionsMap.remove(sessionId);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {

		}
	}
	

	public Session getSession(String sessionId) {
		try {
			return sessionsMap.get(sessionId);
		} catch (Exception e) {
			return null;
		}
	}

	public Session[] getSessions() {
		return sessionsMap.values().toArray(new Session[0]);
	}

	public Set<String> getSessionKeys() {
		return sessionsMap.keySet();
	}

	public int getSessionCount() {
		return sessionsMap.size();
	}

	public boolean exist(String sessionId) {
		Session session = getSession(sessionId);
		return session != null ? true : false;
	}

	public boolean isActive(String sessionId) {
		Session session = getSession(sessionId);
		if (session != null) {
			return session.isActive();
		}
		return false;
	}

	public boolean write(String sessionId, Object msg) {
		Session session = getSession(sessionId);
		if (session != null) {
			return session.write(msg);
		}
		return false;
	}

}
