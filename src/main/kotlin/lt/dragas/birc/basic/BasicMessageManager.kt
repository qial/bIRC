package lt.dragas.birc.basic

import lt.dragas.birc.message.Response
import java.util.*

/**
 *
 */
abstract class BasicMessageManager
{

    private val subscribers = ArrayList<Listener.Subscriber>()
    fun register(any: Listener.Subscriber)
    {
        if (!subscribers.contains(any))
            subscribers.add(any)
        else
            System.err.println("$any is already subscribed")
    }

    fun unregister(any: Listener.Subscriber)
    {
        subscribers.remove(any)
    }

    fun post(response: Response)
    {
        subscribers.forEach { it.onPost(response) }
    }
}
