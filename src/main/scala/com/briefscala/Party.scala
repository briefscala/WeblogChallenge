package com.briefscala

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

import scala.language.higherKinds
import scala.util.Try

trait Party[M[_] <: Seq[_], A] {
  def toPart(a: Seq[A])(implicit ip: IP[A], req: Req[A]): Option[Parts]
}

object Party {
  val formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ").withOffsetParsed()
  implicit val implicitParts: Party[Seq, String] =
    new Party[Seq, String] {
      def toPart(ls: Seq[String])(implicit ip: IP[String], req: Req[String])
      : Option[Parts] = {
        val args = ls.zipWithIndex.map {
          case (s, 0) => DateTime.parse(s, formatter).withMillisOfSecond(0)
          case (s, 1) => s
          case (s, 2) => ip.toIp(s)
          case (s, 3) => ip.toIp(s)
          case (s, 4) => Try(s.toDouble).toOption
          case (s, 5) => Try(s.toDouble).toOption
          case (s, 6) => Try(s.toDouble).toOption
          case (s, 7) => Try(s.toInt).toOption
          case (s, 8) => Try(s.toInt).toOption
          case (s, 9) => Try(s.toInt).toOption
          case (s, 10) => Try(s.toInt).toOption
          case (s, 11) => req.toReq(s)
          case (s, 12) => s
          case (s, 13) => s
          case (s, 14) => s
          case (s, _) => s
        }
        fromList[Parts](args)
      }
    }
}
