import java.util.{Properties, UUID}

import Helper.{produceToKafka, readFile}
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010

object Main {
  val accessKey = "D74BB34F91565DF13A1C"
  val secretKey = "W0MxQjcxNzFCNzczOTYxOTY4RENFMjhERjYzOEE2"
  //s3地址
  val endpoint = "http://10.16.0.1:81"
  //上传到的桶
  val bucket = "yekit"
  //上传文件的路径前缀
  val keyPrefix = "upload/"
  //上传数据间隔 单位毫秒
  val period = 30000
  //输入的kafka主题名称
  val inputTopic = "mn_buy_ticket_2"
  //kafka地址
  val bootstrapServers = "bigdata35.depts.bingosoft.net:29035,bigdata36.depts.bingosoft.net:29036,bigdata37.depts.bingosoft.net:29037"

  val filename = "data"+System.nanoTime() + ".txt"

  def main(args: Array[String]): Unit = {

    // s3 to kafka
    val s3Content = readFile()
    produceToKafka(s3Content)

    // kafka producer to flink
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(2)
    val kafkaProperties = new Properties()
    kafkaProperties.put("bootstrap.servers", bootstrapServers)
    kafkaProperties.put("group.id", UUID.randomUUID().toString)
    kafkaProperties.put("auto.offset.reset", "earliest")
    kafkaProperties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    kafkaProperties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    val kafkaConsumer = new FlinkKafkaConsumer010[String](inputTopic,
      new SimpleStringSchema, kafkaProperties)
    kafkaConsumer.setCommitOffsetsOnCheckpoints(true)
    val inputKafkaStream = env.addSource(kafkaConsumer)

    // flink to s3
    // Transformation of data in S3Writer Class
    inputKafkaStream.writeUsingOutputFormat(new S3Writer(accessKey, secretKey, endpoint, bucket, keyPrefix, period, filename))
    env.execute()
  }
}