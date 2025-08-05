package com.nakibul.ifarmermovieapp.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    // Define your dependencies here
    // For example, you can provide Retrofit, Room, or any other dependencies needed for the app.
    // Use @Provides and @Singleton annotations to define how to create instances of these dependencies.
}