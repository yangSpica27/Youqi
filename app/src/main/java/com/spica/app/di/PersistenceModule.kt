package com.spica.app.di

import android.app.Application
import androidx.room.Room
import com.spica.app.persistence.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

  @Provides
  @Singleton
  fun provideAppDatabase(
    application: Application,
  ): AppDatabase {
    return Room
      .databaseBuilder(application, AppDatabase::class.java, "wan_android.db")
      .fallbackToDestructiveMigration()
      .build()
  }


  @Provides
  @Singleton
  fun provideBannerDao(appDatabase: AppDatabase) = appDatabase.bannerDao()

  @Provides
  @Singleton
  fun provideUserDao(appDatabase: AppDatabase) = appDatabase.UserDao()


}