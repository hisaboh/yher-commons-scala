package net.yher.commons.scala.sql

import java.sql._

class Sql(val con : Connection) {
    def eachRow(sql:String)(block:ResultSet => Unit) {
        var ps : PreparedStatement = null
        var rs : ResultSet = null
        try {
            ps = con.prepareStatement(sql)
            rs = ps.executeQuery()
            while (rs.next()) {
                block(rs)
            }
        } finally {
            if (rs != null) rs.close()
            if (ps != null) ps.close()
        }
    }

    def eachRow(ps : PreparedStatement)(block : ResultSet => Unit) {
        var rs:ResultSet = null
        try {
            rs = ps.executeQuery()
            while (rs.next()) {
                block(rs)
            }
        } finally {
            if (rs != null) rs.close()
        }
    }
}

object Sql {
    def newInstance(url : String) : Sql = {
        return new Sql(DriverManager.getConnection(url));
    }

    def newInstance(url : String, user : String, password : String) {
        return new Sql(DriverManager.getConnection(url, user, password))
    }
}