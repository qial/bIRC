package lt.dragas.birc.routes.channel

import lt.dragas.birc.basic.Route
import lt.dragas.birc.main.MessageManager
import lt.dragas.birc.message.Request
import lt.dragas.birc.message.Response

/**
 * Created by cpartner on 2016-11-08.
 */
class Countdown() : Route("countdown")
{
    override val type: Int = CHANNEL
    override val hasArguments: Boolean = true
    /**
     *  A callback for route when the it has been successfully triggered.
     *
     *  @return [Response] - formatted response by this particular route
     */
    override fun onTrigger(request: Request): Response
    {
        var i = request.message.toInt()
        while (i > 0)
        {
            val start = System.currentTimeMillis()
            MessageManager.post(Response(request.target, "$i"))
            while (System.currentTimeMillis() - start < 1000)
                continue
            i--
        }
        return Response(request.target, "GO")
    }

}