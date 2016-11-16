package lt.dragas.birc.routes.group

import lt.dragas.birc.basic.Route

/**
 * Created by cpartner on 2016-10-18.
 */
class SuperRouteGroup(vararg routes: Route) : Route.RouteGroup("sudo", *routes)
{

}