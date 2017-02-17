package lt.dragas.birc


import lt.dragas.birc.basic.route.RegexRoute
import lt.dragas.birc.basic.route.Route
import lt.dragas.birc.routes.channel.Move
import lt.dragas.birc.routes.channel.Quit
import lt.dragas.birc.routes.channel.Random
import java.util.*

/**
 * Initializes [Route.RouteGroup] array, which is used by this particular client. Note that returned type is an array
 * as working group is not meant to be modified at runtime. To change which [Route] work at runtime, you should look into
 * [Route.isEnabled] handling.
 */
fun initializeRoutes(): Array<Route.RouteGroup>
{
    val array = ArrayList<Route.RouteGroup>()
    val simpleRouteGroup = Route.RouteGroup("do")
    simpleRouteGroup.add(Route.Builder()
            .setCommand("join")
            .setHasArguments(true)
            .setController(Move())
            .setIgnoreCaps(true)
            .setType(Route.CHANNEL.and(Route.PRIVATE))
            .build())
    simpleRouteGroup.add(Route.Builder()
            .setCommand("leave")
            .setType(Route.CHANNEL.and(Route.PRIVATE))
            .setIgnoreCaps(true)
            .setController(Quit())
            .setHasArguments(true)
            .build())
    simpleRouteGroup.add(RegexRoute("\\d+d\\d+((\\+|-)\\d+)*", Random()))
    return array.toTypedArray()
}