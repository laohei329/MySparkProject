package com.sun.scala.json

import com.alibaba.fastjson.JSON

/**
 * @ClassName JsopObjectTest
 * @author Mr.Sun
 * @version 1.0.0
 * @Description TODO
 * @createTime 2021年03月12日 16:09:00
 */
object JsopObjectTest {
  def main(args: Array[String]): Unit = {

    val obj = " {\"age\":\"18\",\"name\":\"hqz\",\"sex\":\"male\",\"asdf\":\"asdfa\"}"
    val ob2 = " {\"age\":\"18\",\"name\":\"hqz\",\"xxx\":\"12\",\"sex\":\"male\"}"
    println(JSON.parseObject(obj, classOf[Person]))
    println(JSON.parseObject(ob2, classOf[Person]))


  }

}
case class Person(age1:Int,name1:String,sex1:String)
