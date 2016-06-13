/**
 * 
 */
package com.clarion.worksys.util;

import java.math.BigDecimal;
/**
 * @author weng_zhangchu
 *
 */
public final class Arith {// 这个类不能实例化
	 
	 private static final int DEF_DIV_SCALE = 2;
	 // 默认除法运算精度
	 private Arith() {
	 }
	 
	 public static double add(double v1, double v2) {
	  BigDecimal b1 = new BigDecimal(Double.toString(v1));
	  BigDecimal b2 = new BigDecimal(Double.toString(v2));
	  return b1.add(b2).doubleValue();
	 }
	 
	 public static double sub(double v1, double v2) {
	  BigDecimal b1 = new BigDecimal(Double.toString(v1));
	  BigDecimal b2 = new BigDecimal(Double.toString(v2));
	  return b1.subtract(b2).doubleValue();
	 }
	 
	 public static double mul(double v1, double v2) {
	  BigDecimal b1 = new BigDecimal(Double.toString(v1));
	  BigDecimal b2 = new BigDecimal(Double.toString(v2));
	  return b1.multiply(b2).doubleValue();
	 }
	 
	 public static double div(double v1, double v2) {
	 if (v2==0) {
			return 0;
		}else {
			return div(v1, v2, DEF_DIV_SCALE);
		}
	  
	 }
	 
	 public static double div(double v1, double v2, int scale) {
	 if (v2==0) {
		return 0;
	 }else {
		if (scale < 0) {
			   throw new IllegalArgumentException(
			   "The scale must be a positive integer or zero");
			  }
			  BigDecimal b1 = new BigDecimal(Double.toString(v1));
			  BigDecimal b2 = new BigDecimal(Double.toString(v2));
			  return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();		
	 	}
	 }
	 
	 public static double round(double v, int scale) {
	 if (scale < 0) {
	   throw new IllegalArgumentException(
	   "The scale must be a positive integer or zero");
	  }
	  BigDecimal b = new BigDecimal(Double.toString(v));
	  BigDecimal one = new BigDecimal("1");
	  return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	 }
	}
