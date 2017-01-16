package com.briefscala

import org.joda.time.DateTime

case class Parts(timestamp: DateTime,
                 site: String,
                 ipAndPort: Option[IpPort],
                 localIp: Option[IpPort],
                 u1: Option[Double],
                 u2: Option[Double],
                 u3: Option[Double],
                 u4: Option[Int],
                 u5: Option[Int],
                 u6: Option[Int],
                 u7: Option[Int],
                 request: Request,
                 browser: String,
                 encript: String,
                 protocol: String)
