package com.briefscala

import breeze.numerics.sqrt
import org.apache.spark.rdd.RDD
import org.joda.time.DateTime

import scala.language.postfixOps

object SessionFinder {

  def apply(rdd: RDD[(String, DateTime)])
  : RDD[(String, Double)] = {

    val aveByKey = rdd.mapValues(_.getMillisOfDay.toLong).combineByKey[(Long, Int)](
      createCombiner, mergeValue, mergeCombiners)
      .mapValues{case (l,i) => l.toDouble/i}

    val sqrSums = rdd.join(aveByKey)
      .mapValues{case (dt, ave) => (dt.getMillisOfDay - ave)*(dt.getMillisOfDay - ave) round}

    sqrSums.combineByKey[(Long, Int)](
      createCombiner, mergeValue, mergeCombiners)
      .mapValues{case (l,i) => sqrt(l.toDouble/i)*2}
  }

  def createCombiner: Long => (Long, Int) =
    dtm => (dtm, 1)

  def mergeValue: ((Long, Int), Long) => (Long, Int) = {
    case ((dur, i), dtm) =>
      (dur + dtm, i + 1)
  }

  def mergeCombiners: ((Long, Int), (Long, Int)) => ((Long, Int)) = {
    case ((d1, i1), (d2, i2)) =>
      (d1 + d2, i1 + i2)
  }
}
