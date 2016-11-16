package lt.dragas.birc.basic

import lt.dragas.birc.message.Request
import java.io.InputStream
import java.util.*

/**
 * Created by cpartner on 2016-10-06.
 */
/**
 * Contains default methods needed for [InputStream] to function. Since it implements [Runnable] interface,
 * it does not throttle the main thread while waiting for responses from server.
 */
abstract class BasicInput(inputStream: InputStream) : Runnable
{
    private val thread = Thread(this, "BasicInput")
    protected val sin = Scanner(inputStream)
    protected var isRunning: Boolean = false
    @Volatile protected var serverResponses: LinkedList<Request> = LinkedList()

    override fun run()
    {
        isRunning = true
        while (isRunning)
        {
            serverResponses.offerLast(getRequest())
        }
    }

    /**
     * Returns first response from server response queue.
     *
     * Note: elements appended into list are in FIFO order.
     *
     * @return [Request] if the queue is not empty, otherwise null.
     */
    fun obtainRequest(): Request?
    {
        return serverResponses.pollFirst()
    }

    /**
     * Waits for next raw response from server. Mainly needed to "flush" unnecessary lines from server's output such as
     * MOTD, general notifications and other garbage or even to note one of the routes that next response is important.
     * For what ever reason.
     */
    @Synchronized
    fun getRequest(): Request
    {
        val rawRequest = Request(sin.nextLine())
        return rawRequest
    }

    /**
     * Starts this particular thread.
     */

    fun start()
    {
        thread.start()
    }
}