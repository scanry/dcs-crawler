package com.six.dcscrawler.downer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URLEncoder;
import java.net.Proxy.Type;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

import com.six.dcscrawler.downer.exception.DataTooBigDownerException;
import com.six.dcscrawler.downer.exception.DownerException;
import com.six.dcscrawler.downer.exception.HttpStatus502DownerException;
import com.six.dcscrawler.downer.exception.IoDownerException;
import com.six.dcscrawler.downer.exception.ManyRedirectDownException;
import com.six.dcscrawler.downer.exception.UnknowHttpStatusDownerException;
import com.six.dcscrawler.modle.HttpMethod;
import com.six.dcscrawler.modle.HttpProxy;
import com.six.dcscrawler.modle.IpProxy;
import com.six.dcscrawler.modle.Page;
import com.six.dcscrawler.modle.PostType;
import com.six.dcscrawler.utils.AutoCharsetDetectorUtils;
import com.six.dcscrawler.utils.JsonUtils;
import com.six.dcscrawler.utils.UrlUtils;
import com.six.dcscrawler.worker.AbstractCrawlerWorker;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Request.Builder;

/**
 * @author liusong
 * @date 2017年8月17日
 * @email 359852326@qq.com
 */

@Slf4j
public class OkHttpDowner extends AbstractDowner {

	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	public static final MediaType PAY_LOAD = MediaType.parse("text/html; charset=utf-8");
	private OkHttpClient okClient;

	public OkHttpDowner(AbstractCrawlerWorker worker, int downloadTimeout, String useAgent, HttpProxy proxy) {
		super(worker, downloadTimeout, useAgent, proxy);
		OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
		okHttpClientBuilder.sslSocketFactory(MX509TrustManager.getSSLSocketFactory(),
				MX509TrustManager.myX509TrustManager);
		okHttpClientBuilder.connectTimeout(60, TimeUnit.SECONDS);
		okHttpClientBuilder.writeTimeout(60, TimeUnit.SECONDS);
		okHttpClientBuilder.readTimeout(60, TimeUnit.SECONDS);
		okHttpClientBuilder.followRedirects(false);
		okHttpClientBuilder.followSslRedirects(false);
		okClient = okHttpClientBuilder.build();
	}
	
	@Override
	protected void doSetProxy(HttpProxy proxy){
		
	}

	@Override
	protected void insideDownload(Page page) {
		String requestUrl = page.getUrl();
		String requesReferer = page.getReferer();
		HttpMethod httpMethod = page.getHttpMethod();
		PostType postType = page.getPostType();
		Map<String, Object> parameters = page.getPostData();
		int redirectTime = 0;
		IpProxy ipProxy = page.getIpProxy();
		Map<String, String> headMap = page.getHead();
		do {
			if (null != headMap) {
				headMap.putAll(HttpConstant.headMap);
			}
			Request request = buildRequest(requestUrl, requesReferer, httpMethod, headMap, postType, parameters,
					ipProxy, getUseAgent());
			log.info("execute request[" + requestUrl + "] by proxy["
					+ (null != ipProxy ? ipProxy.toString() : "noproxy") + "]");
			executeRequest(page, request);
			if (StringUtils.isNotBlank(page.getRedirectedUrl())) {
				requesReferer = requestUrl;
				requestUrl = page.getRedirectedUrl();
				httpMethod = HttpMethod.GET;
				postType = null;
				parameters = null;
				redirectTime++;
				if (redirectTime > HttpConstant.REDIRECT_TIMES) {
					throw new ManyRedirectDownException(
							"execute request[" + request.url() + "] redirectTime is too many");
				}
			}
		} while (StringUtils.isNotBlank(page.getRedirectedUrl()));
		String content = getHtml(page);
		page.setContent(content);
	}

	public Request buildRequest(String url, String referer, HttpMethod method, Map<String, String> headMap,
			PostType postContentType, Map<String, Object> parameters, IpProxy ipProxy, String userAgent) {
		if (StringUtils.isBlank(url)) {
			throw new RuntimeException("the url must not be blank");
		}
		Builder build = null;
		String contentType = null;
		if (HttpMethod.POST == method) {
			RequestBody body = null;
			if (PostType.JSON == postContentType) {
				String json = JsonUtils.toJson(parameters);
				body = RequestBody.create(JSON, json);
				contentType = "application/json;charset=UTF-8";
			} else if (PostType.PAY_LOAD == postContentType) {
				if (null != parameters && !parameters.isEmpty()) {
					String text = "";
					for (String key : parameters.keySet()) {
						text += key + "=" + parameters.get(key) + "\r\n";
					}
					body = RequestBody.create(PAY_LOAD, text);
				}
			} else {
				FormBody.Builder bodyBuild = new FormBody.Builder();
				for (String key : parameters.keySet()) {
					String value = parameters.get(key).toString();
					value = urlEncoder(value);
					bodyBuild.addEncoded(key, value);
				}
				body = bodyBuild.build();
				contentType = HttpConstant.CONTENT_TYPE_FORM_VALUE;
			}
			build = new Builder().url(url).post(body);
		} else {
			build = new Builder().url(url).get();
		}
		if (StringUtils.isNotBlank(contentType)) {
			build.addHeader(HttpConstant.CONTENT_TYPE, contentType);
		}
		if (StringUtils.isBlank(userAgent)) {
			userAgent = HttpConstant.DEFAULT_USER_AGENT;
		}
		build.addHeader(HttpConstant.USERAGENT, userAgent);
		String host = UrlUtils.getHost(url);
		build.addHeader(HttpConstant.HOST, host);

		String origin = UrlUtils.getMainUrl(url);
		build.addHeader(HttpConstant.ORIGIN, origin);

		// String domain = UrlUtils.getDomain(url);
		// okhttp3.Cookie cookie = cookiesStore.getCookieByDamainAndKey(domain,
		// HttpConstant.COOKIE_XSRF);
		// if (null != cookie) {
		// build.addHeader(HttpConstant.X_Xsrftoken, cookie.value());
		// }
		if (null != headMap) {
			for (String name : headMap.keySet()) {
				build.addHeader(name, headMap.get(name));
			}
		}
		if (null != referer) {
			referer = new Builder().url(referer).build().url().toString();
			build.addHeader(HttpConstant.REFERER, referer);
		}
		String headName = HttpConstant.CONNECTION;
		String headValue = HttpConstant.KEEP_ALIVE;
		if (null != ipProxy) {
			SocketAddress sa = new InetSocketAddress(ipProxy.getHost(), ipProxy.getPort());
			Proxy proxy = new Proxy(Type.HTTP, sa);
			build.proxy(proxy);
			if (StringUtils.isNotBlank(ipProxy.getUserName())) {
				String nameAndPass = ipProxy.getUserName() + ":" + ipProxy.getPassWord();
				String encoding = new String(Base64.getEncoder().encode(nameAndPass.getBytes()));
				build.addHeader("Proxy-Authorization", "Basic " + encoding);
			}
			headName = HttpConstant.PROXY_CONNECTION;
			headValue = HttpConstant.KEEP_ALIVE;
		}
		build.addHeader(headName, headValue);
		return build.build();
	}

	public void executeRequest(Page page, Request request) {
		if (null == request) {
			throw new RuntimeException("url must not be blank.");
		}
		String url = request.url().toString();
		try (Response response = okClient.newCall(request).execute();) {
			int httpCode = response.code();
			page.setHttpCode(httpCode);
			page.setHeaders(response.headers());
			if (null != response.body().contentType() && null != response.body().contentType().charset()) {
				String charset = response.body().contentType().charset().name();
				page.setCharset(charset);
			}
			byte[] data = readLimited(response);
			page.setContentBytes(data);
			if (httpCode >= 300 && httpCode < 400) {
				String redirectUrl = response.header("Location");
				if (StringUtils.isBlank(redirectUrl)) {
					throw new RuntimeException("httpCode >= 300 && httpCode < 400 , but redirectUrl is blank:" + url);
				}
				page.setRedirectedUrl(redirectUrl);
			} else if (httpCode == 502) {
				throw new HttpStatus502DownerException("httpCode[" + httpCode + "]:" + url);
			} else if (httpCode != 200) {
				throw new UnknowHttpStatusDownerException("httpCode[" + httpCode + "]:" + url);
			}
		} catch (IOException e) {
			throw new IoDownerException("execute request by proxy[" + request.proxy() + "] err:" + url, e);
		}
	}

	/**
	 * okhttp 已经内置支持gzip和deflate 解压了
	 * 
	 * @param response
	 * @return
	 * @throws IOException
	 */
	private static byte[] readLimited(Response response) throws DownerException {
		final long length = response.body().contentLength();
		byte[] bytes = null;
		// 判断是否大于HTML_MAX_CONTENT_LENGTH 如果是抛出 HttpReadDataMoreThanMaxException
		// 异常
		if (length > (long) HttpConstant.HTML_MAX_CONTENT_LENGTH) {
			throw new DataTooBigDownerException(
					"read bytes length[" + length + "]more than max:" + HttpConstant.HTML_MAX_CONTENT_LENGTH);
		}
		try {
			bytes = response.body().bytes();
		} catch (IOException e) {
			throw new IoDownerException("", e);
		}
		// 判断是否大于HTML_MAX_CONTENT_LENGTH 如果是抛出 HttpReadDataMoreThanMaxException
		// 异常
		if (bytes.length > HttpConstant.HTML_MAX_CONTENT_LENGTH) {
			throw new DataTooBigDownerException(
					"read bytes length[" + bytes.length + "]more than max:" + HttpConstant.HTML_MAX_CONTENT_LENGTH);
		}
		return bytes;
	}

	public String getHtml(Page page) {
		String charset = page.getCharset();
		if (null == charset) {
			charset = AutoCharsetDetectorUtils.instance().getCharset(page.getContentBytes(), page.getContentType());
		} else {
			charset = AutoCharsetDetectorUtils.instance().replacement(charset);
		}
		page.setCharset(charset);
		return getDataAsString(charset, page.getContentBytes());
	}

	private static String getDataAsString(String charset, byte[] data) {
		try {
			if ("utf-8".equalsIgnoreCase(charset) || "utf8".equalsIgnoreCase(charset)) {
				// check for UTF-8 BOM
				if (data.length >= 3) {
					if (data[0] == (byte) 0xEF && data[1] == (byte) 0xBB && data[2] == (byte) 0xBF) {
						return new String(data, 3, data.length - 3, charset);
					}
				}
			}
			return new String(data, charset);
		} catch (UnsupportedEncodingException t) {
			throw new RuntimeException(t);
		}
	}

	public static String urlEncoder(String str) {
		try {
			return URLEncoder.encode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void clearCookie() {
		
	}
	@Override
	public void close() {
	}
}
