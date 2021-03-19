package com.sun.scala.utils


import java.util

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.logging.log4j.util.PropertiesUtil
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import redis.clients.jedis._


/**
 * @ClassName MyKafkaUtil
 * @author Mr.Sun
 * @version 1.0.0
 * @Description TODO
 * @createTime 2021年03月05日 14:09:00
 */
object MyKafkaUtil {
  private val bootstrap_servers: String = PropertiesUtil.getSystemProperties.getProperty("kafka.bootstrap.servers")
  var kafkaParams = collection.mutable.Map[String, Object](
    "bootstrap.servers" -> bootstrap_servers,
    "key.deserializer" -> classOf[StringDeserializer],
    "value.deserializer" -> classOf[StringDeserializer],
    "group.id" -> "groupId",
    "auto.offset.reset" -> "latest",
    "enable.auto.commit" -> (false: java.lang.Boolean)
  )


  def getSparkStreamingFromKafkaFirst(ssc: StreamingContext, topics: Iterable[String], groupId: String) = {
    kafkaParams("group.id") = groupId
    val value: InputDStream[ConsumerRecord[String, String]] = KafkaUtils.createDirectStream(ssc,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](topics, kafkaParams))
    value
  }

  def getSparkStreamingFromKafkaByOffsets(ssc: StreamingContext, topics: Iterable[String],
                                          offsets: collection.mutable.Map[TopicPartition, Long],
                                          groupId: String) = {
    kafkaParams("group.id") = groupId
    val value: InputDStream[ConsumerRecord[String, String]] = KafkaUtils.createDirectStream(ssc,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](topics, kafkaParams, offsets))
    value
  }


}
