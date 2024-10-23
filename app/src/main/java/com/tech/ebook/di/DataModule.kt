package com.tech.ebook.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.google.firebase.database.FirebaseDatabase
import com.tech.ebook.data.repository.BookRepositoryImpl
import com.tech.ebook.local.dao.EbookDao
import com.tech.ebook.local.database.AppDatabase
import com.tech.ebook.presentation.repo.BookRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideFirebaseRealtimeDatabase(): FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }

    @Provides
    @Singleton
    fun provideBookRepository(
        firebaseDatabase: FirebaseDatabase,
    ): BookRepository {
        return BookRepositoryImpl(
            firebaseDatabase = firebaseDatabase
        )
    }
    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }
    @Provides
    @Singleton
    fun provideDatabase(appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "ebook_database"
        ).fallbackToDestructiveMigration().build()
    }
    @Provides
    @Singleton
    fun provideBookmarkDao(database: AppDatabase): EbookDao {
        return database.bookDao()
    }
}