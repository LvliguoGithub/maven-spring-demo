/**
 * 
 */
package net.demo.llg.common.util;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;

/**
 * jackson api facade
 * 
 * history:
 * 2016年5月30日 mis_zym
 */
public class JsonUtil {
	
	/**
	 * json化
	 * @param object
	 * @return
	 */
	public static String toJson(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		JsonFactory jsonFact = mapper.getFactory();
		StringWriter stringWriter = new StringWriter();
		try {
			JsonGenerator generator = jsonFact.createGenerator(stringWriter);
			generator.writeObject(object);
		} catch (Exception e) {
			throw new RuntimeException("error when serialized to json string", e);
		}
		return stringWriter.toString();
	}
	
	/**
	 * 把字符串转化为Json对象
	 * 
	 * @param jsonObject
	 * @param clazz
	 * @return
	 */
	public static Object convertJson(String jsonObject,Class clazz) {
		if(StringUtils.isNotBlank(jsonObject)){
			try {
				JSONArray object = JSONArray.fromObject(jsonObject);
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.setRootClass(clazz);
				JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] {"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd" }));
				return JSONObject.toBean((JSONObject) object.get(0), jsonConfig);
			} catch (Exception e) {
				throw new RuntimeException("转化为JSON对象异常！");
			}
		} else {
			return null;
		}
	}
	
	/**
	 * 将json字符串转化为list
	 * 
	 * @param jsonObject
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> convertToList(String jsonObject,Class<T> clazz) {
		List<T> list = new ArrayList<T>();
		if (StringUtils.isNotBlank(jsonObject) && !"null".equals(jsonObject)){
			try {
				JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] { "yyyy-MM-dd HH:mm:ss" }));
				JSONArray jsonArray = JSONArray.fromObject(jsonObject);
				for (Object object : jsonArray){
					JSONObject obj = (JSONObject) object;
					list.add((T)JSONObject.toBean(obj,clazz));
				}
			} catch (Exception e) {
				throw new RuntimeException("转化为List对象异常！");
			}
		}
		return list;
	}
}
