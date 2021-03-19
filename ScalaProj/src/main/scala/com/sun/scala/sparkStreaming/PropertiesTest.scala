package com.sun.scala.sparkStreaming

import java.io.{FileInputStream, InputStreamReader}
import java.net.URL
import java.util.Properties

import com.sun.scala.utils.PropertiesUtil


/**
 * @ClassName PropertiesTest
 * @author Mr.Sun
 * @version 1.0.0
 * @Description TODO
 * @createTime 2021年03月05日 18:12:00
 */
object PropertiesTest {

  def main(args: Array[String]): Unit = {

    val properties: Properties = PropertiesUtil.getProperties("config.properties")
    println(properties.getProperty("redis.host.host"))
    println(PropertiesUtil.getProperties("config.properties", "redis.host.ip"))
  }
}
