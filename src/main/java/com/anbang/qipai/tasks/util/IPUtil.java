package com.anbang.qipai.tasks.util;

import javax.servlet.http.HttpServletRequest;

public class IPUtil {

	public static String getRealIp(HttpServletRequest request) {
		String ip;
		ip = request.getHeader("X-Real-IP");
		if (ip == null) {
			String xip = request.getHeader("x-forwarded-for");
			if (xip != null) {
				String[] ips = xip.split(",");
				ip = ips[0];
			} else {
				ip = request.getRemoteAddr();
			}
		}
		return ip;
	}

	/**
	 * 验证请求是否经过代理
	 */
	public static boolean verifyIp(HttpServletRequest request) {
		String ip;
		ip = request.getHeader("X-Real-IP");
		if (ip == null) {
			String xip = request.getHeader("X-Forwarded-For");
			if (xip != null) {
				String[] ips = xip.split(",");
				if (ips.length > 2) {
					return false;
				}
			} else {
				return true;
			}
		}
		return true;
	}
}
