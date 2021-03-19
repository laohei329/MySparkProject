package com.sun.scala

import com.alibaba.fastjson.{JSON, JSONObject}
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, HasOffsetRanges, KafkaUtils, LocationStrategies, OffsetRange}
import org.apache.spark.streaming.{Seconds, StreamingContext}




/**
 * @ClassName SparkStreamingFromKafka
 * @author Mr.Sun
 * @version 1.0.0
 * @Description TODO
 * @createTime 2021年02月23日 14:47:00
 */
class SparkStreamingFromKafka {
  val broker_list = "broker_list" //
  var kafkaParams = collection.mutable.Map(
    "bootstrap.servers" -> broker_list, //用于初始化链接到集群的地址
    "key.deserializer" -> classOf[StringDeserializer],
    "value.deserializer" -> classOf[StringDeserializer],
    //用于标识这个消费者属于哪个消费团体
    "group.id" -> "gmall_consumer_group",
    //如果没有初始化偏移量或者当前的偏移量不存在任何服务器上，可以使用这个配置属性
    //可以使用这个配置，latest自动重置偏移量为最新的偏移量
    "auto.offset.reset" -> "latest",
    //如果是true，则这个消费者的偏移量会在后台自动提交,但是kafka宕机容易丢失数据
    //如果是false，会需要手动维护kafka偏移量
    "enable.auto.commit" -> (false: java.lang.Boolean)


  )


  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[4]").setAppName(getClass.getName)
    val ssc = new StreamingContext(conf, Seconds(5))
    val topic = "topic"

    var partition = 9
    var offset: Long = 1L
    var offsets = Map[TopicPartition, Long](new TopicPartition(topic, partition) -> offset)
    val kafkaInputDS: InputDStream[ConsumerRecord[String, String]] = KafkaUtils.createDirectStream(ssc,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](Array(topic), kafkaParams, offsets))
    var offsetRanges: Array[OffsetRange] = null
    val rangeDS: DStream[ConsumerRecord[String, String]] = kafkaInputDS.transform(rdd => {
      offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
      rdd
    }
    )
    val jsonDS: DStream[JSONObject] = rangeDS.map(record => {
      val str: String = record.value()
      val jSONObject: JSONObject = JSON.parseObject(str)
      jSONObject
    })

    ssc.start()
    ssc.awaitTermination()

  }

}
