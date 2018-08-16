package com.weather.utils

import com.typesafe.config.ConfigFactory
import org.apache.commons.lang3.StringUtils

import scala.collection.mutable
import scalaj.http._

object KeyComparison {
  
  val config = ConfigFactory.load("application.conf")
  
  
  def main(args: Array[String]): Unit = {
    require(args.length == 2, "Need Two file names")
    
    val srcData = args.map { arg =>
      io.Source.fromFile(arg, "UTF-8").getLines().toSeq
                           }
    
    val delimiter = config.getString("delimiter").head
    
    
    def isStringEmpty(tstString: String): Boolean = {
      StringUtils.isEmpty(tstString)
    }
    
    
    def aggregateSet(
                      data: Seq[String],
                      zoneSet: Set[String] = Set.empty [String]
                    ): Set[String] = {
      var zones=mutable.Set(zoneSet.toSeq :_*)
      data.tail.foreach { dataLine =>
        val data: Array[String] = dataLine.split(delimiter)
        val zone = data(0)
//        println(s"zone id ${zone}")
        if(zones.contains(zone)) zones.remove(zone) else zones.add(zone)
                        }
      zones.toSet
    }
    
    val oldZoneSet = aggregateSet(srcData.head)
    println(s"""Old Zone Set size ${oldZoneSet.size}""")
    val newZones=srcData.tail.head
    val newZoneSet = aggregateSet(newZones)
    println(s"""new Zone Set size ${newZoneSet.size}""")
    val combinedSet = aggregateSet(newZones,oldZoneSet)
    println(s"""combinded Zone Set size ${combinedSet.size}""")
    val removedOnly = {
      var removedSet=mutable.Set(oldZoneSet.toSeq :_*)
      newZoneSet.foreach{zone =>
        removedSet.remove(zone)
                        }
      removedSet.toSet
    }
    println(s"""removed Zone Set size ${removedOnly.size}""")
    
    println(s"""Removed Zones are ${removedOnly}""")
  }
}
