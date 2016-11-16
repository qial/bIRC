package lt.dragas.birc.routes.channel

import lt.dragas.birc.basic.Route
import lt.dragas.birc.message.Request
import lt.dragas.birc.message.Response

/**
 * Created by cpartner on 2016-10-18.
 */
class Quit() : Route("quit")
{
    override val hasArguments: Boolean = true
    override fun onTrigger(request: Request): Response
    {
        return Response("quit ${request.message}")
    }
}