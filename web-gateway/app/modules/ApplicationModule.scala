package modules

import com.google.inject.{ AbstractModule, Provides }
import net.codingwell.scalaguice.ScalaModule
import play.api.Environment
import play.api.libs.concurrent.AkkaGuiceSupport
import com.lightbend.lagom.scaladsl.client._

class ApplicationModule extends AbstractModule with ScalaModule with AkkaGuiceSupport {

  def helloServiceClient(factory: LagomClientFactory)

  @Provides
  def lagomServiceClientFactory(env: Environment): LagomClientFactory = {
    new StandaloneLagomClientFactory("my-client") with AhcWSComponents with ConfigurationServiceLocatorComponents
  }
}
