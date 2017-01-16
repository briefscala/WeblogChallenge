package com.briefscala

trait Req[A] {
  def toReq(a: A): Request
}

object Req {
  implicit val implicitReq: Req[String] = new Req[String] {
    def toReq(a: String) = {
      val Array(m, u, p) = a.split(' ')
      Request(m, u, p)
    }
  }
}