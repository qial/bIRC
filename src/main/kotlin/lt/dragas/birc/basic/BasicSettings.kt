package lt.dragas.birc.basic

import com.google.gson.Gson
import java.io.FileReader

open class BasicSettings
{
    open var server: String = ""
    open var nicknames: Array<String> = emptyArray()
    open var username: String = ""
    open var realName: String = ""
    open var mode: String = ""
    open var channels: Array<String> = emptyArray()
    open var serverPort: Int = 6667
    val unused = "shit"
    fun initializeSettings(): BasicSettings
    {
        val gson = Gson()
        val fin = FileReader("settings.json")
        return gson.fromJson(fin, BasicSettings::class.java)
    }
}