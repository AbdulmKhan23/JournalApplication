package com.khan.journalapplication.di

import android.content.Context
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import com.khan.journalapplication.data.Database
import com.khan.journalapplication.data.DatabaseDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Singleton
    @Provides
    fun providesDatabaseDAO(journalDatabase: Database): DatabaseDAO
     = journalDatabase.journalDao()

    @Singleton
    @Provides
    fun providesAppDatabase(@ApplicationContext context : Context):Database
    = databaseBuilder(
        context,
        Database::class.java,
        "journal_db").fallbackToDestructiveMigration().build()



}