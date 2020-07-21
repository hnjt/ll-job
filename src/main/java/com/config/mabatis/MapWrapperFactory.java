/**
 * 文件名：MapWrapperFactory.java
 * 
 * figo(http://www.figo.vip)
 * Copyright © 2010 figo.vip All Right Reserved.
 */
package com.config.mabatis;

import java.util.Map;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;

/**
 * <p>
 * <li>Description:</li>
 * <li>$Author$</li>
 * <li>$Revision$</li>
 * <li>$Date$</li>
 * @version 1.0
 */
public class MapWrapperFactory implements ObjectWrapperFactory {
	  @Override
	    public boolean hasWrapperFor(Object object) {
	        return object != null && object instanceof Map;
	    }

	    @Override
	    public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
	        return new MyMapWrapper(metaObject, (Map) object);
	    }

}
