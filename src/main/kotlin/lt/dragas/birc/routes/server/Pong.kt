package lt.dragas.birc.routes.server

import lt.dragas.birc.basic.Route
import lt.dragas.birc.message.Request
import lt.dragas.birc.message.Response

/**
 * Created by cpartner on 2016-10-18.
 */
class Pong() : Route("ping")
{
    override val type: Int = PING

    override fun canTrigger(request: Request): Boolean
    {
        if (request.type == type)
        {
            doTrigger(request)
            return true
        }
        return false
    }

    override fun onTrigger(request: Request): Response
    {
        return Response("pong ${request.message}")
    }
}