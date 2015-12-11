/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



package gcm.play.android.samples.com.gcmquickstart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;

import gcm.play.android.samples.com.gcmquickstart.API.MessApi;
import gcm.play.android.samples.com.gcmquickstart.Models.GlobalMessage;
import gcm.play.android.samples.com.gcmquickstart.Models.PostCallback;
import gcm.play.android.samples.com.gcmquickstart.Models.Registration;
import gcm.play.android.samples.com.gcmquickstart.Models.Users;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";

    // my variables HERE
    private Users userList = new Users();
    private TextView tv;
    Button click;
    String API = "http://test-scala-server.herokuapp.com";
    MessApi messenger;
    EditText edit;
    //thanks javascript for teaching me that that = this xD
    Context that = this;


    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private ProgressBar mRegistrationProgressBar;
    private TextView mInformationTextView;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        click = (Button) findViewById(R.id.Login);
        edit = (EditText)findViewById(R.id.userEdit);

        mRegistrationProgressBar = (ProgressBar) findViewById(R.id.registrationProgressBar);
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mRegistrationProgressBar.setVisibility(ProgressBar.GONE);
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                if (sentToken) {
                    mInformationTextView.setText(getString(R.string.gcm_send_message));
                } else {
                    mInformationTextView.setText(getString(R.string.token_error_message));
                }
            }
        };
        mInformationTextView = (TextView) findViewById(R.id.informationTextView);

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();


        //creates an adapter for retrofit with the base url
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(API).build();

        //creates server for adapter with our get class
        messenger = restAdapter.create(MessApi.class);

        tv = (TextView) findViewById(R.id.tv);
        //button here HANDLING THE LOGIN
        click.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!edit.getText().toString().equals(""))
                {
                    final String MY_PREFS_NAME = "";
                    SharedPreferences settings = getSharedPreferences(MY_PREFS_NAME, 0);
                    boolean val = settings.getBoolean("register", false);
                    if(!val)
                    {
                        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                        final String id = prefs.getString("token", "token");
                        messenger.register(new Registration(edit.getText().toString(), id), new Callback<PostCallback>() {
                            @Override
                            public void success(PostCallback postCallback, Response response) {
                                tv.setText("you've successfully registered");
                                SharedPreferences settings = getSharedPreferences(MY_PREFS_NAME, 0);
                                SharedPreferences.Editor editor = settings.edit();
                                editor.putBoolean("register", true);
                                editor.putString("user",edit.getText().toString());

                                // Commit the edits!
                                editor.commit();
                                //create an intent to change activity
                                Intent intent = new Intent(that, Dash.class);
                                startActivity(intent);
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                tv.setText("registration failed");
                                SharedPreferences settings = getSharedPreferences(MY_PREFS_NAME, 0);
                                SharedPreferences.Editor editor = settings.edit();
                                editor.putBoolean("register", false);
                                // Commit the edits!
                                editor.commit();
                            }
                        });
                    }
                    else
                    {
                        tv.setText("you're already registered");
                        Intent intent = new Intent(that, Dash.class);
                        startActivity(intent);
                    }
                }
            }
        });


        //OBTAINING THE LIST OF USERS STUFF


        //NOW, we need to call for response
        //retrofit using gson for JSON-POJO STUFF

        messenger.getFeed(new Callback<Users>() {
            @Override
            public void success(Users users, Response response) {
                //userList.copyUsers(users);
                //tv.setText("status: " + userList.getStatus() + "data" + userList.getData());
            }

            @Override
            public void failure(RetrofitError error) {
                //tv.setText(error.getMessage());
            }
        });

        //aqui voy a poner los metodos de todos los request, pero obviamente se tienen que arreglar
        //y poner donde van realmente, por ejemplo este post podria ir en un boton

        /*
            Anuncio Global con un mensaje
            Si no estas en el mismo lugar que al principo tienes que declarar un adapter
            Aqui ya esta declarado arriba para el get de los usuarios
            Si no pondrias esto:
            RestAdapter restAdapter2 = new RestAdapter.Builder().setEndpoint(API).build();

         */

        //aqui en vez de "prueba", "JL" pondrias las variables de el modelo Global Message
        GlobalMessage jsonResponse = new GlobalMessage("prueba", "JL");



        messenger.postFeed(jsonResponse, new Callback<PostCallback>() {
            @Override
            public void success(PostCallback s, Response response) {
                //aqui pondrias donde quieres que aparesca el mensaje de la respuesta
               // tv.setText("status: " + s.getStatus() + ", message: " + s.getMessage());
            }

            @Override
            public void failure(RetrofitError error) {
                //tv.setText(error.getMessage());
            }
        });

        /*
            Post directo a una persona
            Igual si no esta en el mismo lugar tienes que llamar al rest adapter
            utilizare la misma variable jsonResponse, pero si esta en otro lugar tienes que declararla
            como arriba, igual con el MessAPI post
         */
        //en vez de itzel pondrias una variable con alguno de los usuarios aqui le darias click a algun usuario
        messenger.postToUser("JL", jsonResponse, new Callback<PostCallback>() {
            @Override
            public void success(PostCallback postCallback, Response response) {
                //aqui pondrias lo que quieres que haga la aplicacion despues de mandar el mensaje
                //en vez de poner tv.setText()
                tv.setText(postCallback.getStatus());
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

        /*
            Notify a todos los usuarios, lo mismo aplica con las cosas que no estoy repitiendo sacalas
            de los post y get original
            este solo manda una notificacion asi que solo ocupa el url del usuario
         */
        //messenger.notifyResponse("Itzel");

    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://gcm.play.android.samples.com.gcmquickstart/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://gcm.play.android.samples.com.gcmquickstart/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
