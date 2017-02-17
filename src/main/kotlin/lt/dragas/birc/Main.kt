package lt.dragas.birc

import lt.dragas.birc.main.ClientImpl
import lt.dragas.birc.main.SettingsImpl


fun main(args: Array<String>)
{
    val routes = initializeRoutes()
    val settings = SettingsImpl.initializeSettings()
    val client = ClientImpl(routes)
    client.setup(settings).connect()
}