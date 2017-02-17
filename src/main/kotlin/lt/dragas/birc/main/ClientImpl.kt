package lt.dragas.birc.main

import lt.dragas.birc.basic.Client
import lt.dragas.birc.basic.Input
import lt.dragas.birc.basic.Output
import lt.dragas.birc.basic.Route
import lt.dragas.birc.message.Response

/**
 * Example implementation of this framework's [ClientImpl] class.
 */
class ClientImpl(routes: Array<Route.RouteGroup>) : Client(routes)
{
    override lateinit var sin: Input
    override lateinit var sout: Output

    override fun onConnect()
    {
        super.onConnect()
        var responseCount = 0
        sin = InputImpl(inputStream)
        sout = OutputImpl(outputStream)
        sin.start()
        while (responseCount < 2)
        {
            val response = sin.obtainRequest()
            if (response == null)
            {
                Thread.sleep(500)
                continue
            }
            responseCount++
        }
        sout.writeResponse("user ${settings.username} ${settings.mode} ${settings.unused} ${settings.realName}")
        sout.writeResponse("nick ${settings.nicknames[0]}")
    }

    /**
     * A listener that is triggered when client receives a ping request.
     *
     * Anything here is triggered every time after client successfully sends a pong response.
     */
    override fun onPong()
    {
        if (!initiliazed)
        {
            initiliazed = true
            settings.channels.forEach {
                sout.writeResponse(Response("join $it"))
            }
        }
    }
}