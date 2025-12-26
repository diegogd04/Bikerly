package edu.iesam.bikerly.app.di

import com.google.firebase.firestore.FirebaseFirestore
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("edu.iesam.bikerly")
class RemoteModule {

    @Single
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }
}