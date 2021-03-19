package com.sun.scala.utils

import java.io.FileInputStream
import java.util.Properties

import com.sun.scala.sparkStreaming.PropertiesTest.getClass

/**
 * @ClassName PropertiesUtil
 * @author Mr.Sun
 * @version 1.0.0
 * @Description TODO
 * @createTime 2021年03月05日 18:32:00
 */
object PropertiesUtil {

  def getProperties(propertiesName: String):Properties={
    val properties = new Properties()
    val path: String = getClass.getClassLoader.getResource(propertiesName).getPath
    properties.load(new FileInputStream(path))
    properties
  }
  def getProperties(propertiesName: String,key:String):String={
    val properties = new Properties()
    val path: String = getClass.getClassLoader.getResource(propertiesName).getPath
    properties.load(new FileInputStream(path))
    val value = properties.get(key).toString
    value
  }
}
