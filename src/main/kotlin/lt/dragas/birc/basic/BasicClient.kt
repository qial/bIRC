package lt.dragas.birc.basic

import lt.dragas.birc.basic.Route.RouteGroup
import lt.dragas.birc.message.Request
import lt.dragas.birc.message.Response
import java.net.InetSocketAddress
import java.net.Socket

/**
 * Contains default methods needed for [Socket] to work in general.
 * @param routes Route set, which consumes [Request] to generate [Response].
 */
abstract class BasicClient(val routes: Array<RouteGroup>) : Socket(), Listener.Client, Listener.Subscriber
{
    lateinit var settings: BasicSettings
    abstract var sin: BasicInput
    abstract var sout: BasicOutput
    var running = false
    var initiliazed = false
    /**
     * Initializes client from provided file.
     */

    open fun setup(settings: BasicSettings): BasicClient
    {
        this.settings = settings
        return this
    }

    /**
     * Notifies client that the client should connect to server defined in default options file.
     */
    open fun connect()
    {
        connect(InetSocketAddress(settings.server, settings.serverPort))
        onConnect()
        run()
    }

    override fun onPost(response: Response)
    {
        sout.appendResponse(response)
        if (response.rawResponse.startsWith("pong", true))
            onPong()
    }

    override fun onConnect()
    {
        BasicClient.default = this
        running = true
    }

    open fun run()
    {
        outer@ while (running)
        {
            val line = sin.obtainRequest() ?: continue
            routes.forEach {
                if (it.canTrigger(line))
                {
                    return@forEach
                }
            }
        }
    }

    companion object
    {
        lateinit var default: BasicClient
    }
}