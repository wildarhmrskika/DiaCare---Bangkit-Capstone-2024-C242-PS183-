package com.capstone.diacare.di

import android.content.Context
import androidx.room.Room
import com.capstone.diacare.data.local.AppDatabase
import com.capstone.diacare.data.local.ChatDao
import com.capstone.diacare.data.local.PredictionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun providePredictionDao(appDatabase: AppDatabase): PredictionDao {
        return appDatabase.predictionDao()
    }

    @Provides
    @Singleton
    fun provideChatDao(appDatabase: AppDatabase): ChatDao {
        return appDatabase.chatDao()
    }
}