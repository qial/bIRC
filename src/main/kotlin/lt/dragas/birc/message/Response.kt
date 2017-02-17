package lt.dragas.birc.message

/**
 * Response to server.
 *
 * Returned by local implementation of Route. Contains two constructors which easily let you build your response with either
 * direct target and message or straight up raw response. By default, [Output] calls [toString] to convert this response
 * to string its representation. Does not need to have CLRF terminator appended as [Output] already handles that.
 */
class Response : Message
{
    constructor(target: String, message: String)
    {
        this.target = target
        this.message = message
    }

    constructor(rawResponse: String)
    {
        this.rawResponse = rawResponse
    }

    override fun toString(): String
    {
        if (rawResponse == "")
            return "privmsg $target $message"
        return rawResponse
    }
}