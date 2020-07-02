package utils

import akka.actor.ActorSystem
import javax.inject.Inject
import play.api.libs.concurrent.CustomExecutionContext

import scala.concurrent.ExecutionContext

trait MyExecutionContext extends ExecutionContext

class MyExecutionContextImpl @Inject() (system: ActorSystem)
  extends CustomExecutionContext(system, "my.executor")
  with MyExecutionContext
