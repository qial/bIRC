package lt.dragas.birc.routes.channel

import lt.dragas.birc.basic.io.Output
import lt.dragas.birc.basic.route.Controller
import lt.dragas.birc.message.Request
import lt.dragas.birc.message.Response

/**
 * Created by mgrid on 2016-11-04.
 */
class Pose : Controller()
{
    override fun onTrigger(request: Request)
    {
        Output.default.writeResponse(Response("privmsg ${request.target} TL note: menacing\r\nprivmsg ${request.target} ゴゴゴゴ"))
    }
}