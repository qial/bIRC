package lt.dragas.birc.helper

import java.util.*
import java.util.regex.Pattern

/**
 * Created by mgrid on 2016-11-04.
 */
object RegexHelper
{
    @JvmStatic
    fun obtainArguments(source: String, pattern: String): Array<String>
    {
        val cPattern = Pattern.compile(pattern)
        val matcher = cPattern.matcher(source)
        val arrayList = ArrayList<String>()
        while (matcher.find())
        {
            arrayList.add(matcher.group())
        }
        return arrayList.toTypedArray()
    }


    /**
     * Returns a particular argument from source. Equivalent to calling [obtainArguments] with [Array.getOrNull]
     */
    @JvmOverloads
    @JvmStatic
    fun obtainArgument(source: String, pattern: String, which: Int = 1): String?
    {
        return obtainArguments(source, pattern).getOrNull(which - 1)
    }
}