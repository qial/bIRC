package lt.dragas.birc.basic

import lt.dragas.birc.message.Response
import java.io.DataOutputStream
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.io.PrintStream
import java.util.*

/**
 * Contains only the most basic methods needed for [OutputStream] to function. Since it implements
 * [Runnable] interface, it does not throttle the main thread while waiting for client to generate responses.
 * @param outputStream OutputStream : server socket's output stream.
 */
abstract class BasicOutput(outputStream: OutputStream) : Runnable
{
    private val thread = Thread(this, "BasicOutput")
    protected val sout: OutputStreamWriter = OutputStreamWriter(DataOutputStream(outputStream))
    protected val cout: PrintStream = System.out
    protected val responses: LinkedList<Response> = LinkedList()
    protected var isRunning: Boolean = false
    override fun run()
    {
        isRunning = true
        while (isRunning)
        {
            val response = responses.pollFirst()
            if (response == null)
            {
                Thread.sleep(1000)
                continue
            }
            writeResponse(response.toString())
            responses.remove(response)
        }
    }

    /**
     * Writes response to server. Due to the nature of how IRCs work, the response here is appended with CRLF new line
     * terminator, so that the server would be able to know when input has ended. By default also calls [writeConsole]
     * so that responses could be debugged in timely fashion.
     *
     * @param response String : preformatted message that is sent to server.
     * @param sendToServer Boolean : notes whether or not the response should be sent to server.
     */
    fun writeResponse(response: String, sendToServer: Boolean = true)
    {
        if (sendToServer)
        {
            sout.append("$response\r\n")
            sout.flush()
        }
        writeConsole(response)
    }

    /**
     * Outputs written response to console. Mainly used for debugging.
     *
     * @param message String: line, that is outputted to console.
     */
    fun writeConsole(message: String)
    {
        cout.println(message)
    }

    /**
     * Appends response to response queue. The order here used is FIFO, just like in [BasicInput]
     */
    fun appendResponse(response: Response)
    {
        responses.offerLast(response)
    }

    /**
     * Starts this particular thread.
     */
    fun start()
    {
        thread.start()
    }
}