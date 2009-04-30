package net.yher.commons.scala.sql

import java.sql._

class Sql(val con : Connection) {
  def eachRow(sql:String)(block:ResultSet => Unit) {
    var ps : PreparedStatement = null
    try {
      ps = con.prepareStatement(sql)
      val rs = ps.executeQuery()
      block(rs)
    } finally {
      if (ps != null) ps.close()
    }
    sql
  }
}

object Sql {
  def newInstance(url : String) : Sql = {
    return new Sql(DriverManager.getConnection(url));
  }
}