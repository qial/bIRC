package lt.dragas.birc.basic.route

import lt.dragas.birc.message.Request

/**
 * Created by mgrid on 2017-02-17.
 */
open class RegexRoute(regexString: String, override var controller: Controller) : Route(regexString)
{
    override fun canTrigger(request: Request): Boolean
    {
        if (isEnabled && type.and(request.type) == type && Regex(commandWord).matches(request.toString()))
        {
            controller.onTrigger(request)
            return true
        }
        return false
    }
}