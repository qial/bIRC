package lt.dragas.birc.message

import lt.dragas.birc.basic.Route

/**
 * Created by mgrid on 2016-11-04.
 */
class Request : Message
{
    var type: Int = Route.NONE

    constructor(rawResponse: String)
    {
        System.out.println(rawResponse)
        this.rawResponse = rawResponse
        message = rawResponse.substring(rawResponse.indexOf(" :") + 2)
        if (rawResponse.startsWith("ping", true))
        {
            type = Route.PING
            return
        }

        try
        {
            val array = rawResponse.take(rawResponse.indexOf(" :")).split(" ")
            username = array[0].substring(1, array[0].indexOf("!"))
            target = array[2]
            if (target.startsWith("#"))
                type = Route.CHANNEL
            else
            {
                type = Route.PRIVATE
                target = username
            }

        }
        catch(err: Exception)
        {
            err.printStackTrace()
        }
    }
}