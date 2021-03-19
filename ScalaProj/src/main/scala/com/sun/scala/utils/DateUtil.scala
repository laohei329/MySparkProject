package com.ssic.utils

import java.text.SimpleDateFormat
import java.util.{Calendar, Locale}

/**
 * @ClassName DataUtil
 * @author Mr.Sun
 * @version 1.0.0
 * @Description TODO
 * @createTime 2021年03月18日 11:04:00
 */
object DateUtil {


  /**
   * @Author MrSun
   * @Description 根据日期格式返回上月的一号的时间
   * @Date 16:40 2021/3/19
   * @Param [pattern]
   * @return java.lang.String
   **/
  def getLastMonthDay(pattern: String) = {
    getMonthFirstDay(pattern, -1)
  }

  /**
   * @Author MrSun
   * @Description 获取指定的月份的月初的日期,如果是上月的一号则month = -1 即可
   * @Date 16:37 2021/3/19
   * @Param [pattern 日期的格式 , month 月份距离现在的数量  之前的月份用负数 未来的月份用正数]
   * @return java.lang.String
   **/
  def getMonthFirstDay(pattern: String, month: Int) = {
    val calendar: Calendar = Calendar.getInstance()
    val format = new SimpleDateFormat(pattern, Locale.CHINA)
    calendar.add(Calendar.MONTH, month)
    calendar.set(Calendar.DAY_OF_MONTH, 1)
    format.format(calendar.getTime)
  }

  /**
   * @Author MrSun
   * @Description 根据传入的时间格式然后按照这个格式传出昨天的日期
   * @Date 10:06 2021/3/19
   * @Param [pattern] 传入和传出的日期格式
   * @return java.lang.String
   **/
  def getYesterday(pattern: String) = {
    getDaysAgo(pattern, 1)
  }

  /**
   * @Author MrSun
   * @Description 根据传入的日期格式返回当前的日期
   * @Date 10:22 2021/3/19
   * @Param [pattern]
   * @return java.lang.String
   **/
  def getNowDay(pattern: String) = {
    getDaysAgo(pattern, 0)
  }

  /**
   * @Author MrSun
   * @Description 根据传入的日期格式返回对应的格式的日期
   * @Date 10:08 2021/3/19
   * @Param [pattern, days] parttern: 日期格式  days : 表示想要获取几天前的日期 ,如果想要获取一天前的时间则传入 1
   * @return java.lang.String
   **/
  def getDaysAgo(pattern: String, days: Int) = {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DATE, -days)
    val format = new SimpleDateFormat(pattern, Locale.CHINA)
    format.format(calendar.getTime)
  }

  /**
   * @Author MrSun
   * @Description 根据传入的日期格式返回对应的格式的日期
   * @Date 10:12 2021/3/19
   * @Param [pattern, days] parttern: 日期格式  days : 表示想要获取几天前的日期 ,如果想要获取一天后的时间则传入 1
   * @return java.lang.String
   **/
  def getDaysAfter(pattern: String, days: Int) = {
    getDaysAgo(pattern, -days)
  }


  /**
   * @Description : 获取上周日的日期 并根据格式返回对应的时间格式的日期
   * @author : Mr.Sun
   * @date 2021/3/18  13:30
   * @param pattern 需要传入的日期格式
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
