package lt.dragas.birc.basic.route

import lt.dragas.birc.message.Request

open class RegexRoute(val regexString: String, override var controller: Controller) : Route(regexString)
{
    override fun canTrigger(request: Request): Boolean
    {
        if (isEnabled && type.and(request.type) == type && Regex(regexString).matches(request.message))
        {
            controller.onTrigger(request)
            return true
        }
        return false
    }
}