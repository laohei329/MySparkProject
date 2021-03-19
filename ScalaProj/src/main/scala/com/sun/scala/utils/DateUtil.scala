package com.sun.scala.utils

import java.text.SimpleDateFormat
import java.util.{Calendar, Date, Locale}

import org.apache.xmlbeans.impl.xb.xsdschema.PatternDocument.Pattern

/**
 * @ClassName DataUtil
 * @author Mr.Sun
 * @version 1.0.0
 * @Description TODO
 * @createTime 2021年03月18日 11:04:00
 */
object DateUtil {


  /**
   * @Description : 获取上周日的日期 并根据格式返回对应的时间格式的日期
   * @author : Mr.Sun
   * @date  2021/3/18  13:30
   * @param pattern  需要传入的日期格式
   * @return java.lang.String
     */
  def getLastSunday(pattern: String) = {
    getSunday(pattern, -1)
  }

  def getSunday(pattern: String, num: Int) = {
    val calendar: Calendar = Calendar.getInstance()
    val format = new SimpleDateFormat(pattern, Locale.CHINA)
    calendar.setFirstDayOfWeek(Calendar.MONDAY)
    calendar.add(Calendar.DATE, num * 7)
    calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
    format.format(calendar.getTime)
  }

  /**
   * @Description : 获取指定的时间的周一的时间,需要传入输出的日期的格式和希望得到的距离本周的时间,上周为﹣  后来为+
   * @author : Mr.Sun
   * @date 2021/3/18  11:43
   * @param patterm 需要传入的时间格式
   * @param num     距离本周的时间  如果是上周一传入的为 -1 如果是下周一 则传入 1 ,如果是上上周一则是 -2 以此类推
   * @return java.lang.String
   */
  def getMonday(patterm: String, num: Int) = {
    val calendar: Calendar = Calendar.getInstance()
    calendar.setFirstDayOfWeek(Calendar.MONDAY)
    calendar.add(Calendar.DATE, num * 7)
    calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
    val format = new SimpleDateFormat(patterm, Locale.CHINA)
    format.format(calendar.getTime)
  }

  /**
   * @Description : 根据传入的日期格式获取到上周一的日期
   * @author : Mr.Sun
   * @date 2021/3/18  13:07
   * @param patterm
   * @return java.lang.String
   */
  def getLastMonday(patterm: String) = {
    getMonday(patterm, -1)
  }

}
