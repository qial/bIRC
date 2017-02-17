package lt.dragas.birc.main

import lt.dragas.birc.basic.io.Input
import java.io.InputStream

/**
 * Example implementation of this framework's [InputImpl] class.
 *
 * Default methods are already defined in parent class, thus it is unnecessary to override it, but it is still recommended
 * that you extend it since you can change the default behavior without heavily modifying framework itself.
 */
class InputImpl(inputStream: InputStream) : Input(inputStream)
{

}