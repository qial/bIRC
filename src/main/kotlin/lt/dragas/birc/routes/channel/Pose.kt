package lt.dragas.birc.routes.channel

import lt.dragas.birc.basic.Route
import lt.dragas.birc.message.Request
import lt.dragas.birc.message.Response

/**
 * Created by mgrid on 2016-11-04.
 */
class Pose : Route("pose")
{
    override val type: Int
        get() = CHANNEL
    override val hasArguments: Boolean
        get() = false

    override fun onTrigger(request: Request): Response
    {
        return Response("privmsg ${request.target} TL note: menacing\r\nprivmsg ${request.target} ゴゴゴゴ")
    }
}