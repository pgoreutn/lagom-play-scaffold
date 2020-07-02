package org.example.hellostream.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.client.ConfigurationServiceLocatorComponents
import com.lightbend.lagom.scaladsl.server._
import com.softwaremill.macwire._
import org.example.hello.api.HelloService
import org.example.hellostream.api.HelloStreamService
import play.api.libs.ws.ahc.AhcWSComponents

trait HelloStreamComponents extends LagomServerComponents
  with AhcWSComponents {

}

class HelloStreamLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new HelloStreamApplication(context) {
      override def serviceLocator: NoServiceLocator.type = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new HelloStreamApplication(context) with ConfigurationServiceLocatorComponents

  override def describeService = Some(readDescriptor[HelloStreamService])
}

abstract class HelloStreamApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
  with AhcWSComponents
  with HelloStreamComponents {

  // Bind the HelloService client
  lazy val helloService: HelloService = serviceClient.implement[HelloService]

  // Bind the service that this server provides
  override lazy val lagomServer: LagomServer = serverFor[HelloStreamService](wire[HelloStreamServiceImpl])
}
