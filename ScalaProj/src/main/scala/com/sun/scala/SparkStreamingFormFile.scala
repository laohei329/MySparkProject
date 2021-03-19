package com.sun.scala


import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming._
import org.apache.spark._
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka010._

import scala.reflect.io.Path

/**
 * @ClassName SparkStreamingFormFile
 * @author Mr.Sun
 * @version 1.0.0
 * @Description TODO
 * @createTime 2021年01月22日 09:11:00
 */
object SparkStreamingFormFile {


  var isFirst = true

  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName(getClass.getName).setMaster("local[*]")
    val sc = new SparkContext(conf)
    val ssc = new StreamingContext(sc, Seconds(5))
    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "172.20.105.112:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "groupId",
      "auto.offset.reset" -> "earliest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )
    val topics = Array("hw_edu_new")

    var offsets = Map(new TopicPartition("hw_edu_new", 0) -> 29326182L)
    var topicPartitions = Map(new TopicPartition("hw_edu_new", 0) -> 29326182L)

    val inputDS: InputDStream[ConsumerRecord[String, String]] = if (!isFirst) {
      KafkaUtils.createDirectStream[String, String](
        ssc,
        LocationStrategies.PreferConsistent,
        ConsumerStrategies.Subscribe[String, String](topics, kafkaParams))
    } else {
      KafkaUtils.createDirectStream[String, String](ssc,
        LocationStrategies.PreferConsistent,
        ConsumerStrategies.Assign[String, String](topicPartitions.keys, kafkaParams, offsets))
    }

    inputDS.foreachRDD(rdd => {
      val ranges: Array[OffsetRange] = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
      println(rdd.map(_.value()))
      ranges.foreach(
        range => {
          val topic: String = range.topic
          val partition: Int = range.partition
          val offset: Long = range.fromOffset
          val untilOffset: Long = range.untilOffset
        }
      )

    }
    )

    ssc.start()
    ssc.awaitTermination()

  }
}
