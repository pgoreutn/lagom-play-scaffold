package modules

import akka.actor.ActorSystem
import com.google.inject.{ AbstractModule, Provides }
import net.codingwell.scalaguice.ScalaModule
import play.api.Environment
import play.api.libs.concurrent.AkkaGuiceSupport
import com.lightbend.lagom.scaladsl.client._
import javax.inject.Singleton
import org.example.hello.api.HelloService
import play.api.libs.ws.ahc.AhcWSComponents
import utils.{ MyExecutionContext, MyExecutionContextImpl }

class ApplicationModule extends AbstractModule with ScalaModule with AkkaGuiceSupport {

  @Provides
  def helloServiceClient(factory: LagomClientFactory): HelloService =
    factory.serviceClient.implement[HelloService]

  @Provides
  def lagomServiceClientFactory(env: Environment): LagomClientFactory =
    new StandaloneLagomClientFactory("my-client") with AhcWSComponents with ConfigurationServiceLocatorComponents

  @Provides
  @Singleton
  def myExecutionContext(system: ActorSystem): MyExecutionContext =
    new MyExecutionContextImpl(system)
}
