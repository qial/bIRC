package lt.dragas.birc

import lt.dragas.birc.main.ClientImpl
import lt.dragas.birc.main.Settings


/**
 * Created by cpartner on 2016-10-04.
 */
fun main(args: Array<String>)
{
    val routes = Routes.initializeRoutes()
    val settings = Settings.initializeSettings()
    val client = ClientImpl(routes)
    client.setup(settings).connect()
}