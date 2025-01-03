package com.byansanur.campuslist.data.local.di

import android.content.Context
import androidx.room.Room
import com.byansanur.campuslist.data.local.CampusDatabases
import com.byansanur.campuslist.data.local.PreferenceHelpers
import com.byansanur.campuslist.data.local.dao.CampusDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) : CampusDatabases {
        return Room.databaseBuilder(
            context.applicationContext,
            CampusDatabases::class.java,
            "campus.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun campusDao(database: CampusDatabases) : CampusDao = database.campusEntityDao()

    @Provides
    fun providePrefHelpers(@ApplicationContext context: Context) : PreferenceHelpers {
        return PreferenceHelpers(context)
    }
}