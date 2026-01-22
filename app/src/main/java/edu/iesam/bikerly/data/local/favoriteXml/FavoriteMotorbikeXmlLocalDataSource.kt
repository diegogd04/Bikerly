package edu.iesam.bikerly.data.local.favoriteXml

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import edu.iesam.bikerly.app.domain.ErrorApp
import edu.iesam.bikerly.domain.Motorbike
import org.koin.core.annotation.Single

@Single
class FavoriteMotorbikeXmlLocalDataSource(private val context: Context) {

    private val sharedPreferences = context.getSharedPreferences(
        "favorites_motorbikes",
        Context.MODE_PRIVATE
    )
    private val gson = Gson()

    fun save(motorbike: Motorbike) {
        val motorbikeXml = motorbike.toXmlModel()
        sharedPreferences.edit {
            putString(
                motorbike.id.toString(),
                gson.toJson(motorbikeXml, MotorbikeXmlModel::class.java)
            )
        }
    }

    fun getList(): Result<List<Motorbike>> {
        return try {
            val motorbikeList = sharedPreferences.all.values
                .map { motorbike ->
                    gson.fromJson(motorbike.toString(), MotorbikeXmlModel::class.java)
                }
                .sortedByDescending { motorbike ->
                    motorbike.addedAt
                }
                .map { motorbike ->
                    motorbike.toModel()
                }

            Result.success(motorbikeList)
        } catch (error: ErrorApp.DataError) {
            Result.failure(error)
        }
    }

    fun remove(motorbike: Motorbike) {
        sharedPreferences.edit {
            remove(motorbike.id.toString())
        }
    }

    fun isFavorite(motorbike: Motorbike): Boolean {
        return sharedPreferences.contains(motorbike.id.toString())
    }

}