package lt.dragas.birc

import lt.dragas.birc.basic.Route
import lt.dragas.birc.routes.channel.*
import lt.dragas.birc.routes.channel.Random
import lt.dragas.birc.routes.group.CommonRouteGroup
import lt.dragas.birc.routes.group.ServerRouteGroup
import lt.dragas.birc.routes.group.SuperRouteGroup
import lt.dragas.birc.routes.server.Pong
import java.util.*

object Routes
{
    /**
     * Initializes [Route.RouteGroup] array, which is used by this particular client. Note that returned type is an array
     * as working group is not meant to be modified at runtime. To change which [Route] work at runtime, you should look into
     * [Route.isEnabled] handling.
     */
    fun initializeRoutes(): Array<Route.RouteGroup>
    {
        val array = ArrayList<Route.RouteGroup>()
        array.add(ServerRouteGroup(Pong()))
        array.add(SuperRouteGroup(Quit()))
        array.add(CommonRouteGroup(Move(), Random(), Pose(), Countdown()))
        return array.toTypedArray()
    }
}