package gcm.play.android.samples.com.gcmquickstart;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import gcm.play.android.samples.com.gcmquickstart.API.MessApi;
import gcm.play.android.samples.com.gcmquickstart.Models.GlobalMessage;
import gcm.play.android.samples.com.gcmquickstart.Models.PostCallback;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class chat extends AppCompatActivity {
    EditText mensaje;

    String API = "http://test-scala-server.herokuapp.com";
    MessApi messenger;
    Bundle extras;
    String user;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        extras = getIntent().getExtras();
        if (extras != null) {
            user = extras.getString("Receive");
        }
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final String MY_PREFS_NAME = "";
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        final String sender = prefs.getString("user", "user");

        mensaje=(EditText)findViewById(R.id.editText);
        tv = (TextView)findViewById(R.id.editText1);
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(API).build();
        messenger = restAdapter.create(MessApi.class);
        tv.setTextColor(Color.BLACK);
        getstuff();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                messenger.postToUser(user, new GlobalMessage(mensaje.getText().toString(), sender), new Callback<PostCallback>() {
                    @Override
                    public void success(PostCallback postCallback, Response response) {
                        String whatever,contenido="";
                        if(tv.getText().toString()!="")
                            contenido=tv.getText().toString()+"\n";
                        whatever = "you: "+mensaje.getText().toString();
                        mensaje.setText("");
                        contenido+=whatever;
                        tv.setTextColor(Color.BLACK);
                        tv.setText(contenido);
                        //Snackbar.make(view, "message sent", Snackbar.LENGTH_LONG)
                          //     .setAction("Action", null).show();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Snackbar.make(view, "error message", Snackbar.LENGTH_LONG)
                               .setAction("Action", null).show();
                    }
                });
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                 //       .setAction("Action", null).show();
            }
        });


    }

    void getstuff()
    {
        String MY_PREFS_NAME = "";
        SharedPreferences settings = getSharedPreferences(MY_PREFS_NAME, 0);
        String val = settings.getString("sender", " ");
        if(val.equals(user))
        {
            String val2 = settings.getString("message", " ");
            tv.setText(val2);
            tv.setTextColor(Color.BLACK);
        }
    }

}
