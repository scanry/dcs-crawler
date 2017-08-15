package com.six.dcscrawler.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.six.dcscrawler.web.service.ClusterManagerService;
import com.six.dcsnodeManager.Node;

/**
 * @author 作者
 * @E-mail: 359852326@qq.com
 * @date 创建时间：2017年3月17日 下午4:16:02 控制页面 必须访问 master节点页面
 * 
 */
public class MasterScheduledApiInterceptor implements HandlerInterceptor {

	ClusterManagerService clusterManager;

	public MasterScheduledApiInterceptor(ClusterManagerService clusterManager) {
		this.clusterManager = clusterManager;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (!clusterManager.isSingle()||!clusterManager.isMaster()) {
			Node masterNode = clusterManager.getMaster();
			response.sendRedirect(getMasterIndex(masterNode));
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	private static String getMasterIndex(Node masterNode) {
		String indexUrl = "http://" + masterNode.getIp() + ":" + masterNode.getPort() + "/crawler";
		return indexUrl;
	}

}
