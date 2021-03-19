package com.sun.scala.sparkStreaming


import com.alibaba.fastjson.{JSON, JSONObject}
import com.sun.scala.utils.MyKafkaUtil
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.TopicPartition
import org.apache.spark.streaming.kafka010._
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming._
import org.apache.spark.streaming.dstream.{DStream, InputDStream}

import scala.collection.mutable

/**
 * @ClassName MySparkStreaming
 * @author Mr.Sun
 * @version 1.0.0
 * @Description TODO
 * @createTime 2021年03月05日 13:13:00
 */
object MySparkStreaming {


  var isFirst: Boolean = true


  def main(args: Array[String]): Unit = {


    val conf: SparkConf = new SparkConf().setAppName(getClass.getName).setMaster("local[4]")
    //new StreamingContext(conf,Seconds(5))  //会创建一个新的sparkcontext环境
    val sc = new SparkContext(conf)
    val ssc = new StreamingContext(sc, Seconds(5))
    val topics = Array("topics")
    val groupId = "mygroupid"
    var offsets: mutable.Map[TopicPartition, Long] = mutable.Map[TopicPartition, Long]()

    val inpuDS: InputDStream[ConsumerRecord[String, String]] = if (isFirst) {
      MyKafkaUtil.getSparkStreamingFromKafkaFirst(ssc, topics, groupId)
    } else {
      MyKafkaUtil.getSparkStreamingFromKafkaByOffsets(ssc, topics, offsets, groupId)
    }
    var ranges: Array[OffsetRange] = null
    val rangeDS: DStream[ConsumerRecord[String, String]] = inpuDS.transform(
      rdd => {
        ranges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
        rdd
      }
    )

    val jsonDS: DStream[JSONObject] = rangeDS.map {
      record =>
        val str: String = record.value()
        val jSONObject: JSONObject = JSON.parseObject(str)
        jSONObject
    }





    ssc.start()
    ssc.awaitTermination()

  }


}
