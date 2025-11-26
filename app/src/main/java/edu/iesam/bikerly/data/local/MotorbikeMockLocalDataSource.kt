package edu.iesam.bikerly.data.local

import androidx.core.net.toUri
import edu.iesam.bikerly.domain.Motorbike
import org.koin.core.annotation.Single

@Single
class MotorbikeMockLocalDataSource {

    private val motorbikes = listOf(
        Motorbike(
            "1",
            "Kawasaki",
            "Ninja 650",
            "2022",
            "Sport",
            "649.0",
            "https://cdn.dealerspike.com/imglib/v1/800x600/imglib/Assets/Inventory/07/7C/077C65E3-5BF7-4435-8BCA-D28995829246.jpg".toUri()
        ),
        Motorbike(
            "2",
            "Kawasaki",
            "KX250",
            "2022",
            "Cross / motocross",
            "250.0",
            "https://storage.kawasaki.eu/public/kawasaki.eu/en-EU/model/imported/BE00000340EBFBEAA5.jpg".toUri()
        ),
        Motorbike(
            "3",
            "Ducati",
            "DesertX",
            "2022",
            "Enduro / offroad",
            "937.0",
            "https://mcn-images.bauersecure.com/wp-images/186156/ducati-desertx-01.jpg".toUri()
        ),
        Motorbike(
            "4",
            "Ducati",
            "Panigale V2 Bayliss",
            "2022",
            "Sport",
            "955.0",
            "https://res.cloudinary.com/twisted-road/image/upload/c_fill,g_auto,h_600,w_900,q_auto,f_auto/v1661024597/twisted_api/production/vehicles/uxdmhg8xqgwgimmxl0pf.jpg".toUri()
        ),
        Motorbike(
            "5",
            "BMW",
            "S 1000 RR",
            "2022",
            "Sport",
            "999.0",
            "https://soymotero.net/wp-content/uploads/2022/09/a3olig557zb55jsgaq57woc6im.jpg".toUri()
        )
    )

    fun getMotorbikeList(): List<Motorbike> {
        return motorbikes
    }
}