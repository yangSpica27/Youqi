package cn.tagux.calendar.di

import android.content.Context
import cn.tagux.calendar.network.AppClient
import cn.tagux.calendar.network.HttpLoggingInterceptor
import cn.tagux.calendar.network.cookie.ClearableCookieJar
import cn.tagux.calendar.network.cookie.PersistentCookieJar
import cn.tagux.calendar.network.cookie.SetCookieCache
import cn.tagux.calendar.network.cookie.SharedPrefsCookiePersistor
import cn.tagux.calendar.network.service.ApiService
import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import com.tencent.tauth.Tencent
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


/**
 * 网络模块
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    /**
     * 注入QQ登陆
     */
    @Provides
    @Singleton
    fun provideQQLogin(@ApplicationContext context: Context): Tencent {

        return Tencent.createInstance("1106365828",
            context)

    }


    /**
     * 注入ohHttpClient
     */
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