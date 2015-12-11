package gcm.play.android.samples.com.gcmquickstart.Models;

/**
 * Created by jl on 12/11/15.
 */
public class Registration
{
    String user;
    String id;

    public Registration(String user, String id)
    {
        this.user = user;
        this.id = id;
    }

    public String getId()
    {
        return id;
    }

    public String getUser()
    {
        return user;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public void setId(String id)
    {
        this.id = id;
    }

}
