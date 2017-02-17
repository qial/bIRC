package lt.dragas.birc.basic


import lt.dragas.birc.basic.io.Input
import lt.dragas.birc.basic.io.Output
import lt.dragas.birc.basic.listeners.Listener
import lt.dragas.birc.basic.route.Route
import lt.dragas.birc.message.Request
import lt.dragas.birc.message.Response
import java.net.InetSocketAddress
import java.net.Socket

/**
 * Contains default methods needed for [Socket] to work in general.
 * @param routes Route set, which consumes [Request] to generate [Response].
 */
abstract class Client(val routes: Array<Route.RouteGroup>) : Socket(), Listener.Client
{
    lateinit var settings: Settings
    abstract var sin: Input
    abstract var sout: Output
    var running = false
    var initiliazed = false
    /**
     * Initializes client from provided file.
     */

    open fun setup(settings: Settings): Client
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

    override fun onConnect()
    {
        Client.default = this
        running = true
    }

    open fun run()
    {
        outer@ while (running)
        {
            val request = sin.obtainRequest() ?: continue
            Thread(Runnable {
                routes.forEach {
                    if (it.canTrigger(request))
                        return@Runnable
                }
            }, "Thread finder").start()
        }
    }
    companion object
    {
        lateinit var default: Client
    }
}