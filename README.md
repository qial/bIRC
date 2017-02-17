# bIRC #

Somewhat elegant Java/Kotlin IRC bot framework.

### Adding functionality ###

bIRC was written with modular functionality in mind, thus adding responses 
is just as easy as creating your own class. Literally.

First you have to create a class which extends `Controller`.

```
class RespondToHello extends Controller
{
    @Override
    void onTrigger(Request request)
    {
        String formattedResponse = "";
        //do stuff
        Output.getDefault().writeResponse(new Response(request.getTarget(), formattedResponse);
    }
}
```

And then you register your route in `Routes.kt` file by adding it into 
your or a predefined `RouteGroup`.

```
fun initializeRoutes(): Array<Route.RouteGroup> {
    val array = ArrayList<Route.RouteGroup>()
    val simpleRouteGroup = Route.RouteGroup("do ")
    simpleRouteGroup.add(Route.Builder().setCommand("hello").setController(RespondToHello()).setType(Route.CHANNEL).build())
    simpleRouteGroup.finalize() // be sure to call this
    return array.toTypedArray()
}
```
That's it!

Since Routes are built using builder pattern, you might want to explore more options than the 3 provided. For example `setHasArguments` and `setIgnoreCaps`.
