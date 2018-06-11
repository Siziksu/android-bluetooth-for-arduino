package com.siziksu.bluetooth.dagger.module;

import android.content.Context;

import com.siziksu.bluetooth.App;
import com.siziksu.bluetooth.common.utils.FileUtils;
import com.siziksu.bluetooth.ui.view.main.ItemAdapter;
import com.siziksu.bluetooth.ui.view.main.ItemAdapterContract;
import com.siziksu.bluetooth.ui.view.main.ItemManager;
import com.siziksu.bluetooth.ui.view.main.ItemManagerContract;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class ApplicationModule {

    private final App application;

    public ApplicationModule(App application) {
        this.application = application;
    }

    @Provides
    Context providesContext() {
        return application.getApplicationContext();
    }

    @Singleton
    @Provides
    FileUtils providesFileUtils(Context context) {
        return new FileUtils(context);
    }

    @Provides
    ItemManagerContract providesItemsManager() {
        return new ItemManager();
    }

    @Provides
    ItemAdapterContract providesItemAdapter(Context context, ItemManagerContract manager) {
        return new ItemAdapter(context, manager);
    }
}
