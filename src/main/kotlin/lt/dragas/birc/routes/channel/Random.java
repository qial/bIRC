package lt.dragas.birc.routes.channel;

import lt.dragas.birc.basic.Route;
import lt.dragas.birc.helper.RegexHelper;
import lt.dragas.birc.message.Request;
import lt.dragas.birc.message.Response;
import org.jetbrains.annotations.NotNull;


public class Random extends Route
{
    private java.util.Random rng = new java.util.Random();

    public Random()
    {
        super("");
    }

    @Override

    public boolean canTrigger(@NotNull Request request)
    {
        if (isEnabled() &&
                request.getMessage().matches("\\d+d\\d+((\\+|-)\\d+)*") &&
                (getType() & CHANNEL) == getType())
        {
            doTrigger(request);
            return true;
        }
        return false;
    }

    @NotNull
    @Override
    public Response onTrigger(@NotNull Request request)
    {
        String[] args = request.getMessage().split("d");
        int count = Integer.parseInt(args[0]);
        int limit = Integer.parseInt(RegexHelper.obtainArgument(args[1], "\\d+"));
        int sum = 0;
        int mod = 0;
        StringBuilder sb = new StringBuilder();
        sb.append(request.getUsername());
        sb.append(": ");
        for (String item : RegexHelper.obtainArguments(args[1], "(\\+|-)\\d+"))
        {
            mod += Integer.parseInt(item);
        }
        for (int i = 0; i < count; i++)
        {
            if (i >= 1)
            {
                sb.append(", ");
            }
            int generated = rng.nextInt(limit) + 1 + mod;
            sum += generated;
            sb.append(generated);
        }
        if (count > 1)
        {
            sb.append(". Sum: ");
            sb.append(sum);
        }
        if (mod != 0)
        {
            sb.append(". Mod: ");
            sb.append(mod);
        }
        return new Response(request.getTarget(), sb.toString());
    }
}
