package org.scalaphx.complaints

import akka.http.scaladsl.server.{HttpApp, Route}
import org.scalaphx.complaints.routes.ComplaintRouteProvider

class ApiGateway(routeProvider: ComplaintRouteProvider) extends HttpApp {
  override protected def routes: Route = routeProvider.routes
}
