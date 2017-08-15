package com.six.dcscrawler.extracter;

import java.util.List;
import java.util.Map;

import com.six.dcscrawler.modle.Page;

/**
 * @author liusong
 * @date 2017年8月15日
 * @email 359852326@qq.com
 */
public interface Extracter {

	Map<String, List<String>> extract(Page page);
}
