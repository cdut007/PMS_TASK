package com.jameschen.comm.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class Util {

	public static <T> Type whatsMyGenericType(Object object) {
		return (Class<T>) ((ParameterizedType) object.getClass() .getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
}
