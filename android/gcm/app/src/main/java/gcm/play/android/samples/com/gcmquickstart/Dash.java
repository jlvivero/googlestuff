package gcm.play.android.samples.com.gcmquickstart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import gcm.play.android.samples.com.gcmquickstart.API.MessApi;
import gcm.play.android.samples.com.gcmquickstart.Models.Users;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Dash extends AppCompatActivity {

    ListView lista;
    Context that = this;
    String API = "http://test-scala-server.herokuapp.com";
    MessApi messenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lista = (ListView) findViewById(R.id.listView);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(API).build();
        messenger = restAdapter.create(MessApi.class);
        messenger.getFeed(new Callback<Users>() {
            @Override
            public void success(Users users, Response response) {
                list(users);
            }

            @Override
            public void failure(RetrofitError error) {
                list(null);
            }
        });

    }

    void list(Users user) {
        // Defined Array values to show in ListView
        String[] values;
        if(user != null)
        {
            values = new String[user.getData().size()];
            for(int i = 0; i < user.getData().size(); i++)
            {
                values[i] = user.getData().get(i);
            }

        }
        else
        {
            values = new String[]{
                    "Android List View",

                    "Adapter implementation",

                    "Simple List View In Android",

                    "Create List View Android",

                    "Android Example",

                    "List View Source Code",

                    "List View Array Adapter",

                    "Android Example List View"

            };
        }


        // Define a new Adapter

        // First parameter - Context

        // Second parameter - Layout for the row

        // Third parameter - ID of the TextView to which the data is written

        // Forth - the Array of data


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,

                android.R.layout.simple_list_item_1, android.R.id.text1, values);


        // Assign adapter to ListView

        lista.setAdapter(adapter);


        // ListView Item Click Listener

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override

            public void onItemClick(AdapterView<?> parent, View view,

                                    int position, long id) {


                // ListView Clicked item index

                int itemPosition = position;


                // ListView Clicked item value

                String itemValue = (String) lista.getItemAtPosition(position);


                // Show Alert

                Toast.makeText(getApplicationContext(),

                        "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)

                        .show();

                Intent intent = new Intent(that, chat.class);
                intent.putExtra("Receive", itemValue);
                startActivity(intent);
            }
        });

    }
}




