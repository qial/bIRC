package lt.dragas.birc.basic.route

import lt.dragas.birc.basic.exception.DuplicateItemException
import lt.dragas.birc.basic.exception.FinalRouteGroupException
import lt.dragas.birc.basic.exception.NotFinalRouteGroupException
import lt.dragas.birc.message.Request
import lt.dragas.birc.routes.server.Pong
import java.util.*

/**
 * Base class from which all other route classes derive from.
 *
 * This particular class contains building blocks for extending routes, thus if necessary they need to be overriden.
 *
 * The check for type of route is done via bitwise and operation, thus allowing you to merge route types into groups as well.
 *
 * Because this class implements a runnable interface, it permits asynchronous concurrent calls.
 * @property ignoreCaps Denotes if this route should respond regardless of [command] capitalization. Default - true
 *
 * @property type Denotes type for this route. By default might be CHANNEL, PING, PRIVATE, or NONE.
 * Does not implement default value, unless you're extending one of the recommended classes.
 *
 * @property isEnabled Denotes whether or not this particular route is enabled. Useful for disabling routes at runtime.
 * Default - true.
 *
 * @property hasArguments Denotes whether or not the [command] is separated with space from message in response from server.
 * Default - true.
 *
 * @property command Denotes when this route should be triggered. Does not contain default value, but may be an empty string.
 */
open class Route(val command: String)
{
    protected var type: Int = NONE
    protected var ignoreCaps: Boolean = true
    protected var isEnabled: Boolean = true
    open protected var hasArguments: Boolean = true
    open protected lateinit var controller: Controller
    protected val commandWord: String
        get()
        {
            val sb = StringBuilder(command)
            if (hasArguments)
                sb.append(" ")
            return sb.toString()
        }

    /**
     * Checks whether or not this particular route can be triggered by checking 3 parameters: [isEnabled], [command] and [type].
     * Note: type is checked with bitwise operation "and", thus allowing you to merge few response types into one.
     */
    open fun canTrigger(request: Request): Boolean
    {
        if (isEnabled && request.message.startsWith(commandWord, ignoreCaps) && request.type.and(type) == type)
        {
            controller.onTrigger(request)
            return true
        }
        return false
    }

    /**
     * A [Route] variant that is used to group routes with same initial command into one. May contain other route groups,
     * as this class also extends [Route].
     *
     * Contents of RouteGroup are not meant to be modified at runtime, thus its content is converted to an array.
     * Also RouteGroups may not contain [Route] with matching [triggerWord]
     */
    open class RouteGroup(val prefix: String, vararg routes: Route)
    {
        protected var hasArguments: Boolean = true
        protected lateinit var routeArray: Array<Route>
        protected var routeMap = HashMap<String, Route>()
        protected var isFinal = false
        protected var type: Int = NONE
        protected var ignoreCaps: Boolean = true
        protected var isEnabled: Boolean = true

        init
        {
            routes.forEach {
                add(it)
            }
            if (routes.isNotEmpty())
                finalize()
        }

        fun add(route: Route)
        {
            if (isFinal)
                throw FinalRouteGroupException()
            if (routeMap.containsValue(route))
                throw DuplicateItemException()
            routeMap[route.commandWord] = route
        }

        /**
         * Notes that this [RouteGroup] is final, thus permits usage of it.
         */
        fun finalize()
        {
            routeArray = routeMap.values.toTypedArray()
            isFinal = true
        }

        /**
         * Tries triggering this particular route group. Does not call [onTrigger], because we are not sure that
         * this particular [RouteGroup] contains a route that might return a response.
         *
         *
         * @param request a formatted server response with type, target, username and message
         * @return true if this particular [RouteGroup] contains a route that has been triggered, otherwise null
         * @throws NotFinalRouteGroupException if this particular group hasn't been finalized
         */

        fun canTrigger(request: Request): Boolean
        {
            if (!isFinal)
                throw NotFinalRouteGroupException()
            if (isEnabled && request.message.startsWith(prefix, ignoreCaps))
            {
                request.message = request.message.replaceFirst(prefix, "", true)
                routeArray.forEach {
                    val triggered = it.canTrigger(request)
                    if (triggered)
                        return true
                }
            }
            return false
        }

        /**
         * In [RouteGroup] this method shouldn't be called as the group is not guaranteed to have a
         * route that corresponds to particular request.
         */
        fun onTrigger(request: Request)
        {
            throw NoSuchMethodException()
        }
    }

    class Builder
    {
        private var controller: Controller? = null
        private var command: String? = null
        private var ignoreCaps: Boolean = false
        private var hasArguments: Boolean = false
        private var type: Int = NONE
        fun build(): Route
        {
            val controller = this.controller ?: throw Exception("Controller is null. Are you sure such route should exist?")
            val command = this.command ?: throw Exception("Command is null. Are you sure such route should exist?")
            val route: Route = Route(command)
            route.controller = controller
            route.ignoreCaps = this.ignoreCaps
            route.hasArguments = this.hasArguments
            route.type = this.type
            return route
        }

        fun setController(controller: Controller): Builder
        {
            this.controller = controller
            return this
        }

        fun setCommand(command: String): Builder
        {
            this.command = command
            return this
        }

        fun setIgnoreCaps(shouldIgnore: Boolean): Builder
        {
            ignoreCaps = shouldIgnore
            return this
        }

        fun setHasArguments(hasArguments: Boolean): Builder
        {
            this.hasArguments = hasArguments
            return this
        }

        fun setType(type: Int): Builder
        {
            this.type = type
            return this
        }
    }
    companion object
    {
        /**
         * Default mode. Means that route does not work solely with private messages or channel messages.
         */
        @JvmField
        val NONE: Int = 1
        /**
         * Marks route as ping route. Shouldn't be used outside [Pong]
         */
        @JvmField
        val PING: Int = 64
        /**
         * Marks route as private message route.
         */
        @JvmField
        val PRIVATE: Int = 3
        /**
         * Marks route as channel route.
         */
        @JvmField
        val CHANNEL: Int = 5
    }
}