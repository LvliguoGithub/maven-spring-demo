package net.demo.llg.common.util;

import java.io.UnsupportedEncodingException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * CommonUtil.java
 *
 * abstract: 通用方法的Util
 *
 * history:
 * 
 * mis_llg 2016年5月20日 初始化
 */
public class CommonUtil {

	private static final Logger log = LoggerFactory.getLogger(CommonUtil.class);
	
	/**
	 * 排除重复的，分隔符默认为","
	 * 
	 * @param str
	 * @return
	 */
	public static String getDistinctStr(String str) {
		if (StringUtils.isNotBlank(str)) {
			return Stream.of(str.split(",")).distinct().sorted().collect(Collectors.joining(","));
		} else {
			return str;
		}
	}

	/**
	 * 将String由utf为ios
	 * 
	 * @param str
	 * @return
	 */
	public static String convertUtfToIos(String str) {
		if (StringUtils.isNotEmpty(str)) {
			try {
				String iosStr = new String(str.getBytes("UTF-8"), "iso-8859-1");
				return iosStr;
			} catch (UnsupportedEncodingException ex) {
				log.error("commonUtil: convertUtfToIos happen exception.", ex);
				throw new RuntimeException("中文编码异常！");
			}
		} else {
			return str;
		}
	}

}
