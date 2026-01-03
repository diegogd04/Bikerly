package edu.iesam.bikerly.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import edu.iesam.bikerly.app.di.REMOTE_TIME_CACHE
import edu.iesam.bikerly.app.domain.ErrorApp
import edu.iesam.bikerly.domain.Motorbike
import kotlinx.coroutines.tasks.await
import org.koin.core.annotation.Single

@Single
class MotorbikeFirebaseRemoteDataSource(private val firestore: FirebaseFirestore) {

    suspend fun setMotorbikeList(motorbikeApiList: List<Motorbike>) {
        val ms = System.currentTimeMillis()

        deleteMotorbikeList()

        val motorbikeList = motorbikeApiList.map { motorbike ->
            motorbike.toRemoteModel(ms)
        }

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
        val motorbikeFirebaseList =
            firestore.collection("motorbikes")
                .orderBy("id", Query.Direction.DESCENDING)
                .get()
                .await()
                .map {
                    it.toObject(MotorbikeRemoteModel::class.java)
                }

        return if (motorbikeFirebaseList.isNotEmpty()) {
            if (motorbikeFirebaseList[0].createdAt.plus(REMOTE_TIME_CACHE) > System.currentTimeMillis()) {
                val motorbikeList = motorbikeFirebaseList.map { motorbike ->
                    motorbike.toModel()
                }
                Result.success(motorbikeList)
            } else {
                Result.failure(ErrorApp.DataError)
            }
        } else {
            Result.failure(ErrorApp.DataError)
        }
    }

    suspend fun getMotorbikeById(motorbikeId: Int): Result<Motorbike> {
        val motorbike = firestore.collection("motorbikes")
            .whereEqualTo("id", motorbikeId)
            .get()
            .await()
            .first()
            .toObject(MotorbikeRemoteModel::class.java).toModel()

        return if (motorbike != null) {
            Result.success(motorbike)
        } else {
            Result.failure(ErrorApp.DataError)
        }
    }
}