package lt.dragas.birc.basic

import lt.dragas.birc.message.Request
import lt.dragas.birc.message.Response


interface Listener
{
    interface Client
    {

        /**
         * A listener that is triggered when socket successfully connects to server.
         *
         * Due to how sockets work, IO streams are only initialized when connection is successful, thus Client's Input/Output
         * implementations have to be initialized here, meanwhile if client fails to connect to server an exception is thrown.
         */
        fun onConnect()

        /**
         * A listener that is triggered when client receives a ping request.
         *
         * Anything here is triggered every time after client successfully sends a pong response.
         */

        fun onPong()
    }

    interface Route
    {
        /**
         *  A callback for route when the it has been successfully triggered.
         *
         *  @return [Response] - formatted response by this particular route
         */
        fun onTrigger(request: Request): Response

        /**
         * Returns whether or not a thread with this particular Route has been started.
         *
         * @return true, if a thread had been started, otherwise false.
         */
        fun canTrigger(request: Request): Boolean
    }
}