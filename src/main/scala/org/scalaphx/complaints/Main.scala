package org.scalaphx.complaints

import org.scalaphx.complaints.repositories.InMemoryComplaintRepositoryImpl
import org.scalaphx.complaints.routes.ComplaintRouteProvider
import org.scalaphx.complaints.services.ComplaintServiceImpl

object Main extends App {
  println("Starting...")
  val gateway = ApiModule.createApiGateway
  gateway.startServer(host = "localhost", port = 8080)
}

object ApiModule {

  def createApiGateway: ApiGateway = {
    val complaintRepository = new InMemoryComplaintRepositoryImpl
    val complaintService = new ComplaintServiceImpl(complaintRepository)
    val routeProvider = new ComplaintRouteProvider(complaintService)
    new ApiGateway(routeProvider)
  }
}
