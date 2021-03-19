package com.sun.scala

import java.net.URL
import java.util.Properties

import org.apache.spark.streaming._
import org.apache.spark._
import org.apache.spark.streaming.dstream._


import scala.reflect.io.File
import scala.tools.nsc.io.Path

/**
 * @ClassName SparkStreamingTest
 * @author Mr.Sun
 * @version 1.0.0
 * @Description TODO
 * @createTime 2021年01月21日 15:45:00
 */
object SparkStreamingTest {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName(this.getClass.getName)
    val sc = new SparkContext(conf)
    sc.setLogLevel("warn")
    val ssc = new StreamingContext(sc, Seconds(5))
    val rid: ReceiverInputDStream[String] = ssc.socketTextStream("localhost", 9999)
    val ds= rid.flatMap(line => {
      val strs: Array[String] = line.split(" ")
      strs
    }).map((_,1)).reduceByKey(_+_)
    ds.print()
    ssc.start()
    ssc.awaitTermination()
  }

}
