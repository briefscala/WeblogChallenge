package com.briefscala

import org.apache.spark.{SparkConf, SparkContext}
import org.scalatest.{BeforeAndAfterAll, Suite}

trait LocalSpark extends  BeforeAndAfterAll { self: Suite =>
  protected def master: String = "local[2]"
  protected def appName: String

  protected def config: SparkConf = {
    new SparkConf()
      .setMaster(master)
      .setAppName(appName)
  }

  implicit protected def context: SparkContext = sc
  private var sc: SparkContext = _

  override def beforeAll() {
    sc = new SparkContext(config)
  }

  override def afterAll() {
    if (sc != null) {
      sc.stop()
    }
  }
}
