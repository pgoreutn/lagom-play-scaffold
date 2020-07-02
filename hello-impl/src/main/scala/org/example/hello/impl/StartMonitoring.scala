package org.example.hello.impl

import kamon.Kamon
import org.slf4j.LoggerFactory
import play.api.{ Configuration, Environment, Mode }
import play.api.inject.ApplicationLifecycle

import scala.concurrent.Future

class StartMonitoring(lifecycle: ApplicationLifecycle, environment: Environment, configuration: Configuration) {

  val log = LoggerFactory.getLogger(classOf[StartMonitoring])

  log.info("Reconfiguring Kamon with Play's Config")

  Kamon.reconfigure(configuration.underlying)
  Kamon.loadModules()

  lifecycle.addStopHook { () =>
    if (environment.mode != Mode.Dev)
      Kamon.stopModules()
    else
      Future.successful(())
  }
}
