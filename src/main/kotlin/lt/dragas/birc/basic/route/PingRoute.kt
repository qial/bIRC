package lt.dragas.birc.basic.route

import lt.dragas.birc.message.Request
import lt.dragas.birc.routes.server.Pong

class PingRoute : Route("")
{
    override var controller: Controller
        get() = Pong()
        set(value)
        {
        }

    override fun canTrigger(request: Request): Boolean
    {
        if (request.type == PING)
        {
            controller.onTrigger(request)
            return true
        }
        return false
    }
}