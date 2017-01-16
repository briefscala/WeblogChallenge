package com.briefscala

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Main {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf()
      .setAppName("weblog-app")
      .set("spark.eventLog.enabled", "true")
      .set("spark.cores.max", "4")
      .set("spark.eventLog.dir", "/home/yoeluk/sparkdir")
      .setMaster("local[*]")

    implicit val sc = new SparkContext(conf)

    val lines = sc.textFile("data/2015_07_22_mktplace_shop_web_log_sample.log")

    /**
      * parses the RRD[String] containing the lines sin the log file to log Parts
      */
    val parts: RDD[Parts] = lines.map(getLogParts('"'))
      .map(_.filter(_.trim.nonEmpty))
      .flatMap(toParty)
      .filter(_.ipAndPort.nonEmpty) /** guarantee that we can call `.get` later on */

    val byUser: RDD[(String, Parts)] =
      parts.keyBy(_.ipAndPort.get.ip).cache()
      .coalesce(25, shuffle = true)

    val res: RDD[(String, Double)] = SessionFinder(byUser.mapValues(_.timestamp))

    val topTen = res.sortBy(_._2, ascending = false).take(10).mkString(",")

    val stats = res.map(_._2).stats()

    /**
      * average session time
      */
    val mean = stats.mean.round

    val urlsByUser = byUser.mapValues(_.request.url.trim)

    /**
      * count of unique urls visited per user
      */
    urlsByUser.distinct().countByKey()

    /**
      * number of unique urls visited
      */
    val size = urlsByUser.values.countApproxDistinct()

    println("number of unique visits: " + size)

    println("average session time: " + mean)

    println("top ten engaged users: " + topTen)
  }
}
