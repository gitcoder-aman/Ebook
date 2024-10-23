package com.tech.ebook

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application()