package lt.dragas.birc.routes.channel

import lt.dragas.birc.basic.io.Output
import lt.dragas.birc.basic.route.Controller
import lt.dragas.birc.message.Request
import lt.dragas.birc.message.Response

/**
 * Created by cpartner on 2016-11-08.
 */
class Countdown : Controller()
{
    /**
     *  A callback for route when the it has been successfully triggered.
     *
     *  @param request Message from server to be consumed
     */
    override fun onTrigger(request: Request)
    {
        var i = request.message.toInt()
        while (i > 0)
        {
            val start = System.currentTimeMillis()
            Output.default.writeResponse((Response(request.target, "$i")))
            while (System.currentTimeMillis() - start < 1000)
                continue
            i--
        }
        Output.default.writeResponse(Response(request.target, "GO"))
    }

}