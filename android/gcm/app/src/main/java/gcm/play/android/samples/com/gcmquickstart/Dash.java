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

public class Dash extends AppCompatActivity {

    ListView lista;
    Context that = this;

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
        list();
    }

    void list() {
        // Defined Array values to show in ListView

        String[] values = new String[]{"Android List View",

                "Adapter implementation",

                "Simple List View In Android",

                "Create List View Android",

                "Android Example",

                "List View Source Code",

                "List View Array Adapter",

                "Android Example List View"

        };


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
                startActivity(intent);
            }


        });



    }
}




