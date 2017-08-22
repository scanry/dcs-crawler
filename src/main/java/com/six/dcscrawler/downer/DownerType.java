package com.six.dcscrawler.downer;

import java.io.Serializable;

/**
 * @author six
 * @date 2016年7月1日 下午3:36:49
 */
public enum DownerType implements Serializable {
	OKHTTP(1), HEADLESS_CHROME(2), CHROME(3);

	final int value;

	DownerType(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}

	public static DownerType valueOf(int type) {
		if (1 == type) {
			return OKHTTP;
		} else if (2 == type) {
			return HEADLESS_CHROME;
		} else if (3 == type) {
			return CHROME;
		} else {
			return OKHTTP;
		}
	}
}
