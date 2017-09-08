package com.six.dcscrawler.downloader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.six.dcscrawler.utils.UrlUtils;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * @author six
 * @date 2016年7月18日 上午10:54:27 cookie 管理
 */
public class CookiesStore implements CookieJar {

	protected final static Logger LOG = LoggerFactory.getLogger(CookiesStore.class);

	private Map<String, Map<String, Cookie>> cookiesMap = new ConcurrentHashMap<String, Map<String, Cookie>>();

	@Override
	public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
		String domain = UrlUtils.getDomain(url.toString());
		Map<String, Cookie> map = getCookieMap(domain);
		for (Cookie ck : cookies) {
			map.put(ck.name(), ck);
		}
	}

	/**
	 * 通过domain 保存cookies
	 * 
	 * @param domain
	 * @param cookies
	 */
	public void saveFromList(String domain, List<Cookie> cookies) {
		Map<String, Cookie> map = getCookieMap(domain);
		for (Cookie ck : cookies) {
			map.put(ck.name(), ck);
		}
	}

	@Override
	public List<Cookie> loadForRequest(HttpUrl url) {
		String domain = UrlUtils.getDomain(url.toString());
		Map<String, Cookie> map = getCookieMap(domain);
		checkExpired(map);
		return new ArrayList<>(map.values());
	}

	/**
	 * 通过domain 加载cookie
	 * 
	 * @param domain
	 * @return
	 */
	public List<Cookie> loadForDomain(String domain) {
		Map<String, Cookie> map = getCookieMap(domain);
		checkExpired(map);
		return new ArrayList<>(map.values());
	}

	/**
	 * 通过domain 和 key 获取cookie
	 * 
	 * @param domain
	 * @param key
	 * @return
	 */
	public Cookie getCookieByDamainAndKey(String domain, String key) {
		Map<String, Cookie> map = getCookieMap(domain);
		return map.get(key);
	}

	/**
	 * 通过domain获取cookie 并检查过期cookie
	 * 
	 * @param url
	 * @return
	 */
	private Map<String, Cookie> getCookieMap(final String domain) {
		Map<String, Cookie> map = cookiesMap.computeIfAbsent(domain, key -> new ConcurrentHashMap<>());
		return map;
	}

	/**
	 * 检查过期cookie并删除 锁的粒度最小为map
	 * 
	 * @param map
	 */
	private void checkExpired(final Map<String, Cookie> map) {
		synchronized (map) {
			try (Stream<Entry<String, Cookie>> stream = map.entrySet().stream();) {
				final long now = System.currentTimeMillis();
				stream.filter(entry -> entry.getValue().expiresAt() <= now).map(entry -> map.remove(entry.getKey()))
						.count();
			}
		}
	}



	/**
	 * 用来处理okhttp cookie 序列化问题
	 * 
	 * @author wisers
	 *
	 */
	public static class ProxyCookie implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = -3466716754657620157L;
		private String name;
		private String value;
		private long expiresAt;
		private String domain;
		private String path;
		private boolean secure;
		private boolean httpOnly;
		private boolean persistent;
		private boolean hostOnly;

		public static ProxyCookie newProxyCookie(Cookie ck) {
			ProxyCookie proxyCookie = new ProxyCookie();
			proxyCookie.setDomain(ck.domain());
			proxyCookie.setName(ck.name());
			proxyCookie.setValue(ck.value());
			proxyCookie.setExpiresAt(ck.expiresAt());
			proxyCookie.setHostOnly(ck.hostOnly());
			proxyCookie.setHttpOnly(ck.httpOnly());
			proxyCookie.setPath(ck.path());
			proxyCookie.setPersistent(ck.persistent());
			return proxyCookie;
		}

		public Cookie newCookie() {
			Cookie.Builder cookieBuilder = new Cookie.Builder();
			cookieBuilder.domain(domain);
			cookieBuilder.name(name);
			cookieBuilder.value(value);
			cookieBuilder.path(path);
			cookieBuilder.expiresAt(expiresAt);
			cookieBuilder.hostOnlyDomain(domain);
			return cookieBuilder.build();
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public long getExpiresAt() {
			return expiresAt;
		}

		public void setExpiresAt(long expiresAt) {
			this.expiresAt = expiresAt;
		}

		public String getDomain() {
			return domain;
		}

		public void setDomain(String domain) {
			this.domain = domain;
		}

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		public boolean isSecure() {
			return secure;
		}

		public void setSecure(boolean secure) {
			this.secure = secure;
		}

		public boolean isHttpOnly() {
			return httpOnly;
		}

		public void setHttpOnly(boolean httpOnly) {
			this.httpOnly = httpOnly;
		}

		public boolean isPersistent() {
			return persistent;
		}

		public void setPersistent(boolean persistent) {
			this.persistent = persistent;
		}

		public boolean isHostOnly() {
			return hostOnly;
		}

		public void setHostOnly(boolean hostOnly) {
			this.hostOnly = hostOnly;
		}

	}




	/**
	 * 关闭指定 domain cookie 内存，并持久化
	 * 
	 * @param domain
	 */
	public void clearBy(String domain) {
		cookiesMap.computeIfPresent(domain,(key,value)->{
			 value.clear();
			 return null;
		});
	}
	
	public void clear() {
		for (Map<String, Cookie> cookieMap : cookiesMap.values()) {
			cookieMap.clear();
		}
		cookiesMap.clear();
	}
}
