package lt.dragas.birc.routes.server

import lt.dragas.birc.basic.Client
import lt.dragas.birc.basic.io.Output
import lt.dragas.birc.basic.route.Controller
import lt.dragas.birc.message.Request
import lt.dragas.birc.message.Response


class Pong : Controller()
{

    override fun onTrigger(request: Request)
    {
        Output.default.writeResponse(Response("pong ${request.message}"))
        Client.default.onPong()
    }
}