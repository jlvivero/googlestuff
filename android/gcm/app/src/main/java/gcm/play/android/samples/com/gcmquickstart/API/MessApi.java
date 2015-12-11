package gcm.play.android.samples.com.gcmquickstart.API;
import java.util.List;

import gcm.play.android.samples.com.gcmquickstart.Models.GlobalMessage;
import gcm.play.android.samples.com.gcmquickstart.Models.PostCallback;
import gcm.play.android.samples.com.gcmquickstart.Models.Registration;
import gcm.play.android.samples.com.gcmquickstart.Models.Users;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by jl on 12/8/15.
 */
public interface MessApi
{
    @GET("/users") //here is the other url part
    void getFeed(Callback<Users> response);
    //@Path("users")

    @GET("/notify/{user}")
    void notifyResponse(@Path("user") String user);

    @POST("/sendgcm")
    void postFeed(@Body GlobalMessage message, Callback<PostCallback> response);

    @POST("/sendgcm/{user}")
    void postToUser(@Path("user") String user, @Body GlobalMessage message, Callback<PostCallback> response);

    @POST("/register")
    void register(@Body Registration info, Callback<PostCallback> response);
}
