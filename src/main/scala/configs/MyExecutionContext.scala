package configs

import java.util.concurrent.Executors
import scala.concurrent.ExecutionContext

object MyExecutionContext {

  val executor = Executors.newFixedThreadPool(4)
  implicit val executionContext = ExecutionContext.fromExecutorService(executor)

}
