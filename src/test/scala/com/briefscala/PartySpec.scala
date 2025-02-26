package com.briefscala

import org.scalatest.{Matchers, WordSpec}

class PartySpec extends WordSpec with Matchers {
  protected val appName = "party-spec-spark"

  val s1 = """015-07-22T09:00:28.019143Z marketpalce-shop 123.242.248.130:54635 10.0.6.158:80 0.000022 0.026109 0.00002 200 200 0 699 "GET https://paytm.com:443/shop/authresponse?code=f2405b05-e2ee-4b0d-8f6a-9fed0fcfe2e0&state=null HTTP/1.1" "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.130 Safari/537.36" ECDHE-RSA-AES128-GCM-SHA256 TLSv1.2"""
  val s2 = """2015-07-22T09:00:27.894580Z marketpalce-shop 203.91.211.44:51402 10.0.4.150:80 0.000024 0.15334 0.000026 200 200 0 1497 "GET https://paytm.com:443/shop/wallet/txnhistory?page_size=10&page_number=0&channel=web&version=2 HTTP/1.1" "Mozilla/5.0 (Windows NT 6.1; rv:39.0) Gecko/20100101 Firefox/39.0" ECDHE-RSA-AES128-GCM-SHA256 TLSv1.2"""
  val s3 = """2015-07-22T09:00:27.885745Z marketpalce-shop 1.39.32.179:56419 10.0.4.244:80 0.000024 0.164958 0.000017 200 200 0 157 "GET https://paytm.com:443/shop/wallet/txnhistory?page_size=10&page_number=0&channel=web&version=2 HTTP/1.1" "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.134 Safari/537.36" ECDHE-RSA-AES128-GCM-SHA256 TLSv1.2"""
  val s4 = """2015-07-22T09:00:28.048369Z marketpalce-shop 180.179.213.94:48725 10.0.6.108:80 0.00002 0.002333 0.000021 200 200 0 35734 "GET https://paytm.com:443/shop/p/micromax-yu-yureka-moonstone-grey-MOBMICROMAX-YU-DUMM141CD60AF7C_34315 HTTP/1.0" "-" ECDHE-RSA-AES128-GCM-SHA256 TLSv1.2"""
  val s5 = """2015-07-22T09:00:28.036251Z marketpalce-shop 120.59.192.208:13527 10.0.4.217:80 0.000024 0.015091 0.000016 200 200 68 640 "POST https://paytm.com:443/papi/v1/expresscart/verify HTTP/1.1" "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.89 Safari/537.36" ECDHE-RSA-AES128-GCM-SHA256 TLSv1.2"""
  val s6 = """2015-07-22T09:00:28.033793Z marketpalce-shop 117.239.195.66:50524 10.0.6.195:80 0.000024 0.02157 0.000021 200 200 0 60 "GET https://paytm.com:443/api/user/favourite?channel=web&version=2 HTTP/1.1" "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.134 Safari/537.36" ECDHE-RSA-AES128-GCM-SHA256 TLSv1.2"""
  val s7 = """2015-07-22T09:00:28.055029Z marketpalce-shop 101.60.186.26:33177 10.0.4.244:80 0.00002 0.001098 0.000022 200 200 0 1150 "GET https://paytm.com:443/favicon.ico HTTP/1.1" "Mozilla/5.0 (Windows NT 6.3; rv:27.0) Gecko/20100101 Firefox/27.0" ECDHE-RSA-AES128-GCM-SHA256 TLSv1.2"""
  val strSeq = Seq(s1, s2, s3, s4, s5, s6, s7)

  "PartySpec" should {
    "parse string log in Parts" in {
      val parties= strSeq.map(getLogParts('"'))
        .map(_.filter(_.trim.nonEmpty))
        .flatMap(toParty)

      parties.length shouldBe 7
    }
  }
}
