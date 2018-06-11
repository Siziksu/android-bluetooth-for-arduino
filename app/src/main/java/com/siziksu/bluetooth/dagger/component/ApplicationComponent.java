package com.siziksu.bluetooth.dagger.component;

import com.siziksu.bluetooth.App;
import com.siziksu.bluetooth.dagger.module.ApplicationModule;
import com.siziksu.bluetooth.dagger.module.DataModule;
import com.siziksu.bluetooth.dagger.module.DomainModule;
import com.siziksu.bluetooth.dagger.module.PresenterModule;
import com.siziksu.bluetooth.ui.view.main.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                ApplicationModule.class,
                DataModule.class,
                DomainModule.class,
                PresenterModule.class
        }
)
public interface ApplicationComponent {

    void inject(App target);

    void inject(MainActivity target);
}
