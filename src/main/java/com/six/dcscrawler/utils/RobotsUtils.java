package com.six.dcscrawler.utils;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import com.six.dcscrawler.AgentNames;

import crawlercommons.robots.BaseRobotRules;
import crawlercommons.robots.SimpleRobotRules;
import crawlercommons.robots.SimpleRobotRules.RobotRulesMode;
import crawlercommons.robots.SimpleRobotRulesParser;

/**
 * @author liusong
 * @date 2017年8月15日
 * @email 359852326@qq.com
 */
public class RobotsUtils {

	static final String USER_AGENT_PRE = "User-agent:";
	static final String ALLOW = "Allow:";
	static final String DISALLOW = "Disallow:";

	private static final BaseRobotRules EMPTY_RULES = new SimpleRobotRules(RobotRulesMode.ALLOW_ALL);
	private static BaseRobotRules FORBID_ALL_RULES = new crawlercommons.robots.SimpleRobotRules(
			RobotRulesMode.ALLOW_NONE);

	/**
	 * User-agent: *
	 * 
	 * Allow: .htm$
	 * 
	 * Disallow: /
	 * 
	 * @param robotsTxt
	 */
	public RobotsUtils() {

	}

	private static BaseRobotRules parseRules(String url, byte[] robots, String contentType, String agentNames) {
		SimpleRobotRulesParser parser = new SimpleRobotRulesParser();
		BaseRobotRules baseRobotRules = parser.parseContent(url, robots, contentType, agentNames);
		return baseRobotRules;
	}

	public static BaseRobotRules getRobotRules(String url, boolean allowForbidden) {
		String robotsUrl = UrlUtils.getMainUrl(url) + "/robots.txt";
		BaseRobotRules robotRules = null;
		Connection conn = Jsoup.connect(robotsUrl);
		try {
			conn.followRedirects(true);
			Response response = conn.execute();
			if (response.statusCode() == 200) {
				robotRules = parseRules(url.toString(), response.bodyAsBytes(), response.header("Content-Type"),
						AgentNames.INSTANCE.toString());
			} else if ((response.statusCode() == 403) && (!allowForbidden))
				robotRules = FORBID_ALL_RULES;
			else if (response.statusCode() >= 500) {
				robotRules = EMPTY_RULES;
			} else
				robotRules = EMPTY_RULES; 
		} catch (Exception t) {
			robotRules = EMPTY_RULES;
		}
		return robotRules;
	}

	public static void main(String[] agrs) throws IOException {
		String url = "https://github.com/robots.txt";
		BaseRobotRules baseRobotRules = RobotsUtils.getRobotRules(url, false);
		String newUrl = "https://github.com/ddddddd/ddd/tree/master";
		boolean result = baseRobotRules.isAllowed(newUrl);
		System.out.println("允许访问:" + result);
		System.out.println("站点map:" + baseRobotRules.getSitemaps());
	}
}
