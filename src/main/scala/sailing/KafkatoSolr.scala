package main.scala.sailing

import java.io.{File, IOException}

import org.apache.hadoop.conf.Configuration
import com.lucidworks.spark.{SolrSupport, SparkApp}
import kafka.serializer.{DefaultDecoder, StringDecoder}
import java.util.Properties

import org.apache.spark.Logging
import org.apache.spark.serializer.KryoRegistrator
import java.io.File
import java.io.FileInputStream
import java.util.{Date, Locale}


import sailing.example.streaming.EventParseUtil
import sailing.solr.SolrException




// in order to pick up implicits that allow DStream.reduceByKey, ... (versus DStream.transform(rddBatch => rddBatch.reduceByKey())
// See https://www.mail-archive.com/user@spark.apache.org/msg10105.html
import org.apache.spark.streaming.StreamingContext.toPairDStreamFunctions
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}
import com.esotericsoftware.kryo.Kryo

object KafkatoSolr extends Logging {

  private var SOLR_KBS_ENABLED: String = null
  private var zkClientTimeout: Int = 0
  private var zkConnectTimeout: Int = 0
  private var ZK_URL: String = null
  var KafKa_ZK_URL: String = null
  private var ZOOKEEPER_DEFAULT_SERVER_PRINCIPAL: String = null
  private var COLLECTION_NAME: String = null
  private var DEFAULT_CONFIG_NAME: String = null
  private var shardNum: Int = 0
  private var replicaNum: Int = 0
  private var principal: String = null
  @transient
  val eventParseUtil = new EventParseUtil
  var path: String = ""


  def main(args: Array[String]) {
    System.setProperty("user.timezone", "Asia/Shanghai")
    val Array(batchTime, topics, brokers,numThreads,pathstr) = args
    System.out.println(pathstr)
    initProperties(pathstr)
    var randomString = new RandomString()

  //  com.lucidworks.spark.SolrSupport.SOLRSUPPORT_PRINCIPAL = principal
    //com.lucidworks.spark.SolrSupport.SOLRSUPPORT_KEYTAB = path + "user.keytab"
   // com.lucidworks.spark.SolrSupport.SOLRSUPPORT_KRB5CONF = path + "krb5.conf"

    val ZOOKEEPER_DEFAULT_LOGIN_CONTEXT_NAME: String = "Client"
    val ZOOKEEPER_SERVER_PRINCIPAL_KEY: String = "zookeeper.server.principal"
    val hadoopConf: Configuration = new Configuration()
    hadoopConf.addResource("/opt/client/Yarn/config/core-site.xml")
    hadoopConf.addResource("/opt/client/Yarn/config/hdfs-site.xml")
    hadoopConf.addResource("/opt/client/Yarn/config/hbase-site.xml")

    System.setProperty("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
    System.setProperty("spark.kryo.registrator", "main.scala.sailing.MyRegistrator")

   // val Array(batchTime, topics, brokers,numThreads) = args

    val sparkConf = new SparkConf()
      .setAppName("kafka-sparkstream-Solr")
    // .set("spark.cleaner.ttl", "120000")   // for long running tasks
    val batchDuration = Seconds(batchTime.toInt)
    val sc = new SparkContext(sparkConf)
    val ssc = new StreamingContext(sc, batchDuration)


    ssc.checkpoint("kafkasparkSolr") // to fetch State for stateful RDD

    // Get the list of topic used by kafka
    //val topicsSet = topics.split(",").toSet
    val topicsMap = topics.split(",").map((_,numThreads.toInt)).toMap

    //val topicsMap = Map("kkjgcl_solr"->6)
    val kafkaParams = Map[String, String]("metadata.broker.list" -> brokers,
      "zookeeper.connect" -> ZK_URL,"group.id" -> "kafkasparkSolr")
    val kafkastreamZK=KafkaUtils.createStream( ssc, KafKa_ZK_URL/*"10.162.121.81:24002,10.162.121.85:24002,10.162.121.92:24002/kafka"*/, "kafkasparkSolr",topicsMap).map(_._2)

    //kafkaStream.checkpoint(Duration(60000))
    var parsedSolrEvents = kafkastreamZK.map(incomingRecord => eventParseUtil.convertData(incomingRecord))
    System.out.println(" parsedSolrEvents.print()--" + parsedSolrEvents.print(5))


    SolrSupport.indexDStreamOfDocs(ZK_URL, COLLECTION_NAME, 10, parsedSolrEvents)

    ssc.start()
    ssc.awaitTermination()


  }



  @throws[SolrException]
  private def initProperties(pathstr:String):Unit= {
    val properties: Properties = new Properties
    //val proPath: String = System.getProperty("user.dir") + File.separator + "conf" + File.separator + "solr-example.properties"

    //var proPath: String = "/opt/client/conf/solr-config.properties"
    var proPath=pathstr
    proPath = proPath.replace("\\", "\\\\")
    try {
      properties.load(new FileInputStream(new File(proPath)))
    }
    catch {
      case e: IOException => {
        throw new SolrException("Failed to load properties file : " + proPath)
      }
    }
    path= properties.getProperty("PATH")
    SOLR_KBS_ENABLED = properties.getProperty("SOLR_KBS_ENABLED")
    zkClientTimeout = Integer.valueOf(properties.getProperty("zkClientTimeout"))
    zkConnectTimeout = Integer.valueOf(properties.getProperty("zkConnectTimeout"))
    ZK_URL = properties.getProperty("ZK_URL")
    KafKa_ZK_URL = properties.getProperty("KafKa_ZK_URL")
    ZOOKEEPER_DEFAULT_SERVER_PRINCIPAL = properties.getProperty("ZOOKEEPER_DEFAULT_SERVER_PRINCIPAL")
    COLLECTION_NAME = properties.getProperty("COLLECTION_NAME")
    DEFAULT_CONFIG_NAME = properties.getProperty("DEFAULT_CONFIG_NAME")
    shardNum = Integer.valueOf(properties.getProperty("shardNum"))
    replicaNum = Integer.valueOf(properties.getProperty("replicaNum"))
    principal = properties.getProperty("principal")

    System.out.println("solrConfig::-path-:" + path )
    System.out.println("solrConfig::ZK_URL-:" +ZK_URL)
    System.out.println("solrConfig::KafKa_ZK_URL-:" +KafKa_ZK_URL)
    System.out.println("solrConfig::COLLECTION_NAME-: "+COLLECTION_NAME)
    System.out.println("solrConfig::principal-:"+principal)
    val ZKServerPrincipal = "zookeeper/hadoop.hadoop.com"
    val ZOOKEEPER_DEFAULT_LOGIN_CONTEXT_NAME: String = "Client"
    val ZOOKEEPER_SERVER_PRINCIPAL_KEY: String = "zookeeper.server.principal"
    val hadoopConf: Configuration  = new Configuration();
    LoginUtil.setJaasConf(ZOOKEEPER_DEFAULT_LOGIN_CONTEXT_NAME, principal, path + "user.keytab")
    LoginUtil.setZookeeperServerPrincipal(ZOOKEEPER_SERVER_PRINCIPAL_KEY, ZKServerPrincipal)
    LoginUtil.login(principal, path + "user.keytab", path + "krb5.conf", hadoopConf);
  }
}

class MyRegistrator extends KryoRegistrator {
  override def registerClasses(kryo: Kryo) {
//    kryo.register(classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable])
//    kryo.register(classOf[org.apache.hadoop.hbase.client.Result])
  kryo.register(classOf[Array[(Any, Any)]])
//    kryo.register(classOf[Array[org.apache.hadoop.hbase.Cell]])
//    kryo.register(classOf[org.apache.hadoop.hbase.NoTagsKeyValue])
//    kryo.register(classOf[org.apache.hadoop.hbase.protobuf.generated.ClientProtos.RegionLoadStats])
  }
}