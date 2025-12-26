package edu.iesam.bikerly.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import edu.iesam.bikerly.app.domain.ErrorApp
import edu.iesam.bikerly.domain.Motorbike
import kotlinx.coroutines.tasks.await
import org.koin.core.annotation.Single

@Single
class MotorbikeFirebaseRemoteDataSource(private val firestore: FirebaseFirestore) {

    suspend fun setMotorbikeList(motorbikeList: List<Motorbike>) {
        deleteMotorbikeList()

        motorbikeList.forEach { motorbike ->
            firestore.collection("motorbikes")
                .add(motorbike)
                .await()
        }
    }

    private suspend fun deleteMotorbikeList() {
        val documents = firestore.collection("motorbikes").get().await()

        documents.documents.forEach { document ->
            document.reference.delete()
        }
    }

    suspend fun getMotorbikeList(): Result<List<Motorbike>> {
        val motorbikeList =
            firestore.collection("motorbikes")
                .orderBy("id", Query.Direction.DESCENDING)
                .get()
                .await()
                .map {
                    it.toObject(MotorbikeRemoteModel::class.java).toModel()
                }

        return if (motorbikeList.isNotEmpty()) {
            Result.success(motorbikeList)
        } else {
            Result.failure(ErrorApp.DataError)
        }
    }
}