package lt.dragas.birc.main

import lt.dragas.birc.basic.*
import lt.dragas.birc.message.Response

/**
 * Example implementation of this framework's [BasicClient] class.
 */
class Client(routes: Array<Route.RouteGroup>) : BasicClient(routes)
{
    override lateinit var sin: BasicInput
    override lateinit var sout: BasicOutput
    override fun setup(settings: BasicSettings): BasicClient
    {
        MessageManager.register(this)
        return super.setup(settings)
    }

    override fun onConnect()
    {
        super.onConnect()
        var responseCount = 0
        sin = Input(inputStream)
        sout = Output(outputStream)
        sin.start()
        sout.start()
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
        sout.writeResponse("nick nerdyy")
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
                sout.appendResponse(Response("join $it"))
            }
        }
    }
}