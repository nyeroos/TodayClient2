package com.example.todayclient.di;

import com.example.todayclient.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract MainActivity mainActivity();
}

