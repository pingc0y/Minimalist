package com.minimalist.util;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class IpAndAddrUtil {

    public static String getIp(HttpServletRequest request) {
            String ip = null;
            ip = request.getHeader("x-forwarded-for");
            if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
                ip = request.getRemoteAddr();
                if (ip.equals("127.0.0.1")) {
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ip = inet.getHostAddress();
                }
            }
            if ((ip != null) && (ip.length() > 15)) {
                if (ip.indexOf(",") > 0) {
                    ip = ip.substring(0, ip.indexOf(","));
                }
            }
            return ip;
        }

    public static String getBrowserName(HttpServletRequest request) {
            String header = request.getHeader("User-Agent");
            UserAgent userAgent = UserAgent.parseUserAgentString(header);
            Browser browser = userAgent.getBrowser();
            return browser.getName();
        }

    public static String getBrowserVersion(HttpServletRequest request) {
            String header = request.getHeader("User-Agent");
            UserAgent userAgent = UserAgent.parseUserAgentString(header);
            //获取浏览器信息
            Browser browser = userAgent.getBrowser();
            //获取浏览器版本号
            Version version = browser.getVersion(header);
            return version.getVersion();
        }

    public static String getOsName(HttpServletRequest request) {
            String header = request.getHeader("User-Agent");
            UserAgent userAgent = UserAgent.parseUserAgentString(header);
            OperatingSystem operatingSystem = userAgent.getOperatingSystem();
            return operatingSystem.getName();
        }

}


