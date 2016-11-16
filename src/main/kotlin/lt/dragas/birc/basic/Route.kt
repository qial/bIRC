package lt.dragas.birc.basic

import lt.dragas.birc.basic.exception.DuplicateItemException
import lt.dragas.birc.basic.exception.FinalRouteGroupException
import lt.dragas.birc.basic.exception.NotFinalRouteGroupException
import lt.dragas.birc.main.MessageManager
import lt.dragas.birc.message.Request
import lt.dragas.birc.message.Response
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
 * @property ignoreCaps Denotes if this route should respond regardless of [triggerWord] capitalization. Default - true
 *
 * @property type Denotes type for this route. By default might be CHANNEL, PING, PRIVATE, or NONE.
 * Does not implement default value, unless you're extending one of the recommended classes.
 *
 * @property isEnabled Denotes whether or not this particular route is enabled. Useful for disabling routes at runtime.
 * Default - true.
 *
 * @property hasArguments Denotes whether or not the [triggerWord] is separated with space from message in response from server.
 * Default - true.
 *
 * @property triggerWord Denotes when this route should be triggered. Does not contain default value, but may be an empty string.
 */
abstract class Route(val triggerWord: String) : Listener.Route, Runnable
{
    open val type: Int = NONE
    open var ignoreCaps: Boolean = true
    open var isEnabled: Boolean = true
    open val hasArguments: Boolean = true
    @Volatile open lateinit var request: Request
    val commandWord: String
        get()
        {
            val sb = StringBuilder(triggerWord)
            if (hasArguments)
                sb.append(" ")
            return sb.toString()
        }

    /**
     * Checks whether or not this particular route can be triggered by checking 3 parameters: [isEnabled], [triggerWord] and [type].
     * Note: type is checked with bitwise operation "and", thus allowing you to merge few response types into one.
     */
    override fun canTrigger(request: Request): Boolean
    {
        if (isEnabled && request.message.startsWith(commandWord, ignoreCaps) && request.type.and(type) == type)
        {
            doTrigger(request)
            return true
        }
        return false
    }

    open fun doTrigger(request: Request)
    {
        request.message = request.message.replaceFirst(commandWord, "", true)
        this.request = request
        Thread(this, commandWord).start()
    }

    override fun run()
    {
        MessageManager.post(onTrigger(request))
    }

    /**
     * A [Route] variant that is used to group routes with same initial command into one. May contain other route groups,
     * as this class also extends [Route].
     *
     * Contents of RouteGroup are not meant to be modified at runtime, thus its content is converted to an array.
     * Also RouteGroups may not contain [Route] with matching [triggerWord]
     */
    abstract class RouteGroup(commandWord: String, vararg routes: Route) : Route(commandWord)
    {
        override var hasArguments: Boolean = true
        private lateinit var routeArray: Array<Route>
        private var routeMap = HashMap<String, Route>()
        private var isFinal = false

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

        override fun canTrigger(request: Request): Boolean
        {
            if (!isFinal)
                throw NotFinalRouteGroupException()
            if (isEnabled && request.message.startsWith(commandWord, ignoreCaps))
            {
                request.message = request.message.replaceFirst(commandWord, "", true)
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
        override fun onTrigger(request: Request): Response
        {
            throw NoSuchMethodException()
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