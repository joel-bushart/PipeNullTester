package com.weather.utils


import scalaj.http._
import com.typesafe.config.ConfigFactory
import org.apache.commons.lang3.StringUtils

object FieldAnalysis {
  
  val config = ConfigFactory.load("application.conf")
  
  
  def main(args: Array[String]): Unit = {
    val response: HttpResponse[String] = Http(config.getString("rawUrl")).asString
    val srcData = io.Source.fromString(response.body).getLines().toSeq
    
    val delimiter = config.getString("delimiter").head
    
    val header = srcData.head.split(delimiter)
    
    var aggregate: Map[String, Int] = header.map { columnName => columnName -> 0 }.toMap
    
    def isStringEmpty(tstString: String): Boolean = {
      StringUtils.isEmpty(tstString)
    }
    
    srcData.tail.foreach { dataLine =>
      val data = dataLine.split(delimiter)
      println(s"adding resort ${data(1)}")
      for(i <- 0 to data.size - 1) {
        val incrementBy: Int = if(isStringEmpty(data(i))) 0 else 1
        val currentCnt: Int = aggregate.get(header(i)).getOrElse(0)
        aggregate += header(i) -> (currentCnt + incrementBy)
      }
    }
    
    for(keypair <- aggregate.toSeq.sortBy(_._2).reverse) yield println(s"${keypair._1} -> ${keypair._2}")
    
  }
}
