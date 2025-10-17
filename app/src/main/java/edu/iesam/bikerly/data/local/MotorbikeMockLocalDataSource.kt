package edu.iesam.bikerly.data.local

import edu.iesam.bikerly.domain.Motorbike

class MotorbikeMockLocalDataSource {

    private val motorbikes = listOf(
        Motorbike(
            "1",
            "Kawasaki",
            "Ninja 650",
            "2022",
            "Sport",
            "649.0"
        ),
        Motorbike(
            "2",
            "Kawasaki",
            "Brute Force 300",
            "2022",
            "ATV",
            "271.0"
        ),
        Motorbike(
            "3",
            "Ducati",
            "DesertX",
            "2022",
            "Enduro / offroad",
            "937.0"
        ),
        Motorbike(
            "4",
            "Ducati",
            "Panigale V2 Bayliss",
            "2022",
            "Sport",
            "955.0"
        ),
        Motorbike(
            "5",
            "BMW",
            "S 1000 RR",
            "2022",
            "Sport",
            "999.0"
        )
    )

    fun getMotorbikeList(): List<Motorbike> {
        return motorbikes
    }
}