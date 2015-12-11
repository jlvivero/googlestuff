package gcm.play.android.samples.com.gcmquickstart;

import android.content.Context;
import android.content.SharedPreferences;

public class persistencia {
    private static persistencia yourPreference;
    private static SharedPreferences sharedPreferences;

    public static persistencia getInstance(int context) {
        if (yourPreference == null) {
            yourPreference = new persistencia(context);
        }
        return yourPreference;
    }

    private persistencia(Context context) {
        sharedPreferences = context.getSharedPreferences("YourCustomNamedPreference",Context.MODE_PRIVATE);
    }

    public static void saveData(String key, String value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor .putString(key, value);
        prefsEditor.commit();
    }

    public static String getData(String key) {
        if (sharedPreferences!= null) {
            return sharedPreferences.getString(key, "");
        }
        return "";
    }
}