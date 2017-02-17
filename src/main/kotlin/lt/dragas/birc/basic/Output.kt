package lt.dragas.birc.basic

import lt.dragas.birc.message.Response
import java.io.DataOutputStream
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.io.PrintStream

/**
 * Contains only the most basic methods needed for [OutputStream] to function.
 * @param outputStream OutputStream : server socket's output stream.
 */
abstract class Output(outputStream: OutputStream)
{
    protected val sout: OutputStreamWriter = OutputStreamWriter(DataOutputStream(outputStream))
    protected val cout: PrintStream = System.out

    /**
     * Writes response to server. Due to the nature of how IRCs work, the response here is appended with CRLF new line
     * terminator, so that the server would be able to know when input has ended. By default also calls [writeConsole]
     * so that responses could be debugged in timely fashion.
     *
     * @param response String : preformatted message that is sent to server.
     * @param sendToServer Boolean : notes whether or not the response should be sent to server.
     */
    @JvmOverloads
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

    @JvmOverloads
    fun writeResponse(response: Response, sendToServer: Boolean = true)
    {
        writeResponse(response.toString(), sendToServer)
    }
    companion object
    {
        lateinit var default: Output
    }
}