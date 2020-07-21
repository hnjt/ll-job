package com.utils;


import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONTokener;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;




public class JsonUtil {

	 public static final char UNDERLINE='_';
	public static String json_camel_to_snake(String str) throws JSONException{
		if(str.charAt(0)==' '){
			System.out.println(str);
			str="";
		}
		if(StringUtils.isNotEmpty(str)&&(str.startsWith("{")||str.startsWith("["))){
			Object object = new JSONTokener(str).nextValue();
			if(object instanceof JSONObject){
				JSONObject jsonObject = (JSONObject)object;
				Set<String> set = jsonObject.keySet();
				JSONObject newData = new JSONObject();
				for(String key : set){
					String subStr = jsonObject.getString(key);

					String newStr = json_camel_to_snake(subStr);
					newData.put(camel4underline(key), newStr);
				}
				return newData.toString();
				
			}else if(object instanceof JSONArray){
				JSONArray array = (JSONArray)object;
				StringBuffer buffer = new StringBuffer();
				buffer.append("[");
				for(int i=0;i<array.size();i++){
					String newStr = array.getString(i);
					buffer.append(json_camel_to_snake(newStr));
					if(i<array.size()-1){
						buffer.append(",");
					}
				}
				buffer.append("]");

				return buffer.toString();
			}else{
				return str;
			}
			
		}else{
			return str;
		}

	}
	
	public static String json_snake_to_camel(String str) throws JSONException{
		str = str.trim();
		if(StringUtils.isNotEmpty(str)&&(str.startsWith("{")||str.startsWith("["))){
			Object json = new JSONTokener(str).nextValue();
			if(str.startsWith("{")){
				JSONObject jsonObject = JSONObject.parseObject(str);
				Set<String> set = jsonObject.keySet();
				JSONObject newData = new JSONObject();
				for(String key : set){
					String subStr = jsonObject.getString(key);

					String newStr = json_snake_to_camel(subStr);
					newData.put(underlineToCamel(key), newStr);
				}
				return newData.toString();
				
			}else if(str.startsWith("[{")){
				 if (json instanceof JSONArray){
					 System.out.println(str);
				 }
				JSONArray array = JSONArray.parseArray(str);
				StringBuffer buffer = new StringBuffer();
				buffer.append("[");
				for(int i=0;i<array.size();i++){
					String newStr = array.getString(i);
					buffer.append(json_snake_to_camel(newStr));
					if(i<array.size()-1){
						buffer.append(",");
					}
				}
				buffer.append("]");

				return buffer.toString();
			}else{
				return str;
			}
			
		}else{
			return str;
		}

	}
	
	 public static String camel4underline(String param){
	    	
			Pattern  p=Pattern.compile("[A-Z]");
			if(param==null ||param.equals("")){
				return "";
			}
			StringBuilder builder=new StringBuilder(param);
			Matcher mc=p.matcher(param);
			int i=0;
			while(mc.find()){
					builder.replace(mc.start()+i, mc.end()+i, "_"+mc.group().toLowerCase());

				i++;
			}


			if('_' == builder.charAt(0)){
				builder.deleteCharAt(0);
			}
				
			
			return builder.toString();
		}
	    public static String underlineToCamel(String param){
	        if (param==null||"".equals(param.trim())){
	            return "";
	        }
	        int len=param.length();
	        StringBuilder sb=new StringBuilder(len);
	        for (int i = 0; i < len; i++) {
	            char c=param.charAt(i);
	            if (c==UNDERLINE){
	               if (++i<len){
	                   sb.append(Character.toUpperCase(param.charAt(i)));
	               }
	            }else{
	                sb.append(c);
	            }
	        }
	        return sb.toString();
	    }
}
