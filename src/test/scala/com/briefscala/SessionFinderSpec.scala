package com.briefscala

import org.apache.spark.rdd.RDD
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.scalatest.WordSpec
import org.scalatest.Matchers

class SessionFinderSpec extends WordSpec with Matchers with LocalSpark {
  protected val appName = "time-average-spark"

  val formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ").withOffsetParsed()

  "SessionFinder" should {
    "return the session time window" in {
      val seq1 = Seq(
        (Option(IpPort("59.162.209.77",26471)), DateTime.parse("2015-07-22T09:00:20.000Z", formatter)),
        (Option(IpPort("59.162.209.77",41831)), DateTime.parse("2015-07-22T09:00:21.000Z", formatter)),
        (Option(IpPort("59.162.209.77",42953)), DateTime.parse("2015-07-22T09:00:22.000Z", formatter)),
        (Option(IpPort("54.251.128.29",52951)), DateTime.parse("2015-07-22T09:00:23.000Z", formatter)),
        (Option(IpPort("54.251.128.29",43446)), DateTime.parse("2015-07-22T09:00:24.000Z", formatter)),
        (Option(IpPort("54.251.128.29",41445)), DateTime.parse("2015-07-22T09:00:25.000Z", formatter)),
        (Option(IpPort("54.251.128.29",61722)), DateTime.parse("2015-07-22T09:00:26.000Z", formatter)),
        (Option(IpPort("192.71.175.30",54182)), DateTime.parse("2015-07-22T09:00:27.000Z", formatter)),
        (Option(IpPort("192.71.175.30",16998)), DateTime.parse("2015-07-22T09:00:28.000Z", formatter)),
        (Option(IpPort("192.71.175.30",11510)), DateTime.parse("2015-07-22T09:00:30.000Z", formatter))
      ) . map { case (opt, dt) => (opt.get.ip, dt)}

      val expectedResult = Seq(
        ("192.71.175.30",2494), /** 2 * stdev */
        ("54.251.128.29",2236),
        ("59.162.209.77",1633)
      ) . sortBy(_._1)

      val rdd: RDD[(String, DateTime)] = context.parallelize(seq1).cache()

      val result = SessionFinder(rdd).mapValues(_.round)
        .collect().toList.sortBy(_._1)

      result shouldBe expectedResult
    }
  }
}
