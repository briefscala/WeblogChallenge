package com

import scala.language.postfixOps

package object briefscala {

  def fromList[T] = new FromList[T]

  def getLogParts(inv: Char, sep: Char = ' '): String => List[String] =
    log => {
      val regex = s"$inv*$inv"
      log.split(regex).toList match {
        case head :: request :: browser :: other :: rest :: Nil =>
          head.split(sep) ++
            List(request) ++
            browser.split(sep) ++
            List(other) ++
            rest.split(sep) toList
        case _ => List.empty
      }
    }

  def toParty(a: List[String]) = implicitly[Party[Seq, String]].toPart(a)
}
