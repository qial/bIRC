package lt.dragas.birc.routes.channel

import lt.dragas.birc.basic.io.Output
import lt.dragas.birc.basic.route.Controller
import lt.dragas.birc.message.Request
import lt.dragas.birc.message.Response


class Quit() : Controller()
{
    override fun onTrigger(request: Request)
    {
        Output.default.writeResponse(Response("quit ${request.message}"))
    }
}