package com.briefscala

import scala.util.Try

trait IP[A] {
  def toIp(a: A): Option[IpPort]
}

object IP {
  implicit val implicitIp = new IP[String] {
    def toIp(a: String): Option[IpPort] = {
      a.split(':').toList match {
        case ip :: port :: Nil =>
          Try(IpPort(ip, port.toInt)).toOption
        case ip :: Nil => Some(IpPort(ip, 80))
        case _ => None
      }
    }
  }
}