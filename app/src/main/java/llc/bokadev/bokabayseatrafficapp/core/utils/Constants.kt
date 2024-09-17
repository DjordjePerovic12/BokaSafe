package llc.amplitudo.flourish_V2.core.utils

import androidx.compose.runtime.mutableStateListOf
import com.google.android.gms.maps.model.LatLng
import llc.bokadev.bokabayseatrafficapp.R
import llc.bokadev.bokabayseatrafficapp.domain.model.Anchorage
import llc.bokadev.bokabayseatrafficapp.domain.model.AnchorageZone
import llc.bokadev.bokabayseatrafficapp.domain.model.Buoy
import llc.bokadev.bokabayseatrafficapp.domain.model.ProhibitedAnchoringZone
import llc.bokadev.bokabayseatrafficapp.domain.model.Checkpoint
import llc.bokadev.bokabayseatrafficapp.domain.model.Pipeline
import llc.bokadev.bokabayseatrafficapp.domain.model.ShipWreck
import llc.bokadev.bokabayseatrafficapp.domain.model.UnderwaterCable

object Constants {
    const val ANIMATION_DURATION = 500
    const val DATASTORE_NAME = "Flourish datastore"
    const val UNAUTHORIZED = "Unauthorized"
    const val CHECK_CONNECTION = "Check your internet connection."
    const val NETWORK_PROBLEM = "An error occurred during communication with the server."
    const val BASE_URL_IMAGES = "https://v2-test.flourishapp.me"

    val lighthouses = mutableStateListOf(
        Checkpoint(
            id = 1,
            name = "Ostrvce Mamula",
            latitude = 42.39543,
            longitude = 18.55787,
            isSelected = false,
            characteristics = "F1 2 3s"
        ),
        Checkpoint(
            id = 2, name = "Dobreč", latitude = 42.42190, longitude = 18.54665, isSelected = false,
            characteristics = "F1 G 5s"
        ),
        Checkpoint(
            id = 3,
            name = "Rose, W strana pristana",
            latitude = 42.42818,
            longitude = 18.55632,
            isSelected = false,
            characteristics = "F G"
        ),
        Checkpoint(
            id = 4,
            name = "HERCEG NOVI, glava lukobrana",
            latitude = 42.44962,
            longitude = 18.53238,
            isSelected = false,
            characteristics = "F1 (2)G 5s"
        ),
        Checkpoint(
            id = 5, name = "Meljine", latitude = 42.45250, longitude = 18.56050, isSelected = false,
            characteristics = "F1 (2)R 6s"
        ),

        Checkpoint(
            id = 6,
            name = "Lazure, sjeverni ulaz",
            latitude = 42.45370,
            longitude = 18.56178,
            isSelected = false,
            characteristics = "F G"
        ),
        Checkpoint(
            id = 7,
            name = "Lazure, južni ulaz",
            latitude = 42.45343,
            longitude = 18.56195,
            isSelected = false,
            characteristics = "F R"
        ),
        Checkpoint(
            id = 8,
            name = "Lazure, Kardinalna plutača južnog kvadranta",
            latitude = 42.45343,
            longitude = 18.56195,
            isSelected = false,
            characteristics = "VQ(6) + LF1 10s"
        ),
        Checkpoint(
            id = 9,
            name = "Zelenika,NW ugao pristana",
            latitude = 42.44968,
            longitude = 18.57132,
            isSelected = false,
            characteristics = "F1 R 3s"
        ),
        Checkpoint(
            id = 10,
            name = "Baošići, glava koljenastog gata",
            latitude = 42.43712,
            longitude = 18.62932,
            isSelected = false,
            characteristics = "F1 R 5s"
        ),
        Checkpoint(
            id = 11,
            name = "Pristan, glava gata",
            latitude = 42.42602,
            longitude = 18.60180,
            isSelected = false,
            characteristics = "F1 G 3s"
        ),
        Checkpoint(
            id = 12,
            name = "Porto Novi",
            latitude = 42.43315,
            longitude = 18.59753,
            isSelected = false,
            characteristics = "F1 R"
        ),
        Checkpoint(
            id = 13,
            name = "Porto Novi, južni lukobran",
            latitude = 42.43190,
            longitude = 18.60535,
            isSelected = false,
            characteristics = "F1 G 3s"
        ),
        Checkpoint(
            id = 14,
            name = "Porto Novi, glava sjevernog lukobrana",
            latitude = 42.43288,
            longitude = 18.60533,
            isSelected = false,
            characteristics = "F G"
        ),
        Checkpoint(
            id = 15,
            name = "Porto Novi, glava južnog lukobrana",
            latitude = 42.43233,
            longitude = 18.60503,
            isSelected = false,
            characteristics = "F R"
        ),
        Checkpoint(
            id = 16,
            name = "Porto Novi, sjeverni kraj plutajućeg valobrana",
            latitude = 42.43318,
            longitude = 18.60672,
            isSelected = false,
            characteristics = "F1(2) W 5s"
        ),
        Checkpoint(
            id = 17,
            name = "Porto Novi, južni kraj plutajućeg valobrana",
            latitude = 42.43163,
            longitude = 18.60607,
            isSelected = false,
            characteristics = "F1(2) W 5s"
        ),
        Checkpoint(
            id = 18,
            name = "Porto Novi, kardinalna plutača istočnog kvadranta",
            latitude = 42.43220,
            longitude = 18.60667,
            isSelected = false,
            characteristics = "Q(3) 10s"
        ),
        Checkpoint(
            id = 19,
            name = "Krašići, glava gata",
            latitude = 42.40927,
            longitude = 18.65335,
            isSelected = false,
            characteristics = "F G"
        ),
        Checkpoint(
            id = 20,
            name = "Bjelila (Oko), NW ugao pristana",
            latitude = 42.40617,
            longitude = 18.66610,
            isSelected = false,
            characteristics = "F1 G 2s"
        ),
        Checkpoint(
            id = 21,
            name = "Žukavac, koljenasti gat",
            latitude = 42.40404,
            longitude = 18.68606,
            isSelected = false,
            characteristics = "F1 G 3s"
        ),
        Checkpoint(
            id = 22,
            name = "Plićina Tunja",
            latitude = 42.41591045284294,
            longitude = 18.680426794570497,
            isSelected = false,
            characteristics = "F1(2) WR"
        ),
        Checkpoint(
            id = 23,
            name = "Kalimanj",
            latitude = 42.42673,
            longitude = 18.69958,
            isSelected = false,
            characteristics = "F R"
        ),
        Checkpoint(
            id = 24,
            name = "Plićina Kalimanj",
            latitude = 42.42565057502816,
            longitude = 18.699151789153444,
            isSelected = false,
            characteristics = "F1 R 4s"
        ),
        Checkpoint(
            id = 25,
            name = "Porto Montenegro, S strana pontona",
            latitude = 42.42999,
            longitude = 18.69119,
            isSelected = false,
            characteristics = "F1 R 4s"
        ),
        Checkpoint(
            id = 26,
            name = "Porto Montenegro, N strana pontona",
            latitude = 42.43413,
            longitude = 18.68997,
            isSelected = false,
            characteristics = "F1 G 4s"
        ),
        Checkpoint(
            id = 27,
            name = "Porto Montenegro, gat 1",
            latitude = 42.43153,
            longitude = 18.69060,
            isSelected = false, characteristics = "F1 W 3s"
        ),
        Checkpoint(
            id = 28,
            name = "Porto Montenegro, gat 4",
            latitude = 42.43372,
            longitude = 18.69083,
            isSelected = false,
            characteristics = "F1 R 4s"
        ),
        Checkpoint(
            id = 29,
            name = "Pristan Staničić, NW ugao gata",
            latitude = 42.43025,
            longitude = 18.69510,
            isSelected = false,
            characteristics = "F1 R(2) 6s"
        ),
        Checkpoint(
            id = 30,
            name = "Rt Seljanovo",
            latitude = 42.439153997139435,
            longitude = 18.684438548431622,
            isSelected = false,
            characteristics = "F1 R 3s"
        ),
        Checkpoint(
            id = 31,
            name = "Rt Sv. Neđelja",
            latitude = 42.46015126097649,
            longitude = 18.675817066343665,
            isSelected = false,
            characteristics = "F1 R 2s"
        ),
        Checkpoint(
            id = 32,
            name = "Rt Opatovo",
            latitude = 42.45942,
            longitude = 18.68148,
            isSelected = false,
            characteristics = "F1 G 2s"
        ),
        Checkpoint(
            id = 33,
            name = "Turski rt",
            latitude = 42.47808,
            longitude = 18.68672,
            isSelected = false,
            characteristics = "F1 W(2) 5s"
        ),
        Checkpoint(
            id = 34,
            name = "Rt  Verige (Gospa od Anđela)",
            latitude = 42.47742500848345,
            longitude = 18.690245160951605,
            isSelected = false,
            characteristics = "F1 G 3s"
        ),
        Checkpoint(
            id = 35,
            name = "Kostanjica, sredina gata",
            latitude = 42.48660,
            longitude = 18.66592,
            isSelected = false, characteristics = "F R"

        ),
        Checkpoint(
            id = 36,
            name = "Morinj,NE ugao pristana",
            latitude = 42.49032,
            longitude = 18.65032,
            isSelected = false,
            characteristics = "F G"
        ),
        Checkpoint(
            id = 37,
            name = "RISAN, glava gata",
            latitude = 42.51344,
            longitude = 18.69432,
            isSelected = false,
            characteristics = "F1 G(3) 6s"
        ),
        Checkpoint(
            id = 38,
            name = "Gospa od Škrpjela, ostrvce",
            latitude = 42.487290731072704,
            longitude = 18.68821472872734,
            isSelected = false,
            characteristics = "F1 R(2) 6s"
        ),
        Checkpoint(
            id = 39,
            name = "PERAST, sredina pristana",
            latitude = 42.48625,
            longitude = 18.69843,
            isSelected = false,
            characteristics = "F1 R(3) 7s"
        ),
        Checkpoint(
            id = 40,
            name = "Stoliv, glava gata",
            latitude = 42.47253,
            longitude = 18.71195,
            isSelected = false,
            characteristics = "F G"
        ),
        Checkpoint(
            id = 41,
            name = "Prčanj, Markov rt",
            latitude = 42.46563,
            longitude = 18.73415,
            isSelected = false,
            characteristics = "F1 G(2) 6s"
        ),
        Checkpoint(
            id = 42,
            name = "Prčanj, N dio pristana",
            latitude = 42.45368,
            longitude = 18.74895,
            isSelected = false,
            characteristics = "F1 G 3s"
        ),
        Checkpoint(
            id = 43,
            name = "Rđakovo, N gat",
            latitude = 42.44835,
            longitude = 18.75415,
            isSelected = false,
            characteristics = "F1 G 5s"
        ),
        Checkpoint(
            id = 44,
            name = "Rt Plagente",
            latitude = 42.43608,
            longitude = 18.76370,
            isSelected = false,
            characteristics = "F1 R(2) 5s"
        ),
        Checkpoint(
            id = 45,
            name = "Muo, glava pristana",
            latitude = 42.43350,
            longitude = 18.75730,
            isSelected = false,
            characteristics = "F1 G(2) 5s"
        ),
        Checkpoint(
            id = 46,
            name = "KOTOR, NW strana obale",
            latitude = 42.42612,
            longitude = 18.76672,
            isSelected = false,
            characteristics = "F1 R 3s"
        ),
    )

    val shipwrecks: MutableList<ShipWreck> = mutableListOf(
        ShipWreck(
            id = 1,
            name = "Spitfire MK9 Supermarine",
            description = "Close to Kabala point near Rose in Boka Kotorska, at the depth of 32m lays the wreck of the 2nd WW British airplane. Spitfire MK9 Supermarine, was the one of the most famous and successful multi task aircrafts of the Second World War. Its remains lie on the sandy bottom, within a diameter of 40m. The most noticeable is the engine, around the engine are parts of the tailsection with rudder and tail wheel, two machine guns, one wing, front wheels, as well as many other hydraulic parts. Despite the fact that it is badly damaged, the wreck is still impressive and makes for an interesting diving experience.",
            isSelected = false,
            depth = "32 m",
            latitude = 42.426436,
            longitude = 18.5412919
        ),
        ShipWreck(
            id = 2,
            name = "SS Tihany",
            description = "Austro-Hungarian passenger/cargo steam ship Tihany was built in 1908 in Trieste. On the day of her disaster, Tihany was transporting a load of coal and oil from Kotor to Bar. On February 12th 1917, due to the navigational error, the ship crashed into the point Arza near the entrance to Boka Kotorska. During the salvage operation, when they started to tug the ship towards Boka Kotorsaka, she started taking water and ship near the island Mamula. The wreck is mostly intact. Despite considerable depth, it is one of the most popular and exiting wreck dive sites in Montenegro.",
            isSelected = false,
            depth = "40 m",
            latitude = 42.399833,
            longitude = 18.55765
        ),
        ShipWreck(
            id = 3,
            name = "Golešnica 76T",
            description = "Torpedo ship Golešnica was built as a part of the order for Austro-Hungarian navy. 76T took part in the First World War naval operations in eastern and western Adriatic. In 1920 it became a property of the newly created Kingdom of SCS, a future Yugoslavia. In 1941 it was seized by the Italian navy, and deployed in operations against partisans along the Dalmatian coast. After the Second World War, it became a part of new Yugoslavian navy, renamed Golešnica, and served mostly as the border patrol ship. In 1959 it was used by the navy for target practice, and sunk near Boka Kotorska. It lies on the port side on the sandy bottom.",
            isSelected = false,
            depth = "40 m",
            latitude = 42.401275,
            longitude = 18.5670498
        ),
        ShipWreck(
            id = 4,
            name = "Patrol ship PBR 512",
            description = "Patrol ship PBR 512 was a part of the Yugoslavian navy until she was decommissioned in 1972. In 1983 she was used as a target for the rocket launch exercise and sunk in the Žanjice bay near Boka Kotorska. The wreck is in very good condition. Because of it’s location and the relatively shallow depth it lies on, it is probably the most visited wreck in Montenegro.",
            isSelected = false,
            depth = "24 m",
            latitude = 42.399031,
            longitude = 18.5764999
        ),
        ShipWreck(
            id = 5,
            name = "16th century shipwreck",
            description = "The strong trade relations between Venice and the Ottoman Empire in the 16th century led to frequent maritime traffic along the Dalmatian coast. These merchant ventures are confirmed by several remains of the shipwrecks and cargos at the bottom of the Eastern Adriatic Sea. There is a high possibility that a shipwreck dated to the late 16th century near the Cape of Kabala in the Bay of Kotor belonged to the Venetian merchant fleet. The cargo which has been unearthed comprises Iznik ceramics of Ottoman origins and Venetian kitchenware and tableware. The remains of the ship’s structure, the ship’s equipment, cargo and weapons lie at a depth of 29 to 31 meters.",
            isSelected = false,
            depth = "30 m",
            latitude = 42.4208,
            longitude = 18.545832
        ),
        ShipWreck(
            id = 6,
            name = "Wreck Higgins 78",
            description = "The wreck \"Higgins\" is located at a depth of 30 – 35 m near the fishing village of Rose at the entrance to the Bay of Kotor. The Yugoslav torpedo boat \"Higgins\" was built in the shipyard \"M. Cetinić\" on the island of Korčula in 1952, according to the American model that was imported from the United States of America. The length of the ship is 23 m, the width is 6 m.",
            isSelected = false,
            depth = "30 m",
            latitude = 42.433192,
            longitude = 18.5679969
        ),
        ShipWreck(
            id = 7,
            name = "Tunj PR-38",
            description = "The military tugboat PR-38 \"Tunj\" was built in the Shipyard \"Brodogradjevna industrija\" Split\" in 1954. The ship was used by the Yugoslav Navy, she was modernized in 1975. Since 2006, she has been in the ranks of the Navy as a military tugboat PR-38 \"Tunj\". Montenegro. In October 2013, the ship PR-38 \"Tunj\" sank in the Bay of Kotor near the city of Perast. The ship \"Tunj\" lies at a shallow depth. While diving on the boat PR-38 \"Tunj\" you can see crabs, octopuses, cuttlefish.",
            isSelected = false,
            depth = "18 m",
            latitude = 42.494008,
            longitude = 18.691178
        ),
    )


    val prohibitedProhibitedAnchoringZones = mutableListOf(
        ProhibitedAnchoringZone(
            id = 1, name = "Kumbor", isSelected = false,
            points = listOf(
                Pair(
                    LatLng(
                        42.43256149323977, 18.603624549053823
                    ),
                    LatLng(
                        42.42521345371988, 18.603765538615107
                    ),
                ),
                Pair(
                    LatLng(
                        42.440093300200864, 18.58496174232076

                    ), LatLng(
                        42.43126251897645, 18.578641334910706
                    )
                ),
            )
        ),
        ProhibitedAnchoringZone(
            id = 2, name = "Verige", isSelected = false,
            points = listOf(
                Pair(
                    LatLng(
                        42.484205, 18.703520
                    ),
                    LatLng(

                        42.474782, 18.700578
                    ),
                ),
                Pair(
                    LatLng(
                        42.494397, 18.691337

                    ), LatLng(
                        42.482783, 18.678783
                    )
                ),
                Pair(
                    LatLng(
                        42.458194, 18.670745

                    ), LatLng(
                        42.456095, 18.681517
                    )
                )
            )
        ),

        ProhibitedAnchoringZone(
            id = 3, name = "Risan", isSelected = false,
            points = listOf(
                Pair(
                    LatLng(
                        42.494397, 18.691337

                    ),
                    LatLng(
                        42.504960, 18.676755
                    ),
                ),
            )
        ),
        ProhibitedAnchoringZone(
            id = 4, name = "Sv. Stasije", isSelected = false,
            points = listOf(
                Pair(
                    LatLng(
                        42.466280, 18.763085

                    ),
                    LatLng(
                        42.456948, 18.744380
                    ),
                ),
                Pair(
                    LatLng(
                        42.472813, 18.763057

                    ),
                    LatLng(
                        42.460600, 18.738660
                    ),
                ),
            )
        ),
        ProhibitedAnchoringZone(
            id = 5, name = "Igalo", isSelected = false,
            points = listOf(
                Pair(
                    LatLng(
                        42.452898, 18.528720

                    ),
                    LatLng(
                        42.441410, 18.508865
                    ),
                ),
            )
        ),
        ProhibitedAnchoringZone(
            id = 6, name = "Igalo", isSelected = false,
            points = listOf(
                Pair(
                    LatLng(
                        42.452898, 18.528720

                    ),
                    LatLng(
                        42.441410, 18.508865
                    ),
                ),
            )
        ),

        ProhibitedAnchoringZone(
            id = 7, name = "Herceg Novi W", isSelected = false,
            points = listOf(
                Pair(
                    LatLng(
                        42.449630, 18.533730

                    ),
                    LatLng(
                        42.433433, 18.530842
                    ),
                ),
                Pair(
                    LatLng(
                        42.432925, 18.535683

                    ),
                    LatLng(
                        42.448737, 18.538462
                    ),
                ),
            )
        ),

        ProhibitedAnchoringZone(
            id = 8, name = "Herceg Novi E", isSelected = false,
            points = listOf(
                Pair(
                    LatLng(
                        42.448737, 18.538462

                    ),
                    LatLng(
                        42.425322, 18.551182
                    ),
                ),
                Pair(
                    LatLng(
                        42.450087, 18.542323

                    ),
                    LatLng(
                        42.426672, 18.555693
                    ),
                ),
            )
        ),

        ProhibitedAnchoringZone(
            id = 9, name = "Meljine", isSelected = false,
            points = listOf(
                Pair(
                    LatLng(
                        42.451305, 18.557036

                    ),
                    LatLng(
                        42.450017, 18.558051
                    ),
                ),
                Pair(
                    LatLng(
                        42.444736, 18.559164

                    ),
                    LatLng(
                        42.440567, 18.560867

                    ),
                ),
                Pair(
                    LatLng(
                        42.440933, 18.565319

                    ),
                    LatLng(
                        42.445493, 18.563903
                    ),
                ),
                Pair(
                    LatLng(
                        42.451032, 18.563395

                    ),
                    LatLng(
                        42.454444, 18.562258
                    ),
                ),
            )
        ),


        ProhibitedAnchoringZone(
            id = 10,
            name = "Porto Montenegro",
            isSelected = false,
            points = listOf(
                Pair(
                    LatLng(
                        42.435833, 18.6925,

                        ),
                    LatLng(
                        42.431389, 18.685556
                    ),
                ),
                Pair(
                    LatLng(

                        42.414722, 18.705556

                    ),
                    LatLng(
                        42.3975, 18.697222

                    ),
                ),
            )
        ),
    )

    val anchorages = mutableListOf(
        Anchorage(
            id = 1,
            name = "Sidrište Ljuta",
            latitude = 42.481370,
            longitude = 18.754385,
            speedLimit = 14,
            isSelected = false
        ),
        Anchorage(
            id = 2,
            name = "Sidrište Kamenarovići",
            latitude = 42.456335,
            longitude = 18.762592,
            speedLimit = 14,
            isSelected = false
        ),
        Anchorage(
            id = 3,
            name = "Sidrište Seljanovo",
            latitude = 42.44865,
            longitude = 18.68261,
            speedLimit = 14,
            isSelected = false
        ),
        Anchorage(
            id = 4,
            name = "Sidrište Herceg Novi - Škver",
            latitude =
            42.448265,
            longitude = 18.529219,
            speedLimit = 14,
            isSelected = false
        ),
    )
    val underwaterCables = mutableListOf(
        UnderwaterCable(
            id = 1,
            name = "Prevlaka cable",
            coordinates = Pair(
                LatLng(42.40595, 18.70155),
                LatLng(42.40337, 18.68953),
            ),
            isSelected = false
        ),
        UnderwaterCable(
            id = 2,
            name = "Ostrvo Stradioti",
            coordinates = Pair(
                LatLng(42.410507, 18.677893),
                LatLng(42.404572, 18.679193),
            ),
            isSelected = false
        ),
    )

    val anchorageZones = mutableListOf(
        AnchorageZone(
            id = 1,
            name = "N stradioti lokacija 1",
            points = listOf(
                LatLng(
                    42.42319444,
                    18.68083333
                ),
                LatLng(
                    42.42152778,
                    18.68361111
                ),
                LatLng(
                    42.42569444,
                    18.68833333
                ),
                LatLng(
                    42.42736111,
                    18.68555556
                ),


                ),
            isSelected = false
        ),
        AnchorageZone(
            id = 2,
            name = "N stradioti lokacija 2",
            points = listOf(
                LatLng(
                    42.4192278,
                    18.68582500
                ),
                LatLng(
                    42.423275,
                    18.69065
                ),
                LatLng(
                    42.41969722,
                    18.69611389
                ),
                LatLng(
                    42.4156500,
                    18.69128611
                ),

                ),
            isSelected = false
        ),
        AnchorageZone(
            id = 3,
            name = "N stradioti lokacija 3",
            points = listOf(
                LatLng(
                    42.41805556,
                    18.69916667
                ),
                LatLng(
                    42.4141444444,
                    18.69527778
                ),
                LatLng(
                    42.41166667,
                    18.70055556
                ),

                LatLng(
                    42.41527778,
                    18.70416667
                ),

                ),
            isSelected = false
        ),
        AnchorageZone(
            id = 4,
            name = "S stradioti",
            points = listOf(
                LatLng(
                    42.40660833,
                    18.68446944
                ),
                LatLng(
                    42.4078889,
                    18.68504444
                ),
                LatLng(
                    42.40576389,
                    18.6936889
                ),
                LatLng(
                    42.40448056,
                    18.69311667
                ),

                ),
            isSelected = false
        ),
    )
    val pipelines = mutableListOf(
        Pipeline(
            id = 1,
            name = "Cjevovod ostrvo Stradioti",
            points = listOf(
                LatLng(
                    42.406873, 18.712915,

                    ),
                LatLng(
                    42.412118, 18.695215
                ),
                LatLng(
                    42.411735, 18.693063
                )
            )
        )
    )
    val buoys = mutableListOf(
        Buoy(
            id = 1,
            name = "KOTOR, specijalna bova",
            coordinates = LatLng(42.4274, 18.7660),
            isSelected = false,
            characteristic = "Oc (2) 3s"
        ),
        Buoy(
            id = 2,
            name = "Bijela, bova 1",
            coordinates = LatLng(42.448611, 18.6475),
            isSelected = false,
            characteristic = "Fl.Y.12s2M"

        ),
        Buoy(
            id = 3,
            name = "Bijela, bova 2",
            coordinates = LatLng(42.445139, 18.652222),
            isSelected = false,
            characteristic = "Fl.Y.12s2M"
        ),

        Buoy(
            id = 4,
            name = "Bijela, bova 3",
            coordinates = LatLng(
                42.447778, 18.656944
            ),
            isSelected = false,
            characteristic = "Fl.Y.12s2M"
        ),
        Buoy(
            id = 5,
            name = "Bijela, bova 4",
            coordinates = LatLng(42.451111, 18.658056),
            isSelected = false,
            characteristic = "Fl.Y.12s2M"
        ),
        Buoy(
            id = 6,
            name = "Bijela, bova 5",
            coordinates = LatLng(42.45, 18.661389),
            isSelected = false,
            characteristic = "Fl.Y.12s2M"
        ),
        Buoy(
            id = 7,
            name = "Meljine, bova 1",
            coordinates = LatLng(42.449444, 18.561389),
            isSelected = false,
            characteristic = "Fl.Y.3s3M"
        ),
        Buoy(
            id = 8,
            name = "Meljine, bova 2",
            coordinates = LatLng(42.441389, 18.561667),
            isSelected = false,
            characteristic = "Fl.Y.3s3M"
        ),
    )

}