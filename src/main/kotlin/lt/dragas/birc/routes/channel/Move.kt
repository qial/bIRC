package lt.dragas.birc.routes.channel

import lt.dragas.birc.basic.io.Output
import lt.dragas.birc.basic.route.Controller
import lt.dragas.birc.message.Request
import lt.dragas.birc.message.Response


class Move : Controller()
{
    /**
     *  A callback for route when the it has been successfully triggered.
     *
     *  @return [Response] - formatted response by this particular route
     */
    override fun onTrigger(request: Request)
    {
        Output.default.writeResponse(Response("join #${request.message}"))
    }

}