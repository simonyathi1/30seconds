package model;

import android.app.Application;
import android.content.Context;

/**
 * Created by temp on 04/10/2017.
 */

public class MyApp extends Application {

    public static MyApp instance;

    public MyApp() {
        instance = this;
    }

    public static Context getContext() {
        return instance;
    }

}
