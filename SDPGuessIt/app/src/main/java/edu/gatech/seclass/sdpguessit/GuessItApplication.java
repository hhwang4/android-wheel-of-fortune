package edu.gatech.seclass.sdpguessit;

import android.app.Application;
import android.content.Context;

import edu.gatech.seclass.sdpguessit.di.AppComponent;
import edu.gatech.seclass.sdpguessit.di.AppModule;
import edu.gatech.seclass.sdpguessit.di.DaggerAppComponent;
import timber.log.Timber;


public class GuessItApplication extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static AppComponent component(Context context) {
        return ((GuessItApplication) context.getApplicationContext()).appComponent;
    }
}
