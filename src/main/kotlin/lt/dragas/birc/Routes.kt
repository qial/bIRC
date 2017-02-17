package lt.dragas.birc

import lt.dragas.birc.basic.Route
import java.util.*

/**
 * Initializes [Route.RouteGroup] array, which is used by this particular client. Note that returned type is an array
 * as working group is not meant to be modified at runtime. To change which [Route] work at runtime, you should look into
 * [Route.isEnabled] handling.
 */
fun initializeRoutes(): Array<Route.RouteGroup>
{
    val array = ArrayList<Route.RouteGroup>()

    return array.toTypedArray()
}