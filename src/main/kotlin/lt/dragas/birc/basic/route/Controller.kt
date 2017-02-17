package lt.dragas.birc.basic.route

import lt.dragas.birc.message.Request


abstract class Controller
{
    abstract fun onTrigger(request: Request)
}