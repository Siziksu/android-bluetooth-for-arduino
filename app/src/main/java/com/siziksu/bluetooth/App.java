package com.siziksu.bluetooth;

import android.app.Application;

import com.siziksu.bluetooth.dagger.component.ApplicationComponent;
import com.siziksu.bluetooth.dagger.component.DaggerApplicationComponent;
import com.siziksu.bluetooth.dagger.module.ApplicationModule;
import com.siziksu.bluetooth.dagger.module.DataModule;
import com.siziksu.bluetooth.dagger.module.DomainModule;
import com.siziksu.bluetooth.dagger.module.PresenterModule;

public class App extends Application {

    private ApplicationComponent applicationComponent;

    private static App app;

    public static App get() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        initDagger();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    private void initDagger() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .dataModule(new DataModule())
                .domainModule(new DomainModule())
                .presenterModule(new PresenterModule())
                .build();
        applicationComponent.inject(this);
    }
}
