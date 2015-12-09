package gcm.play.android.samples.com.gcmquickstart.Models;

/**
 * Created by jl on 12/9/15.
 */
public class GlobalMessage
{
    String message;
    String sender;

    public GlobalMessage(String message, String sender)
    {
        this.message = message;
        this.sender = sender;
    }

    public String getMessage()
    {
        return message;
    }

    public String getSender()
    {
        return sender;
    }
}
