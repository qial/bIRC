package lt.dragas.birc.routes.channel

import lt.dragas.birc.basic.Route
import lt.dragas.birc.message.Request
import lt.dragas.birc.message.Response

/**
 * Created by cpartner on 2016-10-18.
 */
class Move() : Route("move")
{
    /**
     *  A callback for route when the it has been successfully triggered.
     *
     *  @return [Response] - formatted response by this particular route
     */
    override fun onTrigger(request: Request): Response
    {
        return Response("join #${request.message}")
    }

}