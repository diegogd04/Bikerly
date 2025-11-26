package edu.iesam.bikerly.app

import android.app.Application
import edu.iesam.bikerly.app.di.LocalModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

class BikerlyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BikerlyApplication)
            modules(
                LocalModule().module
            )
        }
    }
}