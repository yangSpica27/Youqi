package com.spica.app.di

import android.content.Context
import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import com.spica.app.network.HttpLoggingInterceptor
import com.spica.app.network.AppClient
import com.spica.app.network.cookie.ClearableCookieJar
import com.spica.app.network.cookie.PersistentCookieJar
import com.spica.app.network.cookie.SetCookieCache
import com.spica.app.network.cookie.SharedPrefsCookiePersistor
import com.spica.app.network.service.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


  @Provides
  @Singleton
  fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {

    val cookieJar: ClearableCookieJar =
      PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(context))

    return OkHttpClient.Builder()
      .addInterceptor(HttpLoggingInterceptor())
      .cookieJar(cookieJar)
      .build()
  }

  @Provides
  @Singleton
  fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
      .client(okHttpClient)
      .baseUrl("https://youqi.taguxdesign.com/api/")
      .addConverterFactory(MoshiConverterFactory.create())
      .addCallAdapterFactory(CoroutinesResponseCallAdapterFactory.create())
      .build()
  }

  @Provides
  @Singleton
  fun provideApiService(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
  }

  @Provides
  @Singleton
  fun provideApiClient(apiService: ApiService): AppClient {
    return AppClient(apiService)
  }


}