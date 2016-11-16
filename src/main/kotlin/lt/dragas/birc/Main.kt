package lt.dragas.birc

import lt.dragas.birc.main.Client
import lt.dragas.birc.main.Settings


/**
 * Created by cpartner on 2016-10-04.
 */
fun main(args: Array<String>)
{
    val routes = Routes.initializeRoutes()
    val settings = Settings.initializeSettings()
    val client = Client(routes)
    client.setup(settings).connect()
}