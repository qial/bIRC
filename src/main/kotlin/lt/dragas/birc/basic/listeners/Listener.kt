package lt.dragas.birc.basic.listeners


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
}