package lt.dragas.birc.message

/**
 * A super class for IRC server message syntax.
 */
open class Message
{
    var rawResponse: String = ""
    var target: String = ""
    var username: String = ""
    var message: String = ""
    override fun toString(): String
    {
        return rawResponse
    }
}