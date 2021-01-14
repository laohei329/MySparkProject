
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 常用的公共的逻辑处理方法
 * @author Administrator
 *
 */
public class CommonUtil {
	


	/**
	 * 指定日期加制定天数（返回值为yyyy-MM-dd格式）
	 * @param date yyyy-MM-dd格式时间
	 * @param addDay 增加的天数
	 * @return
	 */
	public static String dateAddDay(String date, int addDay) {
		String endDateAddOne;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		endDateAddOne = df.format(new Date());
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(df.parse(date));
			calendar.add(Calendar.DAY_OF_MONTH, addDay);//加一天
			endDateAddOne = df.format(calendar.getTime());
		} catch (ParseException e1) {
		}
		return endDateAddOne;
	}
	
	/**
	 * 指定日期加制定天数（返回值为yyyy-MM-dd格式）
	 * @param date yyyy-MM-dd格式时间
	 * @param addDay 增加的天数
	 * @return
	 */
	public static String dateAddDay(String date, int addDay,String strFormat) {
		String endDateAddOne;
		DateFormat df = new SimpleDateFormat(strFormat);
		endDateAddOne = df.format(new Date());
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(df.parse(date));
			calendar.add(Calendar.DAY_OF_MONTH, addDay);//加一天
			endDateAddOne = df.format(calendar.getTime());
		} catch (ParseException e1) {
		}
		return endDateAddOne;
	}
	

	/**
	 * 获取指定开始年份、月份、结束年份月份之间所有的年份月份结合
	 * 每个值的格式为：year_startMonth_endMonth
	 * @param startYear 开始年份
	 * @param startMonth 开始月份
	 * @param endYear 结束年份
	 * @param endMonth 结束月份
	 * @return
	 */
	public static List<String> getYearMonthList(String startYear, String startMonth, String endYear, String endMonth) {
		//获取年份+月份集合，方便查询
		int iStartYear = Integer.parseInt(startYear);
		int iEndYear = Integer.parseInt(endYear);
		int iStartMonth = Integer.parseInt(startMonth);
		int iEndMonth = Integer.parseInt(endMonth);
		
		//year_startMonth_endMonth
		List<String> listYearMonth = new ArrayList<>();
		String strYearMonth = "";
		for(int year =iStartYear;year<=iEndYear;year++) {
			//年份和开始月份
			if(year ==iStartYear) {
				//第一年，开始月份等于开始时间的月份
				strYearMonth = year+"_"+iStartMonth;
			}else if (year > iStartYear) {
				//非第一年，开始月份等于1
				strYearMonth = year+"_"+1;
			}
			
			//结束月份
			if(year == iEndYear) {
				//如果是最后一年，则结束月份等于结束日期的月份
				strYearMonth +="_"+iEndMonth;
			}else {
				//如果不是最后一年，则结束月份为12月
				strYearMonth +="_"+12;
			}
			
			if(strYearMonth!=null && !"".equals(strYearMonth) && strYearMonth.split("_").length>=3) {
				listYearMonth.add(strYearMonth);
			}
		}
		return listYearMonth;
	}
	
	/**
	 * 判断是否是整数（包含负数）
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str){
	    Pattern pattern = Pattern.compile("-?[0-9]+?");
	    Matcher isNum = pattern.matcher(str);
	    if( !isNum.matches() ){
	        return false;
	    }
	    return true;
	}
	
	/**
	 * 判断是否是数字（包含小数、整数、正数、负数）
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){
	    Pattern pattern = Pattern.compile("-?[0-9]+(.[0-9]+)?");
	    Matcher isNum = pattern.matcher(str);
	    if( !isNum.matches() ){
	        return false;
	    }
	    return true;
	}

	/**
	 * 获取指定日期上月的日期集合
	 * @param date
	 * @return
	 */
	public static String[] getPreMonthDays(String date) {
		String [] dates = date.split("/");
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Integer.valueOf(dates[0]), Integer.valueOf(dates[1]) - 1-1, 1);
		
		int day = calendar.getActualMaximum(5);
		calendar.set(Integer.valueOf(dates[0]), Integer.valueOf(dates[1]) - 1-1, day);
		
		String[] endDates = df.format(calendar.getTime()).split("/");
		String[] resultDates = new String[Integer.valueOf(endDates[2])];
		for(int i =0 ;i<resultDates.length;i++) {
			resultDates[resultDates.length-i-1] = CommonUtil.dateAddDay(df.format(calendar.getTime()), -i,"yyyy/MM/dd");
		}
		return resultDates;
	}
	
	/**
	 * 获取指定日期的上周日期集合
	 * @param date
	 * @return
	 */
	public static String[] getPreWeekDays(String date) {
		String[] resultDates = new String[7];
		try {
			DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
			Calendar cal = Calendar.getInstance();
			cal.setTime(df.parse(date));
			int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
			if (dayofweek == 1) {
				dayofweek += 7;
			}
			cal.add(Calendar.DATE, 2 - dayofweek-7);
			
			for(int i =0 ;i<resultDates.length;i++) {
				resultDates[i] = CommonUtil.dateAddDay(df.format(cal.getTime()), i,"yyyy/MM/dd");
			}
			
		} catch (ParseException e1) {
			
		}
		
		return resultDates;
	}
	

	
	 public static boolean isNotEmpty(String cs) {
	        return (StringUtils.isNotEmpty(cs) && !"null".equalsIgnoreCase(cs));
	 }
	 
	 public static boolean isEmpty(String cs) {
	        return (StringUtils.isEmpty(cs) || "null".equalsIgnoreCase(cs));
	 }
	 
	 /**
	  * 比较两个String类型时间的大小
	  * @param date1 String 类型时间1
	  * @param date2 String 类型时间1
	  * @return 0：时间相等 1：date1 小于date2 2：date1 大于date2 -1:异常
	  */
	 public static Integer compareStrDate(String date1,String date2,String format){
		 
			DateFormat dateFormat=new SimpleDateFormat(format);
			try {
				Date d1 = dateFormat.parse(date1);
				Date d2 = dateFormat.parse(date2);
				if(d1.equals(d2)){
					return 0;
				}else if(d1.before(d2)){
					return 1;
				}else if(d1.after(d2)){
					return 2;
				}
			} catch (ParseException e) {
				e.printStackTrace();
				System.out.println("compareStrDate异常"+date1+","+date2);
			}
			
			return -1;
		}

	
   /**
    * Map转换位String类型
    * @param map
    * @return
    */
   public static String getMapToString(Map<String,Object> map){
       Set<String> keySet = map.keySet();
       //将set集合转换为数组
       String[] keyArray = keySet.toArray(new String[keySet.size()]);
       //给数组排序(升序)
       Arrays.sort(keyArray);
       //因为String拼接效率会很低的，所以转用StringBuilder
       StringBuilder sb = new StringBuilder();
       for (int i = 0; i < keyArray.length; i++) {
           // 参数值为空，则不参与签名 这个方法trim()是去空格
           if ((String.valueOf(map.get(keyArray[i]))).trim().length() > 0) {
               sb.append(keyArray[i]).append(":").append(String.valueOf(map.get(keyArray[i])).trim());
           }
           if(i != keyArray.length-1){
               sb.append(",");
           }
       }
       return sb.toString();
   }
	   
   /**
    * 
    * String转map
    * @param
    * @return
    */
   public static Map<String,Object> getStringToMap(String strMap){
       //根据逗号截取字符串数组
       String[] arrMap = strMap.split(",");
       //创建Map对象
       Map<String,Object> map = new HashMap<>();
       //循环加入map集合
       String[] arrTemp;
       for (int i = 0; i < arrMap.length; i++) {
           //根据":"截取字符串数组
    	   arrTemp = arrMap[i].split(":");
           //str2[0]为KEY,str2[1]为值
           map.put(arrTemp[0],arrTemp[1]);
       }
       return map;
   }
   
   /**
    * Map转成实体对象
    *
    * @param map   map实体对象包含属性
    * @param clazz 实体对象类型
    * @return
    */
   public static <T> T map2Object(Map<String, String> map, Class<T> clazz) {
       if (map == null) {
           return null;
       }
       T obj = null;
       try {
           obj = clazz.newInstance();

           Field[] fields = obj.getClass().getDeclaredFields();
           for (Field field : fields) {
               int mod = field.getModifiers();
               if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                   continue;
               }
               field.setAccessible(true);
               String filedTypeName = field.getType().getName();
               
               //判断是否是null或者空
               String filed = String.valueOf(map.get(field.getName()));
        	   if(!filedTypeName.equalsIgnoreCase("java.lang.String") && CommonUtil.isEmpty(filed)) {
        		   field.set(obj, null);
        		   continue;
        	   }
               
               if (filedTypeName.equalsIgnoreCase("java.util.date")) {
                   String datetimestamp = String.valueOf(map.get(field.getName()));
                   if (datetimestamp.equalsIgnoreCase("null")) {
                       field.set(obj, null);
                   } else {
                       field.set(obj, new Date(Long.parseLong(datetimestamp)));
                   }
               }else if (filedTypeName.equalsIgnoreCase("java.lang.Long")) {
            	   field.set(obj, map.get(field.getName())==null?null:Long.valueOf(map.get(field.getName()).toString()));
               }else if (filedTypeName.equalsIgnoreCase("java.lang.Integer")) {
            	   field.set(obj, map.get(field.getName())==null?null:Integer.valueOf(map.get(field.getName()).toString()));
               }else if (filedTypeName.equalsIgnoreCase("java.math.BigDecimal")) {
            	   field.set(obj, map.get(field.getName())==null?null:BigDecimal.valueOf(Double.valueOf(map.get(field.getName()).toString())));
               }else if (filedTypeName.equalsIgnoreCase("java.lang.Double")) {
            	   field.set(obj, map.get(field.getName())==null?null:Double.valueOf(map.get(field.getName()).toString()));
               }else {
                   field.set(obj, map.get(field.getName()));
               }
           }
       } catch (Exception e) {
           e.printStackTrace();
           return null;
       }
       return obj;
   }


}
