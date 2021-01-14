package com.sun.test

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object SparkSqlTest {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("cluster")
    val session: SparkSession = SparkSession
      .builder()
      .config(conf)
      //.config("spark.sql.warehouse.dir", "hdfs://172.20.105.112:9000/user/hive/warehouse")
      .appName("spark 测试")

      //.enableHiveSupport()
      .getOrCreate()
    session.sql("show databases").show(1000)



  }

}
