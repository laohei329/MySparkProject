import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.ReceiverInputDStream
import org.apache.spark.streaming.{Seconds, StreamingContext}



object SparkStreamingTest001 {

  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf().setAppName("xxxx").setMaster("local[2]")
    val sc = new StreamingContext(conf, Seconds(5))
    val line: ReceiverInputDStream[String] = sc.socketTextStream("127.0.0.1", 9999)
    line.print()

    sc.start()
    sc.awaitTermination()
  }

}
