package llc.amplitudo.flourish_V2.core.utils

import com.google.android.gms.maps.model.LatLng
import llc.bokadev.bokasafe.domain.model.Anchorage
import llc.bokadev.bokasafe.domain.model.AnchorageZone
import llc.bokadev.bokasafe.domain.model.Buoy
import llc.bokadev.bokasafe.domain.model.ProhibitedAnchoringZone
import llc.bokadev.bokasafe.domain.model.Depth
import llc.bokadev.bokasafe.domain.model.Pipeline
import llc.bokadev.bokasafe.domain.model.ShipWreck
import llc.bokadev.bokasafe.domain.model.UnderwaterCable

object Constants {
    const val ANIMATION_DURATION = 500
    const val DATASTORE_NAME = "Boka Bay Sea Traficc app datastore"
    const val UNAUTHORIZED = "Unauthorized"
    const val CHECK_CONNECTION = "Check your internet connection."
    const val NETWORK_PROBLEM = "An error occurred during communication with the server."
    const val BASE_URL_IMAGES = "https://v2-test.flourishapp.me"


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

    val depths by lazy {
        mutableListOf(
            Depth(111.0, LatLng(42.38566, 18.496381)),
            Depth(109.0, LatLng(42.38459, 18.501755)),
            Depth(106.0, LatLng(42.388522, 18.505841)),
            Depth(106.0, LatLng(42.391929, 18.499154)),
            Depth(105.0, LatLng(42.386598, 18.515183)),
            Depth(103.0, LatLng(42.389999, 18.512482)),
            Depth(103.0, LatLng(42.388528, 18.495883)),
            Depth(102.0, LatLng(42.391967, 18.509006)),
            Depth(101.0, LatLng(42.3939, 18.504399)),
            Depth(101.0, LatLng(42.397568, 18.495854)),
            Depth(96.0, LatLng(42.387941, 18.520308)),
            Depth(95.0, LatLng(42.396536, 18.50263)),
            Depth(94.0, LatLng(42.38631, 18.524776)),
            Depth(93.0, LatLng(42.390623, 18.516808)),
            Depth(92.0, LatLng(42.400281, 18.497128)),
            Depth(88.0, LatLng(42.395337, 18.510955)),
            Depth(87.0, LatLng(42.394318, 18.516791)),
            Depth(86.0, LatLng(42.386397, 18.532975)),
            Depth(86.0, LatLng(42.397712, 18.507422)),
            Depth(85.0, LatLng(42.386584, 18.529073)),
            Depth(84.0, LatLng(42.385668, 18.539835)),
            Depth(84.0, LatLng(42.389786, 18.522342)),
            Depth(83.0, LatLng(42.404463, 18.495859)),
            Depth(81.0, LatLng(42.384793, 18.55756)),
            Depth(80.0, LatLng(42.387242, 18.544368)),
            Depth(79.0, LatLng(42.401266, 18.50393)),
            Depth(77.0, LatLng(42.390414, 18.527566)),
            Depth(76.0, LatLng(42.386181, 18.550703)),
            Depth(76.0, LatLng(42.403301, 18.5018)),
            Depth(75.0, LatLng(42.387994, 18.540388)),
            Depth(74.0, LatLng(42.396976, 18.513004)),
            Depth(69.0, LatLng(42.387489, 18.556032)),
            Depth(68.0, LatLng(42.387768, 18.547538)),
            Depth(66.0, LatLng(42.391085, 18.547169)),
            Depth(64.0, LatLng(42.39291, 18.542722)),
            Depth(64.0, LatLng(42.385239, 18.564486)),
            Depth(64.0, LatLng(42.480174, 18.707807)),
            Depth(64.0, LatLng(42.390042, 18.535559)),
            Depth(64.0, LatLng(42.393366, 18.521718)),
            Depth(63.0, LatLng(42.411763, 18.495807)),
            Depth(63.0, LatLng(42.390829, 18.532855)),
            Depth(62.0, LatLng(42.389997, 18.540299)),
            Depth(62.0, LatLng(42.407907, 18.498238)),
            Depth(61.0, LatLng(42.38797, 18.564076)),
            Depth(61.0, LatLng(42.405772, 18.501918)),
            Depth(60.0, LatLng(42.38662, 18.568735)),
            Depth(60.0, LatLng(42.400549, 18.509518)),
            Depth(59.0, LatLng(42.385914, 18.571431)),
            Depth(59.0, LatLng(42.39112, 18.554327)),
            Depth(57.0, LatLng(42.394588, 18.547436)),
            Depth(57.0, LatLng(42.398237, 18.511598)),
            Depth(56.0, LatLng(42.390046, 18.56269)),
            Depth(54.0, LatLng(42.388344, 18.568707)),
            Depth(54.0, LatLng(42.397346, 18.539502)),
            Depth(53.0, LatLng(42.411115, 18.499307)),
            Depth(51.0, LatLng(42.399441, 18.535016)),
            Depth(51.0, LatLng(42.480292, 18.705271)),
            Depth(51.0, LatLng(42.392132, 18.565199)),
            Depth(51.0, LatLng(42.391817, 18.538366)),
            Depth(50.0, LatLng(42.427356, 18.602687)),
            Depth(50.0, LatLng(42.480309, 18.721071)),
            Depth(49.0, LatLng(42.402123, 18.544799)),
            Depth(49.0, LatLng(42.396913, 18.54999)),
            Depth(49.0, LatLng(42.392286, 18.558654)),
            Depth(49.0, LatLng(42.393395, 18.551064)),
            Depth(49.0, LatLng(42.401504, 18.539225)),
            Depth(48.0, LatLng(42.430529, 18.586212)),
            Depth(47.0, LatLng(42.395714, 18.553441)),
            Depth(47.0, LatLng(42.404697, 18.539542)),
            Depth(47.0, LatLng(42.402491, 18.536117)),
            Depth(46.0, LatLng(42.434268, 18.577347)),
            Depth(46.0, LatLng(42.431206, 18.540238)),
            Depth(46.0, LatLng(42.429377, 18.59671)),
            Depth(46.0, LatLng(42.402417, 18.540463)),
            Depth(46.0, LatLng(42.40473, 18.547986)),
            Depth(46.0, LatLng(42.389734, 18.558702)),
            Depth(46.0, LatLng(42.402478, 18.553002)),
            Depth(45.0, LatLng(42.434816, 18.569405)),
            Depth(45.0, LatLng(42.402581, 18.530267)),
            Depth(45.0, LatLng(42.433592, 18.544024)),
            Depth(44.0, LatLng(42.431938, 18.551712)),
            Depth(44.0, LatLng(42.47693, 18.687845)),
            Depth(44.0, LatLng(42.429714, 18.616682)),
            Depth(43.8, LatLng(42.409536, 18.53494)),
            Depth(43.0, LatLng(42.436652, 18.574854)),
            Depth(43.0, LatLng(42.437366, 18.545919)),
            Depth(43.0, LatLng(42.432907, 18.559528)),
            Depth(43.0, LatLng(42.434465, 18.555294)),
            Depth(43.0, LatLng(42.436516, 18.551774)),
            Depth(43.0, LatLng(42.434722, 18.538722)),
            Depth(43.0, LatLng(42.429309, 18.595217)),
            Depth(43.0, LatLng(42.394942, 18.538191)),
            Depth(43.0, LatLng(42.429021, 18.600147)),
            Depth(43.0, LatLng(42.405106, 18.552669)),
            Depth(42.0, LatLng(42.441012, 18.547871)),
            Depth(42.0, LatLng(42.439616, 18.559529)),
            Depth(42.0, LatLng(42.43914, 18.551032)),
            Depth(42.0, LatLng(42.437284, 18.565712)),
            Depth(42.0, LatLng(42.437144, 18.560873)),
            Depth(42.0, LatLng(42.428899, 18.544824)),
            Depth(42.0, LatLng(42.437737, 18.540476)),
            Depth(42.0, LatLng(42.425947, 18.628301)),
            Depth(42.0, LatLng(42.412654, 18.533741)),
            Depth(42.0, LatLng(42.480599, 18.691116)),
            Depth(42.0, LatLng(42.392499, 18.528651)),
            Depth(42.0, LatLng(42.41101, 18.538947)),
            Depth(42.0, LatLng(42.430693, 18.633918)),
            Depth(41.0, LatLng(42.440327, 18.543665)),
            Depth(41.0, LatLng(42.410032, 18.525011)),
            Depth(41.0, LatLng(42.426926, 18.638735)),
            Depth(41.0, LatLng(42.429416, 18.536586)),
            Depth(41.0, LatLng(42.40244, 18.555976)),
            Depth(41.0, LatLng(42.396487, 18.533886)),
            Depth(41.0, LatLng(42.401343, 18.525155)),
            Depth(41.0, LatLng(42.424667, 18.633765)),
            Depth(41.0, LatLng(42.479081, 18.689184)),
            Depth(41.0, LatLng(42.407818, 18.548664)),
            Depth(41.0, LatLng(42.430192, 18.641554)),
            Depth(41.0, LatLng(42.430018, 18.649058)),
            Depth(40.2, LatLng(42.408351, 18.53948)),
            Depth(40.0, LatLng(42.442444, 18.555515)),
            Depth(40.0, LatLng(42.408475, 18.530121)),
            Depth(40.0, LatLng(42.408039, 18.523221)),
            Depth(40.0, LatLng(42.416514, 18.538062)),
            Depth(40.0, LatLng(42.427485, 18.548947)),
            Depth(40.0, LatLng(42.436081, 18.581291)),
            Depth(40.0, LatLng(42.475594, 18.685329)),
            Depth(40.0, LatLng(42.432926, 18.617093)),
            Depth(40.0, LatLng(42.481378, 18.695568)),
            Depth(40.0, LatLng(42.484635, 18.695175)),
            Depth(40.0, LatLng(42.400173, 18.556478)),
            Depth(40.0, LatLng(42.413655, 18.497672)),
            Depth(40.0, LatLng(42.393512, 18.523796)),
            Depth(40.0, LatLng(42.39853, 18.513302)),
            Depth(40.0, LatLng(42.425817, 18.646468)),
            Depth(39.0, LatLng(42.422175, 18.645008)),
            Depth(39.0, LatLng(42.44127, 18.539602)),
            Depth(39.0, LatLng(42.443401, 18.551002)),
            Depth(39.0, LatLng(42.442565, 18.544357)),
            Depth(39.0, LatLng(42.416362, 18.530174)),
            Depth(39.0, LatLng(42.425746, 18.534218)),
            Depth(39.0, LatLng(42.428224, 18.623544)),
            Depth(39.0, LatLng(42.417853, 18.53557)),
            Depth(39.0, LatLng(42.384931, 18.576065)),
            Depth(39.0, LatLng(42.424602, 18.651822)),
            Depth(39.0, LatLng(42.411156, 18.52133)),
            Depth(39.0, LatLng(42.473059, 18.683724)),
            Depth(39.0, LatLng(42.473893, 18.685268)),
            Depth(39.0, LatLng(42.47833, 18.708815)),
            Depth(39.0, LatLng(42.435798, 18.646459)),
            Depth(39.0, LatLng(42.46224, 18.679132)),
            Depth(39.0, LatLng(42.422814, 18.637723)),
            Depth(39.0, LatLng(42.407714, 18.553424)),
            Depth(39.0, LatLng(42.436075, 18.660538)),
            Depth(38.0, LatLng(42.481726, 18.702304)),
            Depth(38.0, LatLng(42.49736, 18.682794)),
            Depth(38.0, LatLng(42.435834, 18.584888)),
            Depth(38.0, LatLng(42.440368, 18.535155)),
            Depth(38.0, LatLng(42.412594, 18.529178)),
            Depth(38.0, LatLng(42.421216, 18.534132)),
            Depth(38.0, LatLng(42.416186, 18.524732)),
            Depth(38.0, LatLng(42.441653, 18.565746)),
            Depth(38.0, LatLng(42.436978, 18.53347)),
            Depth(38.0, LatLng(42.439316, 18.569077)),
            Depth(38.0, LatLng(42.478314, 18.704005)),
            Depth(38.0, LatLng(42.407619, 18.518052)),
            Depth(38.0, LatLng(42.413616, 18.522061)),
            Depth(38.0, LatLng(42.434045, 18.566692)),
            Depth(38.0, LatLng(42.452786, 18.677049)),
            Depth(38.0, LatLng(42.455906, 18.678889)),
            Depth(38.0, LatLng(42.43743, 18.642705)),
            Depth(38.0, LatLng(42.439668, 18.645015)),
            Depth(38.0, LatLng(42.443238, 18.662685)),
            Depth(38.0, LatLng(42.444064, 18.669925)),
            Depth(38.0, LatLng(42.479443, 18.699183)),
            Depth(38.0, LatLng(42.427485, 18.659921)),
            Depth(38.0, LatLng(42.396944, 18.56822)),
            Depth(38.0, LatLng(42.432868, 18.574516)),
            Depth(38.0, LatLng(42.396941, 18.562757)),
            Depth(38.0, LatLng(42.395296, 18.519635)),
            Depth(38.0, LatLng(42.439919, 18.6698)),
            Depth(38.0, LatLng(42.440254, 18.657793)),
            Depth(38.0, LatLng(42.437652, 18.653827)),
            Depth(38.0, LatLng(42.468509, 18.680902)),
            Depth(37.0, LatLng(42.405152, 18.519275)),
            Depth(37.0, LatLng(42.418154, 18.541318)),
            Depth(37.0, LatLng(42.420426, 18.538084)),
            Depth(37.0, LatLng(42.430208, 18.532785)),
            Depth(37.0, LatLng(42.443715, 18.558636)),
            Depth(37.0, LatLng(42.405244, 18.516932)),
            Depth(37.0, LatLng(42.458188, 18.678433)),
            Depth(37.0, LatLng(42.449039, 18.676264)),
            Depth(37.0, LatLng(42.447297, 18.67128)),
            Depth(37.0, LatLng(42.450153, 18.670862)),
            Depth(37.0, LatLng(42.478367, 18.695914)),
            Depth(37.0, LatLng(42.482127, 18.68932)),
            Depth(37.0, LatLng(42.39554, 18.569442)),
            Depth(37.0, LatLng(42.392648, 18.567476)),
            Depth(37.0, LatLng(42.409177, 18.550755)),
            Depth(37.0, LatLng(42.401323, 18.560222)),
            Depth(37.0, LatLng(42.414833, 18.540951)),
            Depth(37.0, LatLng(42.44456, 18.553734)),
            Depth(37.0, LatLng(42.44246, 18.653504)),
            Depth(37.0, LatLng(42.476868, 18.715721)),
            Depth(37.0, LatLng(42.4643, 18.67866)),
            Depth(37.0, LatLng(42.439248, 18.664001)),
            Depth(37.0, LatLng(42.475774, 18.724768)),
            Depth(37.0, LatLng(42.471041, 18.683451)),
            Depth(37.0, LatLng(42.440414, 18.65101)),
            Depth(37.0, LatLng(42.435436, 18.670589)),
            Depth(37.0, LatLng(42.47761, 18.720929)),
            Depth(36.0, LatLng(42.426085, 18.604064)),
            Depth(36.0, LatLng(42.395035, 18.535068)),
            Depth(36.0, LatLng(42.418337, 18.5258)),
            Depth(36.0, LatLng(42.41913, 18.642558)),
            Depth(36.0, LatLng(42.4252, 18.536595)),
            Depth(36.0, LatLng(42.47612, 18.734172)),
            Depth(36.0, LatLng(42.474123, 18.719594)),
            Depth(36.0, LatLng(42.386565, 18.573274)),
            Depth(36.0, LatLng(42.478848, 18.712922)),
            Depth(36.0, LatLng(42.384604, 18.580868)),
            Depth(36.0, LatLng(42.475432, 18.713095)),
            Depth(36.0, LatLng(42.420553, 18.65356)),
            Depth(36.0, LatLng(42.453936, 18.673294)),
            Depth(36.0, LatLng(42.446411, 18.665009)),
            Depth(36.0, LatLng(42.480504, 18.740046)),
            Depth(36.0, LatLng(42.434187, 18.632696)),
            Depth(36.0, LatLng(42.479717, 18.71797)),
            Depth(36.0, LatLng(42.486247, 18.691998)),
            Depth(36.0, LatLng(42.483772, 18.686544)),
            Depth(36.0, LatLng(42.389021, 18.571604)),
            Depth(36.0, LatLng(42.444899, 18.56307)),
            Depth(36.0, LatLng(42.393432, 18.555521)),
            Depth(36.0, LatLng(42.44377, 18.56755)),
            Depth(36.0, LatLng(42.431902, 18.591874)),
            Depth(36.0, LatLng(42.422016, 18.676721)),
            Depth(36.0, LatLng(42.473099, 18.733348)),
            Depth(36.0, LatLng(42.437576, 18.673289)),
            Depth(35.0, LatLng(42.41661, 18.543356)),
            Depth(35.0, LatLng(42.441926, 18.571795)),
            Depth(35.0, LatLng(42.434584, 18.529231)),
            Depth(35.0, LatLng(42.442822, 18.645418)),
            Depth(35.0, LatLng(42.440207, 18.639597)),
            Depth(35.0, LatLng(42.474032, 18.727185)),
            Depth(35.0, LatLng(42.427937, 18.606261)),
            Depth(35.0, LatLng(42.430756, 18.612122)),
            Depth(35.0, LatLng(42.431939, 18.623448)),
            Depth(35.0, LatLng(42.443449, 18.675411)),
            Depth(35.0, LatLng(42.4787, 18.726099)),
            Depth(35.0, LatLng(42.486199, 18.682892)),
            Depth(35.0, LatLng(42.425239, 18.61637)),
            Depth(35.0, LatLng(42.400608, 18.564797)),
            Depth(35.0, LatLng(42.41307, 18.54347)),
            Depth(35.0, LatLng(42.410352, 18.545438)),
            Depth(35.0, LatLng(42.409722, 18.503034)),
            Depth(35.0, LatLng(42.472725, 18.736931)),
            Depth(35.0, LatLng(42.466223, 18.67729)),
            Depth(35.0, LatLng(42.466908, 18.682711)),
            Depth(34.0, LatLng(42.44485, 18.54847)),
            Depth(34.0, LatLng(42.421062, 18.530309)),
            Depth(34.0, LatLng(42.420668, 18.542236)),
            Depth(34.0, LatLng(42.409903, 18.515176)),
            Depth(34.0, LatLng(42.474337, 18.742533)),
            Depth(34.0, LatLng(42.474089, 18.747826)),
            Depth(34.0, LatLng(42.45677, 18.674982)),
            Depth(34.0, LatLng(42.480569, 18.74574)),
            Depth(34.0, LatLng(42.434305, 18.617189)),
            Depth(34.0, LatLng(42.489731, 18.689005)),
            Depth(34.0, LatLng(42.488688, 18.68431)),
            Depth(34.0, LatLng(42.47974, 18.687344)),
            Depth(34.0, LatLng(42.481691, 18.683181)),
            Depth(34.0, LatLng(42.483249, 18.698608)),
            Depth(34.0, LatLng(42.398995, 18.57)),
            Depth(34.0, LatLng(42.432458, 18.580824)),
            Depth(34.0, LatLng(42.40654, 18.543239)),
            Depth(34.0, LatLng(42.478404, 18.731879)),
            Depth(34.0, LatLng(42.44639, 18.559251)),
            Depth(34.0, LatLng(42.478125, 18.742555)),
            Depth(34.0, LatLng(42.472438, 18.729462)),
            Depth(34.0, LatLng(42.477238, 18.745949)),
            Depth(33.1, LatLng(42.406322, 18.556603)),
            Depth(33.0, LatLng(42.442629, 18.535469)),
            Depth(33.0, LatLng(42.419033, 18.545008)),
            Depth(33.0, LatLng(42.409153, 18.512771)),
            Depth(33.0, LatLng(42.428264, 18.594432)),
            Depth(33.0, LatLng(42.477199, 18.69475)),
            Depth(33.0, LatLng(42.477295, 18.753321)),
            Depth(33.0, LatLng(42.472796, 18.75123)),
            Depth(33.0, LatLng(42.478986, 18.749798)),
            Depth(33.0, LatLng(42.481958, 18.748168)),
            Depth(33.0, LatLng(42.487614, 18.68899)),
            Depth(33.0, LatLng(42.486092, 18.679188)),
            Depth(33.0, LatLng(42.483797, 18.690549)),
            Depth(33.0, LatLng(42.489838, 18.680327)),
            Depth(33.0, LatLng(42.482123, 18.710492)),
            Depth(33.0, LatLng(42.411739, 18.550121)),
            Depth(33.0, LatLng(42.470959, 18.74139)),
            Depth(33.0, LatLng(42.468402, 18.74239)),
            Depth(33.0, LatLng(42.470866, 18.745714)),
            Depth(32.0, LatLng(42.445061, 18.540864)),
            Depth(32.0, LatLng(42.420402, 18.544623)),
            Depth(32.0, LatLng(42.414818, 18.515342)),
            Depth(32.0, LatLng(42.416407, 18.546144)),
            Depth(32.0, LatLng(42.444547, 18.571902)),
            Depth(32.0, LatLng(42.429266, 18.55274)),
            Depth(32.0, LatLng(42.440349, 18.577229)),
            Depth(32.0, LatLng(42.437777, 18.584228)),
            Depth(32.0, LatLng(42.490055, 18.669133)),
            Depth(32.0, LatLng(42.418142, 18.51948)),
            Depth(32.0, LatLng(42.416284, 18.516914)),
            Depth(32.0, LatLng(42.458452, 18.680079)),
            Depth(32.0, LatLng(42.419548, 18.663908)),
            Depth(32.0, LatLng(42.41577, 18.647034)),
            Depth(32.0, LatLng(42.445743, 18.654833)),
            Depth(32.0, LatLng(42.445271, 18.649208)),
            Depth(32.0, LatLng(42.452094, 18.681244)),
            Depth(32.0, LatLng(42.480922, 18.733541)),
            Depth(32.0, LatLng(42.446231, 18.677997)),
            Depth(32.0, LatLng(42.46921, 18.754888)),
            Depth(32.0, LatLng(42.45155, 18.667161)),
            Depth(32.0, LatLng(42.465644, 18.7498)),
            Depth(32.0, LatLng(42.469109, 18.733221)),
            Depth(32.0, LatLng(42.467889, 18.73854)),
            Depth(32.0, LatLng(42.49296, 18.671029)),
            Depth(32.0, LatLng(42.483444, 18.680059)),
            Depth(32.0, LatLng(42.491992, 18.681053)),
            Depth(32.0, LatLng(42.495183, 18.667498)),
            Depth(32.0, LatLng(42.480679, 18.685359)),
            Depth(32.0, LatLng(42.496858, 18.671074)),
            Depth(32.0, LatLng(42.499178, 18.668704)),
            Depth(32.0, LatLng(42.494684, 18.680923)),
            Depth(32.0, LatLng(42.398291, 18.573795)),
            Depth(32.0, LatLng(42.401426, 18.568576)),
            Depth(32.0, LatLng(42.439641, 18.677398)),
            Depth(32.0, LatLng(42.468272, 18.678269)),
            Depth(32.0, LatLng(42.428413, 18.674409)),
            Depth(32.0, LatLng(42.483335, 18.746237)),
            Depth(32.0, LatLng(42.434141, 18.675091)),
            Depth(31.0, LatLng(42.482769, 18.717842)),
            Depth(31.0, LatLng(42.482077, 18.729635)),
            Depth(31.0, LatLng(42.419503, 18.523232)),
            Depth(31.0, LatLng(42.421255, 18.528167)),
            Depth(31.0, LatLng(42.423636, 18.54148)),
            Depth(31.0, LatLng(42.424848, 18.531184)),
            Depth(31.0, LatLng(42.430932, 18.556339)),
            Depth(31.0, LatLng(42.439497, 18.529433)),
            Depth(31.0, LatLng(42.50473, 18.677722)),
            Depth(31.0, LatLng(42.434001, 18.584391)),
            Depth(31.0, LatLng(42.469765, 18.729119)),
            Depth(31.0, LatLng(42.41631, 18.639823)),
            Depth(31.0, LatLng(42.482985, 18.756349)),
            Depth(31.0, LatLng(42.482113, 18.73767)),
            Depth(31.0, LatLng(42.449205, 18.662577)),
            Depth(31.0, LatLng(42.464485, 18.745734)),
            Depth(31.0, LatLng(42.434007, 18.623383)),
            Depth(31.0, LatLng(42.483332, 18.743718)),
            Depth(31.0, LatLng(42.463108, 18.753649)),
            Depth(31.0, LatLng(42.500997, 18.67062)),
            Depth(31.0, LatLng(42.488188, 18.675757)),
            Depth(31.0, LatLng(42.49845, 18.673309)),
            Depth(31.0, LatLng(42.491489, 18.675778)),
            Depth(31.0, LatLng(42.492591, 18.687422)),
            Depth(31.0, LatLng(42.396255, 18.572195)),
            Depth(31.0, LatLng(42.430639, 18.677241)),
            Depth(31.0, LatLng(42.424526, 18.674034)),
            Depth(30.3, LatLng(42.431202, 18.527764)),
            Depth(30.0, LatLng(42.427931, 18.596328)),
            Depth(30.0, LatLng(42.446558, 18.552293)),
            Depth(30.0, LatLng(42.425107, 18.543647)),
            Depth(30.0, LatLng(42.421187, 18.631257)),
            Depth(30.0, LatLng(42.433626, 18.527371)),
            Depth(30.0, LatLng(42.5089, 18.680741)),
            Depth(30.0, LatLng(42.412914, 18.512012)),
            Depth(30.0, LatLng(42.415817, 18.660318)),
            Depth(30.0, LatLng(42.416169, 18.665536)),
            Depth(30.0, LatLng(42.461224, 18.746108)),
            Depth(30.0, LatLng(42.434816, 18.628213)),
            Depth(30.0, LatLng(42.439878, 18.636072)),
            Depth(30.0, LatLng(42.462832, 18.68114)),
            Depth(30.0, LatLng(42.470695, 18.724054)),
            Depth(30.0, LatLng(42.495426, 18.68559)),
            Depth(30.0, LatLng(42.485819, 18.675592)),
            Depth(30.0, LatLng(42.494654, 18.675631)),
            Depth(30.0, LatLng(42.428377, 18.591253)),
            Depth(30.0, LatLng(42.4463, 18.566759)),
            Depth(30.0, LatLng(42.403173, 18.506592)),
            Depth(30.0, LatLng(42.474571, 18.709786)),
            Depth(30.0, LatLng(42.473581, 18.715599)),
            Depth(30.0, LatLng(42.425626, 18.677806)),
            Depth(30.0, LatLng(42.485447, 18.751485)),
            Depth(29.0, LatLng(42.435876, 18.586923)),
            Depth(29.0, LatLng(42.426021, 18.530495)),
            Depth(29.0, LatLng(42.412436, 18.65066)),
            Depth(29.0, LatLng(42.412612, 18.660887)),
            Depth(29.0, LatLng(42.413892, 18.663806)),
            Depth(29.0, LatLng(42.448714, 18.681072)),
            Depth(29.0, LatLng(42.418599, 18.657784)),
            Depth(29.0, LatLng(42.444526, 18.645361)),
            Depth(29.0, LatLng(42.466934, 18.75814)),
            Depth(29.0, LatLng(42.491698, 18.689807)),
            Depth(29.0, LatLng(42.497447, 18.678136)),
            Depth(29.0, LatLng(42.400148, 18.573509)),
            Depth(29.0, LatLng(42.433854, 18.591759)),
            Depth(29.0, LatLng(42.429074, 18.588765)),
            Depth(29.0, LatLng(42.423971, 18.622325)),
            Depth(29.0, LatLng(42.430492, 18.58325)),
            Depth(29.0, LatLng(42.460136, 18.755043)),
            Depth(29.0, LatLng(42.458079, 18.751531)),
            Depth(28.0, LatLng(42.497632, 18.686966)),
            Depth(28.0, LatLng(42.499632, 18.684962)),
            Depth(28.0, LatLng(42.500962, 18.681261)),
            Depth(28.0, LatLng(42.426361, 18.619545)),
            Depth(28.0, LatLng(42.421904, 18.626243)),
            Depth(28.0, LatLng(42.422653, 18.537857)),
            Depth(28.0, LatLng(42.448017, 18.563217)),
            Depth(28.0, LatLng(42.415364, 18.51321)),
            Depth(28.0, LatLng(42.461796, 18.742706)),
            Depth(28.0, LatLng(42.481791, 18.760508)),
            Depth(28.0, LatLng(42.443123, 18.641176)),
            Depth(28.0, LatLng(42.432463, 18.610088)),
            Depth(28.0, LatLng(42.461763, 18.757962)),
            Depth(28.0, LatLng(42.489728, 18.691546)),
            Depth(28.0, LatLng(42.483188, 18.695513)),
            Depth(28.0, LatLng(42.491751, 18.665296)),
            Depth(28.0, LatLng(42.393647, 18.557584)),
            Depth(28.0, LatLng(42.3931, 18.562483)),
            Depth(28.0, LatLng(42.48603, 18.746673)),
            Depth(28.0, LatLng(42.426803, 18.681484)),
            Depth(28.0, LatLng(42.456973, 18.749678)),
            Depth(28.0, LatLng(42.435238, 18.680557)),
            Depth(28.0, LatLng(42.454763, 18.756392)),
            Depth(28.0, LatLng(42.423541, 18.681352)),
            Depth(28.0, LatLng(42.418749, 18.675104)),
            Depth(27.0, LatLng(42.413154, 18.656876)),
            Depth(27.0, LatLng(42.484797, 18.760829)),
            Depth(27.0, LatLng(42.410194, 18.662324)),
            Depth(27.0, LatLng(42.477836, 18.687489)),
            Depth(27.0, LatLng(42.47555, 18.687338)),
            Depth(27.0, LatLng(42.471137, 18.760702)),
            Depth(27.0, LatLng(42.463953, 18.758428)),
            Depth(27.0, LatLng(42.495177, 18.688858)),
            Depth(27.0, LatLng(42.474298, 18.706831)),
            Depth(27.0, LatLng(42.493452, 18.69015)),
            Depth(27.0, LatLng(42.432354, 18.577957)),
            Depth(27.0, LatLng(42.404676, 18.505715)),
            Depth(27.0, LatLng(42.463185, 18.675522)),
            Depth(27.0, LatLng(42.450415, 18.756066)),
            Depth(27.0, LatLng(42.459338, 18.759298)),
            Depth(27.0, LatLng(42.471669, 18.720081)),
            Depth(27.0, LatLng(42.441792, 18.679427)),
            Depth(26.0, LatLng(42.483023, 18.712555)),
            Depth(26.0, LatLng(42.41759, 18.546585)),
            Depth(26.0, LatLng(42.436206, 18.524238)),
            Depth(26.0, LatLng(42.504077, 18.682269)),
            Depth(26.0, LatLng(42.3943, 18.534256)),
            Depth(26.0, LatLng(42.415702, 18.655083)),
            Depth(26.0, LatLng(42.41048, 18.657432)),
            Depth(26.0, LatLng(42.409434, 18.665645)),
            Depth(26.0, LatLng(42.449004, 18.75841)),
            Depth(26.0, LatLng(42.499154, 18.664637)),
            Depth(26.0, LatLng(42.424702, 18.610331)),
            Depth(26.0, LatLng(42.412561, 18.546802)),
            Depth(26.0, LatLng(42.425717, 18.68576)),
            Depth(26.0, LatLng(42.422529, 18.684363)),
            Depth(26.0, LatLng(42.412814, 18.669601)),
            Depth(26.0, LatLng(42.418802, 18.680947)),
            Depth(25.4, LatLng(42.418197, 18.515009)),
            Depth(25.4, LatLng(42.442216, 18.529075)),
            Depth(25.0, LatLng(42.499691, 18.689586)),
            Depth(25.0, LatLng(42.448495, 18.56727)),
            Depth(25.0, LatLng(42.427936, 18.528537)),
            Depth(25.0, LatLng(42.424102, 18.547916)),
            Depth(25.0, LatLng(42.423221, 18.615965)),
            Depth(25.0, LatLng(42.446608, 18.760013)),
            Depth(25.0, LatLng(42.447622, 18.651429)),
            Depth(25.0, LatLng(42.4481, 18.659439)),
            Depth(25.0, LatLng(42.430152, 18.608543)),
            Depth(25.0, LatLng(42.436248, 18.619502)),
            Depth(25.0, LatLng(42.483934, 18.67705)),
            Depth(25.0, LatLng(42.484104, 18.700904)),
            Depth(25.0, LatLng(42.481987, 18.697306)),
            Depth(25.0, LatLng(42.4901, 18.662729)),
            Depth(25.0, LatLng(42.50095, 18.688118)),
            Depth(25.0, LatLng(42.42915, 18.683947)),
            Depth(25.0, LatLng(42.48685, 18.758)),
            Depth(25.0, LatLng(42.408469, 18.670617)),
            Depth(25.0, LatLng(42.410866, 18.671679)),
            Depth(24.0, LatLng(42.444316, 18.531568)),
            Depth(24.0, LatLng(42.446042, 18.535338)),
            Depth(24.0, LatLng(42.447122, 18.570304)),
            Depth(24.0, LatLng(42.432772, 18.56374)),
            Depth(24.0, LatLng(42.423788, 18.53686)),
            Depth(24.0, LatLng(42.429384, 18.526959)),
            Depth(24.0, LatLng(42.442007, 18.577518)),
            Depth(24.0, LatLng(42.440319, 18.524256)),
            Depth(24.0, LatLng(42.437796, 18.757795)),
            Depth(24.0, LatLng(42.47622, 18.760883)),
            Depth(24.0, LatLng(42.4331, 18.569108)),
            Depth(24.0, LatLng(42.418761, 18.636033)),
            Depth(24.0, LatLng(42.506869, 18.681259)),
            Depth(24.0, LatLng(42.477598, 18.68899)),
            Depth(24.0, LatLng(42.483354, 18.706766)),
            Depth(24.0, LatLng(42.435921, 18.624625)),
            Depth(24.0, LatLng(42.47209, 18.685164)),
            Depth(24.0, LatLng(42.483065, 18.723424)),
            Depth(24.0, LatLng(42.486742, 18.695632)),
            Depth(24.0, LatLng(42.487481, 18.667349)),
            Depth(24.0, LatLng(42.484918, 18.698379)),
            Depth(24.0, LatLng(42.485536, 18.672388)),
            Depth(24.0, LatLng(42.483311, 18.703857)),
            Depth(24.0, LatLng(42.409499, 18.554424)),
            Depth(24.0, LatLng(42.406449, 18.505641)),
            Depth(24.0, LatLng(42.483208, 18.727034)),
            Depth(24.0, LatLng(42.454811, 18.761452)),
            Depth(24.0, LatLng(42.407645, 18.676031)),
            Depth(24.0, LatLng(42.418834, 18.6859)),
            Depth(24.0, LatLng(42.413732, 18.673229)),
            Depth(23.0, LatLng(42.441116, 18.758975)),
            Depth(23.0, LatLng(42.426581, 18.600745)),
            Depth(23.0, LatLng(42.42824, 18.617384)),
            Depth(23.0, LatLng(42.457736, 18.67246)),
            Depth(23.0, LatLng(42.451361, 18.683014)),
            Depth(23.0, LatLng(42.485331, 18.763515)),
            Depth(23.0, LatLng(42.395436, 18.575312)),
            Depth(23.0, LatLng(42.394872, 18.573377)),
            Depth(23.0, LatLng(42.46674, 18.684921)),
            Depth(23.0, LatLng(42.486828, 18.762292)),
            Depth(23.0, LatLng(42.470267, 18.681351)),
            Depth(23.0, LatLng(42.457318, 18.746684)),
            Depth(23.0, LatLng(42.426407, 18.688854)),
            Depth(23.0, LatLng(42.46691, 18.734052)),
            Depth(23.0, LatLng(42.431954, 18.681465)),
            Depth(23.0, LatLng(42.422423, 18.689304)),
            Depth(23.0, LatLng(42.416324, 18.671429)),
            Depth(22.2, LatLng(42.41985, 18.51798)),
            Depth(22.0, LatLng(42.427684, 18.55522)),
            Depth(22.0, LatLng(42.427102, 18.553427)),
            Depth(22.0, LatLng(42.436391, 18.759507)),
            Depth(22.0, LatLng(42.438992, 18.7591)),
            Depth(22.0, LatLng(42.468776, 18.760645)),
            Depth(22.0, LatLng(42.447421, 18.656169)),
            Depth(22.0, LatLng(42.433931, 18.612533)),
            Depth(22.0, LatLng(42.446632, 18.756303)),
            Depth(22.0, LatLng(42.4941, 18.663281)),
            Depth(22.0, LatLng(42.501022, 18.666457)),
            Depth(22.0, LatLng(42.48795, 18.663905)),
            Depth(22.0, LatLng(42.458003, 18.762045)),
            Depth(22.0, LatLng(42.424214, 18.691422)),
            Depth(22.0, LatLng(42.407768, 18.667556)),
            Depth(22.0, LatLng(42.407835, 18.679736)),
            Depth(22.0, LatLng(42.416506, 18.689028)),
            Depth(21.0, LatLng(42.491128, 18.660204)),
            Depth(21.0, LatLng(42.464242, 18.739029)),
            Depth(21.0, LatLng(42.419597, 18.515514)),
            Depth(21.0, LatLng(42.450087, 18.560356)),
            Depth(21.0, LatLng(42.401272, 18.521134)),
            Depth(21.0, LatLng(42.502996, 18.676724)),
            Depth(21.0, LatLng(42.445881, 18.755247)),
            Depth(21.0, LatLng(42.413001, 18.644839)),
            Depth(21.0, LatLng(42.461166, 18.740911)),
            Depth(21.0, LatLng(42.456097, 18.668104)),
            Depth(21.0, LatLng(42.442539, 18.638712)),
            Depth(21.0, LatLng(42.460287, 18.762003)),
            Depth(21.0, LatLng(42.505362, 18.686666)),
            Depth(21.0, LatLng(42.486297, 18.689674)),
            Depth(21.0, LatLng(42.395945, 18.5609)),
            Depth(21.0, LatLng(42.421605, 18.622191)),
            Depth(21.0, LatLng(42.411639, 18.554344)),
            Depth(21.0, LatLng(42.448598, 18.555394)),
            Depth(21.0, LatLng(42.484694, 18.7446)),
            Depth(21.0, LatLng(42.469488, 18.679207)),
            Depth(21.0, LatLng(42.470893, 18.685459)),
            Depth(21.0, LatLng(42.458665, 18.74443)),
            Depth(21.0, LatLng(42.409505, 18.677129)),
            Depth(21.0, LatLng(42.406455, 18.684775)),
            Depth(21.0, LatLng(42.406065, 18.674597)),
            Depth(20.5, LatLng(42.432742, 18.522729)),
            Depth(20.3, LatLng(42.397457, 18.575476)),
            Depth(20.3, LatLng(42.43113, 18.761335)),
            Depth(20.2, LatLng(42.488083, 18.755605)),
            Depth(20.2, LatLng(42.397057, 18.559769)),
            Depth(20.1, LatLng(42.410657, 18.653065)),
            Depth(20.1, LatLng(42.423788, 18.619643)),
            Depth(20.1, LatLng(42.448996, 18.56918)),
            Depth(20.1, LatLng(42.395142, 18.532)),
            Depth(19.9, LatLng(42.433343, 18.76256)),
            Depth(19.9, LatLng(42.454785, 18.749722)),
            Depth(19.8, LatLng(42.429063, 18.760115)),
            Depth(19.8, LatLng(42.430199, 18.524546)),
            Depth(19.4, LatLng(42.427397, 18.530116)),
            Depth(19.2, LatLng(42.433614, 18.524361)),
            Depth(19.0, LatLng(42.426846, 18.6914)),
            Depth(19.0, LatLng(42.434156, 18.684257)),
            Depth(19.0, LatLng(42.437066, 18.682876)),
            Depth(19.0, LatLng(42.446809, 18.683906)),
            Depth(19.0, LatLng(42.429214, 18.586753)),
            Depth(19.0, LatLng(42.420107, 18.624608)),
            Depth(19.0, LatLng(42.409435, 18.658485)),
            Depth(19.0, LatLng(42.445283, 18.761268)),
            Depth(19.0, LatLng(42.428469, 18.762909)),
            Depth(19.0, LatLng(42.440274, 18.755543)),
            Depth(19.0, LatLng(42.465114, 18.674566)),
            Depth(19.0, LatLng(42.462072, 18.674296)),
            Depth(19.0, LatLng(42.499847, 18.662406)),
            Depth(19.0, LatLng(42.50804, 18.685445)),
            Depth(19.0, LatLng(42.510571, 18.684844)),
            Depth(19.0, LatLng(42.506185, 18.691422)),
            Depth(19.0, LatLng(42.397678, 18.525832)),
            Depth(19.0, LatLng(42.447682, 18.534432)),
            Depth(19.0, LatLng(42.443696, 18.524792)),
            Depth(19.0, LatLng(42.445856, 18.574336)),
            Depth(19.0, LatLng(42.450554, 18.565946)),
            Depth(19.0, LatLng(42.454378, 18.667695)),
            Depth(19.0, LatLng(42.405805, 18.682322)),
            Depth(19.0, LatLng(42.40614, 18.69055)),
            Depth(19.0, LatLng(42.416489, 18.694369)),
            Depth(18.0, LatLng(42.430846, 18.687419)),
            Depth(18.0, LatLng(42.472511, 18.715403)),
            Depth(18.0, LatLng(42.421618, 18.619242)),
            Depth(18.0, LatLng(42.423145, 18.545738)),
            Depth(18.0, LatLng(42.450856, 18.763518)),
            Depth(18.0, LatLng(42.435269, 18.76211)),
            Depth(18.0, LatLng(42.490073, 18.656183)),
            Depth(18.0, LatLng(42.483483, 18.720332)),
            Depth(18.0, LatLng(42.511124, 18.682161)),
            Depth(18.0, LatLng(42.420875, 18.525676)),
            Depth(18.0, LatLng(42.440692, 18.520238)),
            Depth(18.0, LatLng(42.441889, 18.635089)),
            Depth(18.0, LatLng(42.409889, 18.68145)),
            Depth(18.0, LatLng(42.419542, 18.690138)),
            Depth(18.0, LatLng(42.412323, 18.679122)),
            Depth(17.2, LatLng(42.392751, 18.56958)),
            Depth(17.0, LatLng(42.491985, 18.654928)),
            Depth(17.0, LatLng(42.425891, 18.693317)),
            Depth(17.0, LatLng(42.473319, 18.681871)),
            Depth(17.0, LatLng(42.424275, 18.695567)),
            Depth(17.0, LatLng(42.469415, 18.725374)),
            Depth(17.0, LatLng(42.488474, 18.762835)),
            Depth(17.0, LatLng(42.47532, 18.701368)),
            Depth(17.0, LatLng(42.451086, 18.751968)),
            Depth(17.0, LatLng(42.456108, 18.76338)),
            Depth(17.0, LatLng(42.473951, 18.763113)),
            Depth(17.0, LatLng(42.483572, 18.739328)),
            Depth(17.0, LatLng(42.422612, 18.527288)),
            Depth(17.0, LatLng(42.446359, 18.52736)),
            Depth(17.0, LatLng(42.447952, 18.551402)),
            Depth(17.0, LatLng(42.437442, 18.619486)),
            Depth(17.0, LatLng(42.445723, 18.643776)),
            Depth(17.0, LatLng(42.447206, 18.646598)),
            Depth(17.0, LatLng(42.485557, 18.68793)),
            Depth(17.0, LatLng(42.470022, 18.761902)),
            Depth(17.0, LatLng(42.41356, 18.698797)),
            Depth(17.0, LatLng(42.42043, 18.697155)),
            Depth(17.0, LatLng(42.412816, 18.694927)),
            Depth(17.0, LatLng(42.420674, 18.693856)),
            Depth(16.9, LatLng(42.434438, 18.521234)),
            Depth(16.2, LatLng(42.406088, 18.671392)),
            Depth(16.0, LatLng(42.432813, 18.685659)),
            Depth(16.0, LatLng(42.436124, 18.687398)),
            Depth(16.0, LatLng(42.422314, 18.697768)),
            Depth(16.0, LatLng(42.429791, 18.689278)),
            Depth(16.0, LatLng(42.487922, 18.760934)),
            Depth(16.0, LatLng(42.472678, 18.705726)),
            Depth(16.0, LatLng(42.40126, 18.571837)),
            Depth(16.0, LatLng(42.43169, 18.560848)),
            Depth(16.0, LatLng(42.466493, 18.73127)),
            Depth(16.0, LatLng(42.416482, 18.632628)),
            Depth(16.0, LatLng(42.429713, 18.765736)),
            Depth(16.0, LatLng(42.482823, 18.734954)),
            Depth(16.0, LatLng(42.407287, 18.506326)),
            Depth(16.0, LatLng(42.402735, 18.516918)),
            Depth(16.0, LatLng(42.451722, 18.568391)),
            Depth(16.0, LatLng(42.452368, 18.563378)),
            Depth(16.0, LatLng(42.438442, 18.519461)),
            Depth(16.0, LatLng(42.438695, 18.629645)),
            Depth(16.0, LatLng(42.443027, 18.57915)),
            Depth(16.0, LatLng(42.451206, 18.66155)),
            Depth(16.0, LatLng(42.4732, 18.713016)),
            Depth(16.0, LatLng(42.404346, 18.694956)),
            Depth(16.0, LatLng(42.405811, 18.693222)),
            Depth(16.0, LatLng(42.404812, 18.686237)),
            Depth(16.0, LatLng(42.416719, 18.678269)),
            Depth(16.0, LatLng(42.416087, 18.700084)),
            Depth(15.6, LatLng(42.466455, 18.675585)),
            Depth(15.6, LatLng(42.437536, 18.586746)),
            Depth(15.1, LatLng(42.449193, 18.531316)),
            Depth(15.0, LatLng(42.488438, 18.656632)),
            Depth(15.0, LatLng(42.466695, 18.761392)),
            Depth(15.0, LatLng(42.449033, 18.755182)),
            Depth(15.0, LatLng(42.414604, 18.638276)),
            Depth(15.0, LatLng(42.470782, 18.717537)),
            Depth(15.0, LatLng(42.407697, 18.664373)),
            Depth(15.0, LatLng(42.431283, 18.557794)),
            Depth(15.0, LatLng(42.481532, 18.762436)),
            Depth(15.0, LatLng(42.442091, 18.760334)),
            Depth(15.0, LatLng(42.432256, 18.765057)),
            Depth(15.0, LatLng(42.432266, 18.759226)),
            Depth(15.0, LatLng(42.434674, 18.764013)),
            Depth(15.0, LatLng(42.496592, 18.690047)),
            Depth(15.0, LatLng(42.494472, 18.658978)),
            Depth(15.0, LatLng(42.513585, 18.68492)),
            Depth(15.0, LatLng(42.503221, 18.692295)),
            Depth(15.0, LatLng(42.512047, 18.691053)),
            Depth(15.0, LatLng(42.422782, 18.529051)),
            Depth(15.0, LatLng(42.446904, 18.543991)),
            Depth(15.0, LatLng(42.432757, 18.606274)),
            Depth(15.0, LatLng(42.439848, 18.632275)),
            Depth(15.0, LatLng(42.444224, 18.753553)),
            Depth(15.0, LatLng(42.488042, 18.687217)),
            Depth(15.0, LatLng(42.418758, 18.695119)),
            Depth(15.0, LatLng(42.414656, 18.689329)),
            Depth(15.0, LatLng(42.403669, 18.691505)),
            Depth(15.0, LatLng(42.396563, 18.556913)),
            Depth(15.0, LatLng(42.401124, 18.69697)),
            Depth(14.2, LatLng(42.413704, 18.693154)),
            Depth(14.0, LatLng(42.45522, 18.664192)),
            Depth(14.0, LatLng(42.443374, 18.683749)),
            Depth(14.0, LatLng(42.447943, 18.685195)),
            Depth(14.0, LatLng(42.472767, 18.707401)),
            Depth(14.0, LatLng(42.488943, 18.753617)),
            Depth(14.0, LatLng(42.425865, 18.696321)),
            Depth(14.0, LatLng(42.398933, 18.576568)),
            Depth(14.0, LatLng(42.411617, 18.556696)),
            Depth(14.0, LatLng(42.425918, 18.550387)),
            Depth(14.0, LatLng(42.426583, 18.765609)),
            Depth(14.0, LatLng(42.425312, 18.761652)),
            Depth(14.0, LatLng(42.493305, 18.654798)),
            Depth(14.0, LatLng(42.49304, 18.65797)),
            Depth(14.0, LatLng(42.508331, 18.693982)),
            Depth(14.0, LatLng(42.495746, 18.66061)),
            Depth(14.0, LatLng(42.445937, 18.520906)),
            Depth(14.0, LatLng(42.4497, 18.530236)),
            Depth(14.0, LatLng(42.449162, 18.650725)),
            Depth(14.0, LatLng(42.435858, 18.613211)),
            Depth(14.0, LatLng(42.441862, 18.580878)),
            Depth(14.0, LatLng(42.437496, 18.614928)),
            Depth(14.0, LatLng(42.424108, 18.766153)),
            Depth(14.0, LatLng(42.470114, 18.722102)),
            Depth(14.0, LatLng(42.417655, 18.697849)),
            Depth(14.0, LatLng(42.417444, 18.701597)),
            Depth(14.0, LatLng(42.413605, 18.703019)),
            Depth(14.0, LatLng(42.393147, 18.561077)),
            Depth(14.0, LatLng(42.414186, 18.679142)),
            Depth(14.0, LatLng(42.411461, 18.700162)),
            Depth(14.0, LatLng(42.404375, 18.697279)),
            Depth(14.0, LatLng(42.406404, 18.669652)),
            Depth(13.2, LatLng(42.411471, 18.697237)),
            Depth(13.0, LatLng(42.399038, 18.698824)),
            Depth(13.0, LatLng(42.445604, 18.684571)),
            Depth(13.0, LatLng(42.438141, 18.683735)),
            Depth(13.0, LatLng(42.42736, 18.760212)),
            Depth(13.0, LatLng(42.455178, 18.76441)),
            Depth(13.0, LatLng(42.492315, 18.652688)),
            Depth(13.0, LatLng(42.439738, 18.517054)),
            Depth(13.0, LatLng(42.449398, 18.527763)),
            Depth(13.0, LatLng(42.442725, 18.518323)),
            Depth(13.0, LatLng(42.448223, 18.524411)),
            Depth(13.0, LatLng(42.433855, 18.603883)),
            Depth(13.0, LatLng(42.431695, 18.600664)),
            Depth(13.0, LatLng(42.453325, 18.663083)),
            Depth(13.0, LatLng(42.479366, 18.761639)),
            Depth(13.0, LatLng(42.415285, 18.704142)),
            Depth(13.0, LatLng(42.411112, 18.704913)),
            Depth(13.0, LatLng(42.441021, 18.683331)),
            Depth(13.0, LatLng(42.417438, 18.703946)),
            Depth(13.0, LatLng(42.406704, 18.673272)),
            Depth(13.0, LatLng(42.416362, 18.681058)),
            Depth(12.4, LatLng(42.419991, 18.520528)),
            Depth(12.2, LatLng(42.407978, 18.541739)),
            Depth(12.0, LatLng(42.47419, 18.703659)),
            Depth(12.0, LatLng(42.488458, 18.654867)),
            Depth(12.0, LatLng(42.430998, 18.689663)),
            Depth(12.0, LatLng(42.434123, 18.688189)),
            Depth(12.0, LatLng(42.428012, 18.693879)),
            Depth(12.0, LatLng(42.400052, 18.5764)),
            Depth(12.0, LatLng(42.464537, 18.761573)),
            Depth(12.0, LatLng(42.504408, 18.671981)),
            Depth(12.0, LatLng(42.515793, 18.688084)),
            Depth(12.0, LatLng(42.447442, 18.548528)),
            Depth(12.0, LatLng(42.432735, 18.604257)),
            Depth(12.0, LatLng(42.433952, 18.608682)),
            Depth(12.0, LatLng(42.439505, 18.583509)),
            Depth(12.0, LatLng(42.450712, 18.658152)),
            Depth(12.0, LatLng(42.393991, 18.570891)),
            Depth(12.0, LatLng(42.393855, 18.56099)),
            Depth(12.0, LatLng(42.408856, 18.687007)),
            Depth(12.0, LatLng(42.410052, 18.700886)),
            Depth(12.0, LatLng(42.409442, 18.703436)),
            Depth(12.0, LatLng(42.413676, 18.684176)),
            Depth(11.8, LatLng(42.495412, 18.657286)),
            Depth(11.8, LatLng(42.489176, 18.653848)),
            Depth(11.7, LatLng(42.444598, 18.516425)),
            Depth(11.0, LatLng(42.437233, 18.685586)),
            Depth(11.0, LatLng(42.386147, 18.576072)),
            Depth(11.0, LatLng(42.489456, 18.65157)),
            Depth(11.0, LatLng(42.450991, 18.527967)),
            Depth(11.0, LatLng(42.447332, 18.573751)),
            Depth(11.0, LatLng(42.420293, 18.700692)),
            Depth(11.0, LatLng(42.410377, 18.702707)),
            Depth(11.0, LatLng(42.40869, 18.691256)),
            Depth(11.0, LatLng(42.403113, 18.700488)),
            Depth(10.6, LatLng(42.484593, 18.764123)),
            Depth(10.6, LatLng(42.438581, 18.515751)),
            Depth(10.5, LatLng(42.452135, 18.560256)),
            Depth(9.9, LatLng(42.402863, 18.562319)),
            Depth(9.9, LatLng(42.449843, 18.519641)),
            Depth(9.5, LatLng(42.452331, 18.525639)),
            Depth(9.3, LatLng(42.448287, 18.516315)),
            Depth(9.2, LatLng(42.444849, 18.513229)),
            Depth(9.0, LatLng(42.44782, 18.538898)),
            Depth(9.0, LatLng(42.428635, 18.695924)),
            Depth(9.0, LatLng(42.395603, 18.700716)),
            Depth(9.0, LatLng(42.404987, 18.700639)),
            Depth(8.9, LatLng(42.441632, 18.51441)),
            Depth(8.8, LatLng(42.453458, 18.562989)),
            Depth(8.8, LatLng(42.42391, 18.769058)),
            Depth(8.7, LatLng(42.39727, 18.699999)),
            Depth(8.7, LatLng(42.443029, 18.685711)),
            Depth(8.7, LatLng(42.399861, 18.703793)),
            Depth(8.5, LatLng(42.494495, 18.653609)),
            Depth(8.5, LatLng(42.51672, 18.689023)),
            Depth(8.5, LatLng(42.485253, 18.691373)),
            Depth(8.5, LatLng(42.413494, 18.706536)),
            Depth(8.3, LatLng(42.452342, 18.521475)),
            Depth(8.3, LatLng(42.464133, 18.736422)),
            Depth(8.2, LatLng(42.43844, 18.685806)),
            Depth(8.1, LatLng(42.448122, 18.544694)),
            Depth(8.1, LatLng(42.424329, 18.699811)),
            Depth(8.0, LatLng(42.410272, 18.708567)),
            Depth(7.9, LatLng(42.438828, 18.623612)),
            Depth(7.9, LatLng(42.427894, 18.697573)),
            Depth(7.9, LatLng(42.422874, 18.763043)),
            Depth(7.9, LatLng(42.514879, 18.690436)),
            Depth(7.8, LatLng(42.421397, 18.702282)),
            Depth(7.8, LatLng(42.399552, 18.578264)),
            Depth(7.8, LatLng(42.409777, 18.699061)),
            Depth(7.7, LatLng(42.49442, 18.656412)),
            Depth(7.7, LatLng(42.404126, 18.55983)),
            Depth(7.7, LatLng(42.40825, 18.705422)),
            Depth(7.6, LatLng(42.450536, 18.531099)),
            Depth(7.6, LatLng(42.397936, 18.703939)),
            Depth(7.6, LatLng(42.444677, 18.763061)),
            Depth(7.6, LatLng(42.432752, 18.520223)),
            Depth(7.5, LatLng(42.490263, 18.650869)),
            Depth(7.4, LatLng(42.451071, 18.558598)),
            Depth(7.4, LatLng(42.394555, 18.701926)),
            Depth(7.4, LatLng(42.514353, 18.692046)),
            Depth(7.4, LatLng(42.393465, 18.573797)),
            Depth(7.3, LatLng(42.448175, 18.546621)),
            Depth(7.3, LatLng(42.451601, 18.516548)),
            Depth(7.3, LatLng(42.406797, 18.700837)),
            Depth(7.1, LatLng(42.453294, 18.519328)),
            Depth(7.1, LatLng(42.447937, 18.542527)),
            Depth(7.1, LatLng(42.42985, 18.693697)),
            Depth(7.1, LatLng(42.417243, 18.706606)),
            Depth(7.0, LatLng(42.477198, 18.762603)),
            Depth(7.0, LatLng(42.433368, 18.690644)),
            Depth(7.0, LatLng(42.411038, 18.680676)),
            Depth(7.0, LatLng(42.406617, 18.698767)),
            Depth(6.9, LatLng(42.421041, 18.514764)),
            Depth(6.9, LatLng(42.449672, 18.514301)),
            Depth(6.8, LatLng(42.496188, 18.658859)),
            Depth(6.8, LatLng(42.416026, 18.708656)),
            Depth(6.7, LatLng(42.446002, 18.576182)),
            Depth(6.7, LatLng(42.459159, 18.76388)),
            Depth(6.7, LatLng(42.401986, 18.703808)),
            Depth(6.6, LatLng(42.385146, 18.666726)),
            Depth(6.6, LatLng(42.43141, 18.692034)),
            Depth(6.6, LatLng(42.453859, 18.522544)),
            Depth(6.6, LatLng(42.408054, 18.694741)),
            Depth(6.5, LatLng(42.497281, 18.659474)),
            Depth(6.2, LatLng(42.489146, 18.756532)),
            Depth(6.2, LatLng(42.39855, 18.523088)),
            Depth(6.2, LatLng(42.408504, 18.702059)),
            Depth(6.2, LatLng(42.40508, 18.672684)),
            Depth(6.1, LatLng(42.415847, 18.67622)),
            Depth(6.0, LatLng(42.434326, 18.518844)),
            Depth(6.0, LatLng(42.404082, 18.703905)),
            Depth(6.0, LatLng(42.419495, 18.706367)),
            Depth(5.9, LatLng(42.386603, 18.579023)),
            Depth(5.9, LatLng(42.414913, 18.510771)),
            Depth(5.7, LatLng(42.510481, 18.695014)),
            Depth(5.7, LatLng(42.384532, 18.663396)),
            Depth(5.6, LatLng(42.453275, 18.560835)),
            Depth(5.5, LatLng(42.406624, 18.511508)),
            Depth(5.4, LatLng(42.448583, 18.764797)),
            Depth(5.4, LatLng(42.403251, 18.56477)),
            Depth(5.4, LatLng(42.447473, 18.510907)),
            Depth(5.1, LatLng(42.403587, 18.706329)),
            Depth(4.7, LatLng(42.394368, 18.55986)),
            Depth(4.6, LatLng(42.432926, 18.766788)),
            Depth(4.6, LatLng(42.4223, 18.764822)),
            Depth(4.6, LatLng(42.403073, 18.70898)),
            Depth(4.5, LatLng(42.462833, 18.737488)),
            Depth(4.5, LatLng(42.451637, 18.511454)),
            Depth(4.3, LatLng(42.398237, 18.579337)),
            Depth(4.2, LatLng(42.433013, 18.59948)),
            Depth(4.1, LatLng(42.445207, 18.509262)),
            Depth(4.0, LatLng(42.455113, 18.518184)),
            Depth(4.0, LatLng(42.431156, 18.758359)),
            Depth(4.0, LatLng(42.505202, 18.692874)),
            Depth(3.9, LatLng(42.399337, 18.707079)),
            Depth(3.9, LatLng(42.426048, 18.699925)),
            Depth(3.8, LatLng(42.453896, 18.513731)),
            Depth(3.8, LatLng(42.45591, 18.521106)),
            Depth(3.8, LatLng(42.40865, 18.708131)),
            Depth(3.7, LatLng(42.434715, 18.606217)),
            Depth(3.7, LatLng(42.412609, 18.710449)),
            Depth(3.6, LatLng(42.394434, 18.704941)),
            Depth(3.5, LatLng(42.409984, 18.711475)),
            Depth(3.4, LatLng(42.422731, 18.766096)),
            Depth(3.4, LatLng(42.384677, 18.658033)),
            Depth(3.4, LatLng(42.515371, 18.691682)),
            Depth(3.3, LatLng(42.397278, 18.706626)),
            Depth(3.3, LatLng(42.461535, 18.763721)),
            Depth(3.3, LatLng(42.504588, 18.693194)),
            Depth(3.3, LatLng(42.437446, 18.514538)),
            Depth(3.3, LatLng(42.401371, 18.706425)),
            Depth(3.2, LatLng(42.438435, 18.687848)),
            Depth(3.1, LatLng(42.450314, 18.555092)),
            Depth(3.1, LatLng(42.43083, 18.767218)),
            Depth(3.1, LatLng(42.440376, 18.511621)),
            Depth(3.1, LatLng(42.413913, 18.708032)),
            Depth(3.0, LatLng(42.438495, 18.513524)),
            Depth(2.9, LatLng(42.394873, 18.578311)),
            Depth(2.9, LatLng(42.48912, 18.760493)),
            Depth(2.8, LatLng(42.439733, 18.626468)),
            Depth(2.8, LatLng(42.438819, 18.68946)),
            Depth(2.8, LatLng(42.445577, 18.764589)),
            Depth(2.8, LatLng(42.446369, 18.507715)),
            Depth(2.8, LatLng(42.406832, 18.706266)),
            Depth(2.7, LatLng(42.448566, 18.535481)),
            Depth(2.7, LatLng(42.404982, 18.676752)),
            Depth(2.6, LatLng(42.40453, 18.673296)),
            Depth(2.5, LatLng(42.456703, 18.518556)),
            Depth(2.4, LatLng(42.434584, 18.60318)),
            Depth(2.4, LatLng(42.424502, 18.701408)),
            Depth(2.4, LatLng(42.442313, 18.509636)),
            Depth(2.3, LatLng(42.448149, 18.508051)),
            Depth(2.2, LatLng(42.433244, 18.602573)),
            Depth(2.2, LatLng(42.409731, 18.650632)),
            Depth(2.2, LatLng(42.425405, 18.699182)),
            Depth(2.1, LatLng(42.455458, 18.524321)),
            Depth(2.1, LatLng(42.385006, 18.68257)),
            Depth(2.1, LatLng(42.445102, 18.507665)),
            Depth(2.0, LatLng(42.455942, 18.515391)),
            Depth(1.9, LatLng(42.414198, 18.711436)),
            Depth(1.8, LatLng(42.443017, 18.76272)),
            Depth(1.8, LatLng(42.400149, 18.579289)),
            Depth(1.6, LatLng(42.449464, 18.542102)),
            Depth(1.6, LatLng(42.439711, 18.584924)),
            Depth(1.6, LatLng(42.407053, 18.71127)),
            Depth(1.5, LatLng(42.489645, 18.756784)),
            Depth(1.5, LatLng(42.405925, 18.664872)),
            Depth(1.3, LatLng(42.436227, 18.611409)),
            Depth(1.3, LatLng(42.446495, 18.506004)),
            Depth(1.3, LatLng(42.438991, 18.511314)),
            Depth(1.2, LatLng(42.438774, 18.621057)),
            Depth(1.2, LatLng(42.407327, 18.508169)),
            Depth(1.2, LatLng(42.417687, 18.710251)),
            Depth(1.1, LatLng(42.443313, 18.580229)),
            Depth(1.1, LatLng(42.392394, 18.572254)),
            Depth(1.1, LatLng(42.407335, 18.702561)),
            Depth(1.0, LatLng(42.458574, 18.516541)),
            Depth(1.0, LatLng(42.452965, 18.509118)),
            Depth(1.0, LatLng(42.456776, 18.514216)),
            Depth(1.0, LatLng(42.409115, 18.712897)),
            Depth(1.0, LatLng(42.415353, 18.712242)),
            Depth(0.6, LatLng(42.424331, 18.703034)),
            Depth(0.3, LatLng(42.451382, 18.506844)),
            Depth(0.2, LatLng(42.448467, 18.50629)),
            Depth(0.1, LatLng(42.45332, 18.507561))
        )
    }
}