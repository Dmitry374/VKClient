package com.dima.vkclient.di

import android.content.Context
import androidx.room.Room
import com.dima.vkclient.BuildConfig
import com.dima.vkclient.R
import com.dima.vkclient.common.*
import com.dima.vkclient.database.NewsDao
import com.dima.vkclient.database.NewsDatabase
import com.dima.vkclient.domain.BitmapSaver
import com.dima.vkclient.domain.NewsInteractor
import com.dima.vkclient.network.ApiService
import com.dima.vkclient.network.TokenHelper
import com.dima.vkclient.repository.NewsRepository
import com.dima.vkclient.storage.MediaStoreExporter
import com.dima.vkclient.ui.main.MainPresenter
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideContext(): Context {
        return context
    }

    @Singleton
    @Provides
    fun provideApiService(okHttpClient: OkHttpClient): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(tokenHelper: TokenHelper): OkHttpClient {
        // init logging interceptor
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        val tokenInterceptor = object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val httpUrl = chain.request().url.newBuilder()
                    .addQueryParameter("access_token", tokenHelper.token)
                    .addQueryParameter("v", Constants.API_VERSION)
                    .build()

                return chain.proceed(
                    chain.request().newBuilder()
                        .url(httpUrl)
                        .build()
                )
            }

        }

        return OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideTokenHelper(): TokenHelper {
        return TokenHelper()
    }

    @Singleton
    @Provides
    fun provideDb(context: Context): NewsDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            NewsDatabase::class.java,
            Constants.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideNewsDao(db: NewsDatabase): NewsDao {
        return db.newsDao()
    }

    @Singleton
    @Provides
    fun provideDataMapper(): DataMapper {
        return DataMapper()
    }

    @Singleton
    @Provides
    fun provideNewsRepository(
        apiService: ApiService,
        newsDao: NewsDao,
        dataMapper: DataMapper
    ): NewsRepository {
        return NewsRepository(apiService, newsDao, dataMapper)
    }

    @Singleton
    @Provides
    fun provideResourceProvider(context: Context): ResourceProvider {
        return ResourceProvider(context)
    }

    @Singleton
    @Provides
    fun provideSharedPreferencesHelper(context: Context): SharedPreferencesHelper {
        return SharedPreferencesHelper(
            context.getSharedPreferences(
                context.resources.getString(R.string.app_name),
                Context.MODE_PRIVATE
            )
        )
    }

    @Singleton
    @Provides
    fun provideDisplayScreenMetrics(context: Context): DisplayScreenMetrics {
        return DisplayScreenMetrics(context)
    }

    @Singleton
    @Provides
    fun provideNewsInteractor(
        newsRepository: NewsRepository,
        sharedPreferencesHelper: SharedPreferencesHelper
    ): NewsInteractor {
        return NewsInteractor(newsRepository, sharedPreferencesHelper)
    }

    @Singleton
    @Provides
    fun provideMediaStoreExporter(
        resourceProvider: ResourceProvider,
        applicationContext: Context
    ): MediaStoreExporter {
        return MediaStoreExporter(
            resourceProvider = resourceProvider,
            applicationContext = applicationContext,
            resolver = applicationContext.contentResolver
        )
    }

    @Singleton
    @Provides
    fun provideBitmapSaver(mediaStoreExporter: MediaStoreExporter): BitmapSaver {
        return BitmapSaver(mediaStoreExporter)
    }

    @Singleton
    @Provides
    fun provideMainPresenter(newsInteractor: NewsInteractor): MainPresenter {
        return MainPresenter(newsInteractor)
    }
}