package com.sun.scala.structStreaming


import org.apache.spark.sql.streaming.StreamingQuery
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.streaming.Trigger.ProcessingTime

/**
 * @ClassName MyStructStreaming
 * @author Mr.Sun
 * @version 1.0.0
 * @Description TODO
 * @createTime 2021年03月08日 15:32:00
 */
object MyStructStreaming {
  def main(args: Array[String]): Unit = {

    val session = SparkSession.builder()
      .appName(getClass.getName)
      .master("local[4]")
      .getOrCreate()
    import session.implicits._
    val ncDF = session
      .readStream
      .format("socket")
      .option("host", "localhost")
      .option("port", 9999)
      .load()


    val query: StreamingQuery = ncDF.writeStream
      .outputMode("append")
      .format("console")
      .trigger(ProcessingTime("10 seconds"))
      //.option("checkpointLocation", checkpointLocation)
      .start()

    query.awaitTermination()
  }

}
