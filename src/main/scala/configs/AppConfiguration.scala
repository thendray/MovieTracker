package configs

import com.typesafe.config.{Config, ConfigFactory}

object AppConfiguration {

  val configs: Config = ConfigFactory.load("application.conf")

}
