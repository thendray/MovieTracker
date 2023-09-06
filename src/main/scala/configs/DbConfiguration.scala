package configs

import slick.jdbc.JdbcBackend.Database


object DbConfiguration {

  lazy val db = Database.forConfig("my-app.db")

}
