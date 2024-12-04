package llc.bokadev.bokabayseatrafficapp.core.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Circle
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.Dash
import com.google.android.gms.maps.model.Gap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PatternItem
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import llc.bokadev.bokabayseatrafficapp.data.local.model.RouteEntity
import llc.bokadev.bokabayseatrafficapp.domain.model.Checkpoint
import llc.bokadev.bokabayseatrafficapp.domain.model.ProhibitedAnchoringZone
import kotlin.math.PI
import kotlin.math.sin


val allPolylines: MutableList<Polyline> = mutableListOf()
val pipelinePolylines: MutableList<Polyline> = mutableListOf()
val allCircles: MutableList<Circle> = mutableListOf()


// Function to calculate the centroid from List<Pair<LatLng, LatLng>>
fun calculateBoundingBoxCentroid(pairs: List<Pair<LatLng, LatLng>>): LatLng {
    val latitudes = mutableListOf<Double>()
    val longitudes = mutableListOf<Double>()

    // Check if we have pairs
    if (pairs.isEmpty()) {
        throw IllegalArgumentException("At least one pair of coordinates is needed.")
    }

    // Process each pair
    for (pair in pairs) {
        val (point1, point2) = pair
        latitudes.add(point1.latitude)
        latitudes.add(point2.latitude)
        longitudes.add(point1.longitude)
        longitudes.add(point2.longitude)
    }

    // Calculate the centroid from all the latitude and longitude values
    val latSum = latitudes.sum()
    val lngSum = longitudes.sum()
    val count = latitudes.size

    return LatLng(latSum / count, lngSum / count)
}

fun calculateCentroid(points: List<LatLng>): LatLng {
    if (points.isEmpty()) {
        throw IllegalArgumentException("At least one point is needed.")
    }

    // Extract latitude and longitude values
    val latitudes = points.map { it.latitude }
    val longitudes = points.map { it.longitude }

    // Calculate the centroid
    val latSum = latitudes.sum()
    val lngSum = longitudes.sum()
    val count = points.size

    return LatLng(latSum / count, lngSum / count)
}

fun drawLinesBetweenPoints(map: GoogleMap, points: List<LatLng>) {
    // Check if there are enough points to draw lines
    if (points.size < 2) {
        throw IllegalArgumentException("At least two points are needed to draw lines.")
    }

    // Iterate over the points and draw lines between consecutive points
    for (i in 0 until points.size - 1) {
        val start = points[i]
        val end = points[i + 1]

        val polylineOptions = PolylineOptions().apply {
            color(0xFF9678B6.toInt()) // Line color
            width(2f)
            pattern(listOf(Dash(30f), Gap(20f))) // Dash and gap pattern
            add(start)
            add(end)
        }

        // Add the polyline to the map and store it for future reference
        allPolylines.add(map.addPolyline(polylineOptions))
    }

    // Connect the last point back to the first point to close the loop
    val firstPoint = points.first()
    val lastPoint = points.last()

    val closingLine = PolylineOptions().apply {
        color(0xFF9678B6.toInt()) // Line color
        width(2f)
        pattern(listOf(Dash(30f), Gap(20f))) // Dash and gap pattern
        add(lastPoint)
        add(firstPoint)
    }

    // Add the closing polyline to the map and store it for future reference
    allPolylines.add(map.addPolyline(closingLine))
}


fun drawAnchoringZoneLines(map: GoogleMap, prohibitedAnchoringZone: ProhibitedAnchoringZone) {
    // Check if we have points to work with
    if (prohibitedAnchoringZone.points.isNotEmpty()) {
        // Case 1: Only one pair of points provided
        if (prohibitedAnchoringZone.points.size == 1) {
            val pair = prohibitedAnchoringZone.points[0]
            val topRight = pair.first
            val bottomRight = pair.second

            // Draw a single vertical line between the two points
            val verticalLine = PolylineOptions().apply {
                color(0xFF9678B6.toInt()) // Line color
                width(2f)
                pattern(listOf(Dash(30f), Gap(20f))) // Dash and gap pattern
                add(topRight)
                add(bottomRight)
            }
            allPolylines.add(map.addPolyline(verticalLine))
//            map.addPolyline(verticalLine)
        }
        // Case 2: Multiple pairs provided, determine the bounding box
        else if (prohibitedAnchoringZone.points.size > 1) {
            val firstPair = prohibitedAnchoringZone.points[0]
            val secondPair = prohibitedAnchoringZone.points[1]

            val topRight = firstPair.first
            val bottomRight = firstPair.second
            val bottomLeft = secondPair.second
            val topLeft = secondPair.first

            // Draw vertical line from top-right to bottom-right
            when (prohibitedAnchoringZone.id) {
                7 -> {
                    val verticalLineRight = PolylineOptions().apply {
                        color(0xFF9678B6.toInt()) // Line color
                        width(2f)
                        pattern(listOf(Dash(30f), Gap(20f))) // Dash and gap pattern
                        add(topRight)
                        add(bottomRight)
                    }
//                    map.addPolyline(verticalLineRight)
                    allPolylines.add(map.addPolyline(verticalLineRight))
                    val connectionLine = PolylineOptions().apply {
                        color(0xFF9678B6.toInt()) // Line color
                        width(2f)
                        pattern(listOf(Dash(30f), Gap(20f))) // Dash and gap pattern
                        add(topLeft)
                        add(bottomRight)
                    }
//                    map.addPolyline(connectionLine)
                    allPolylines.add(map.addPolyline(connectionLine))

                    // Draw vertical line from bottom-left to top-left
                    val verticalLineLeft = PolylineOptions().apply {
                        color(0xFF9678B6.toInt()) // Line color
                        width(2f)
                        pattern(listOf(Dash(30f), Gap(20f))) // Dash and gap pattern
                        add(bottomLeft)
                        add(topLeft)
                    }
//                    map.addPolyline(verticalLineLeft)
                    allPolylines.add(map.addPolyline(verticalLineLeft))
                }

                10 -> {
                    if (prohibitedAnchoringZone.points.size > 1) {
                        val firstPair = prohibitedAnchoringZone.points[0]
                        val lastPair =
                            prohibitedAnchoringZone.points[prohibitedAnchoringZone.points.size - 1]

                        // Get the corners of the boundary
                        val topRight = firstPair.first
                        val bottomRight = firstPair.second
                        val bottomLeft = lastPair.second
                        val topLeft = lastPair.first

                        // Draw vertical line from top-right to bottom-right
                        val verticalLineRight = PolylineOptions().apply {
                            color(0xFF9678B6.toInt()) // Line color
                            width(2f)
                            pattern(listOf(Dash(30f), Gap(20f))) // Dash and gap pattern
                            add(topRight)
                            add(bottomRight)
                        }
                        allPolylines.add(map.addPolyline(verticalLineRight))


                        // Draw vertical line from bottom-left to top-left
                        val verticalLineLeft = PolylineOptions().apply {
                            color(0xFF9678B6.toInt()) // Line color
                            width(2f)
                            pattern(listOf(Dash(30f), Gap(20f))) // Dash and gap pattern
                            add(bottomLeft)
                            add(topLeft)
                        }
                        allPolylines.add(map.addPolyline(verticalLineLeft))

                        // Draw line from top-left to bottom-right
                        val connectionLine = PolylineOptions().apply {
                            color(0xFF9678B6.toInt()) // Line color
                            width(2f)
                            pattern(listOf(Dash(30f), Gap(20f))) // Dash and gap pattern
                            add(topLeft)
                            add(bottomRight)
                        }
                        allPolylines.add(map.addPolyline(connectionLine))
                    }

                }

                else -> {
                    val verticalLineRight = PolylineOptions().apply {
                        color(0xFF9678B6.toInt()) // Line color
                        width(2f)
                        pattern(listOf(Dash(30f), Gap(20f))) // Dash and gap pattern
                        add(topRight)
                        add(bottomRight)
                    }
//                    map.addPolyline(verticalLineRight)
                    allPolylines.add(map.addPolyline(verticalLineRight))

                    // Draw vertical line from bottom-left to top-left
                    val verticalLineLeft = PolylineOptions().apply {
                        color(0xFF9678B6.toInt()) // Line color
                        width(2f)
                        pattern(listOf(Dash(30f), Gap(20f))) // Dash and gap pattern
                        add(bottomLeft)
                        add(topLeft)
                    }
//                    map.addPolyline(verticalLineLeft)
                    allPolylines.add(map.addPolyline(verticalLineLeft))
                }
            }

        }
    }
}

fun drawAnchoringZoneLinesWith8Points(
    map: GoogleMap,
    prohibitedAnchoringZone: ProhibitedAnchoringZone
) {
    //For meljine prohibited zone
    if (prohibitedAnchoringZone.points.size == 4) {
        val pair1 = prohibitedAnchoringZone.points[0]
        val pair2 = prohibitedAnchoringZone.points[1]
        val pair3 = prohibitedAnchoringZone.points[2]
        val pair4 = prohibitedAnchoringZone.points[3]

        val point1 = pair1.first
        val point2 = pair1.second
        val point3 = pair2.second
        val point4 = pair2.first
        val point5 = pair3.first
        val point6 = pair3.second
        val point7 = pair4.first
        val point8 = pair4.second

        //Left side shore to first point
        val line1 = PolylineOptions().apply {
            color(0xFF9678B6.toInt()) // Line color
            width(2f)
            pattern(listOf(Dash(30f), Gap(20f)))
            add(point1, point2)
        }
//        map.addPolyline(line1)
        allPolylines.add(map.addPolyline(line1))


        //Left side first to second point
        val line2 = PolylineOptions().apply {
            color(0xFF9678B6.toInt())
            width(2f)
            pattern(listOf(Dash(30f), Gap(20f)))
            add(point2, point4)
        }
//        map.addPolyline(line2)
        allPolylines.add(map.addPolyline(line2))
        //Left side second to third
        val line3 = PolylineOptions().apply {
            color(0xFF9678B6.toInt())
            width(2f)
            pattern(listOf(Dash(30f), Gap(20f)))
            add(point3, point4)
        }
//        map.addPolyline(line3)
        allPolylines.add(map.addPolyline(line3))


        //Left right connection
        val line4 = PolylineOptions().apply {
            color(0xFF9678B6.toInt())
            width(2f)
            pattern(listOf(Dash(30f), Gap(20f)))
            add(point3, point5)
        }
//        map.addPolyline(line4)
        allPolylines.add(map.addPolyline(line4))

        val line5 = PolylineOptions().apply {
            color(0xFF9678B6.toInt())
            width(2f)
            pattern(listOf(Dash(30f), Gap(20f)))
            add(point5, point6)
        }
//        map.addPolyline(line5)
        allPolylines.add(map.addPolyline(line5))

        val line6 = PolylineOptions().apply {
            color(0xFF9678B6.toInt())
            width(2f)
            pattern(listOf(Dash(30f), Gap(20f)))
            add(point6, point7)
        }
//        map.addPolyline(line6)
        allPolylines.add(map.addPolyline(line6))

        val line7 = PolylineOptions().apply {
            color(0xFF9678B6.toInt())
            width(2f)
            pattern(listOf(Dash(30f), Gap(20f)))
            add(point7, point8)
        }
//        map.addPolyline(line7)
        allPolylines.add(map.addPolyline(line7))
    }
}

fun drawAnchoringZoneLinesWithSixPoints(
    map: GoogleMap,
    prohibitedAnchoringZone: ProhibitedAnchoringZone
) {
    // Check if we have exactly 6 points to work with
    if (prohibitedAnchoringZone.points.size == 3) { // Each pair contains 2 points, hence 4 pairs
        val pair1 = prohibitedAnchoringZone.points[0] // Top-right and bottom-right
        val pair2 = prohibitedAnchoringZone.points[1] // Bottom-right and bottom-left
        val pair3 = prohibitedAnchoringZone.points[2] // Bottom-left and top-left

        val point1 = pair1.first // Top-right
        val point2 = pair1.second // Bottom-right
        val point3 = pair2.second // Bottom-left
        val point4 = pair2.first // Top-left
        val point5 = pair3.first // Additional point 1
        val point6 = pair3.second // Additional point 2

        // Draw the lines to form the desired shape

        if (prohibitedAnchoringZone.id == 2) {
            val line1 = PolylineOptions().apply {
                color(0xFF9678B6.toInt()) // Line color
                width(2f)
                pattern(listOf(Dash(30f), Gap(20f)))
                add(point1, point2)
            }
//            map.addPolyline(line1)
            allPolylines.add(map.addPolyline(line1))

            // Draw line from point2 (Bottom-right) to point3 (Bottom-left)
            val line2 = PolylineOptions().apply {
                color(0xFF9678B6.toInt())
                width(2f)
                pattern(listOf(Dash(30f), Gap(20f)))
                add(point5, point6)
            }
//            map.addPolyline(line2)
            allPolylines.add(map.addPolyline(line2))

            // Draw additional lines for the irregular shape
            val line5 = PolylineOptions().apply {
                color(0xFF9678B6.toInt())
                width(2f)
                pattern(listOf(Dash(30f), Gap(20f)))
                add(point4, point3)
            }
//            map.addPolyline(line5)
            allPolylines.add(map.addPolyline(line5))
        } else {
            // Draw the lines to form the desired shape

            // Draw line from point1 (Top-right) to point2 (Bottom-right)
            val line1 = PolylineOptions().apply {
                color(0xFF9678B6.toInt()) // Line color
                width(2f)
                pattern(listOf(Dash(30f), Gap(20f)))
                add(point1, point2)
            }
//            map.addPolyline(line1)
            allPolylines.add(map.addPolyline(line1))

            // Draw line from point2 (Bottom-right) to point3 (Bottom-left)
            val line2 = PolylineOptions().apply {
                color(0xFF9678B6.toInt())
                width(2f)
                pattern(listOf(Dash(30f), Gap(20f)))
                add(point2, point4)
            }
//            map.addPolyline(line2)
            allPolylines.add(map.addPolyline(line2))

            val line3 = PolylineOptions().apply {
                color(0xFF9678B6.toInt())
                width(2f)
                pattern(listOf(Dash(30f), Gap(20f)))
                add(point3, point5)
            }
//            map.addPolyline(line3)
            allPolylines.add(map.addPolyline(line3))

            val line4 = PolylineOptions().apply {
                color(0xFF9678B6.toInt())
                width(2f)
                pattern(listOf(Dash(30f), Gap(20f)))
                add(point4, point3)
            }
//            map.addPolyline(line4)
            allPolylines.add(map.addPolyline(line4))

            // Draw additional lines for the irregular shape
            val line5 = PolylineOptions().apply {
                color(0xFF9678B6.toInt())
                width(2f)
                pattern(listOf(Dash(30f), Gap(20f)))
                add(point5, point6)
            }
//            map.addPolyline(line5)
            allPolylines.add(map.addPolyline(line5))
//
        }

//
    }
}


fun calculateBounds(checkpoints: MutableList<Checkpoint>, context: Context): LatLngBounds {
    val builder = LatLngBounds.Builder()

    val selected = checkpoints.filter {
        it.isSelected
    }
    if (selected.size >= 2) {
        selected.forEach {
            builder.include(LatLng(it.latitude, it.longitude))
        }
    } else {
        checkpoints.forEach {
            builder.include(LatLng(it.latitude, it.longitude))
        }
    }
    return builder.build()
}

fun calculateBoundsCustomRoute(routeEntity: RouteEntity, context: Context): LatLngBounds {
    val builder = LatLngBounds.Builder()

    if (routeEntity.pointS.size >= 2) {
        routeEntity.pointS.forEach {
            builder.include(LatLng(it.latitude, it.longitude))
        }
    } else {
        routeEntity.pointS.forEach {
            builder.include(LatLng(it.latitude, it.longitude))
        }
    }
    return builder.build()
}

fun bitmapDescriptorFromVector(
    height: Int? = null, width: Int? = null, context: Context, vectorResId: Int
): BitmapDescriptor? {

    // retrieve the actual drawable
    val drawable = ContextCompat.getDrawable(context, vectorResId) ?: return null
    drawable.setBounds(0, 0, width ?: drawable.intrinsicWidth, height ?: drawable.intrinsicHeight)
    val bm = Bitmap.createBitmap(
        width ?: drawable.intrinsicWidth,
        height ?: drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    // draw it onto the bitmap
    val canvas = android.graphics.Canvas(bm)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bm)
}


fun bitmapDescriptorFromText(
    text: String,
    height: Int? = null,
    width: Int? = null,
    context: Context
): BitmapDescriptor {

    // Set default dimensions if not provided
    val textSize = 40f  // Default text size, adjust as needed
    val defaultWidth = width ?: 100
    val defaultHeight = height ?: 100

    // Create a new bitmap with the given width and height
    val bitmap = Bitmap.createBitmap(defaultWidth, defaultHeight, Bitmap.Config.ARGB_8888)

    // Create a canvas to draw onto the bitmap
    val canvas = Canvas(bitmap)
    canvas.drawColor(Color.TRANSPARENT)  // Transparent background

    // Create a Paint object to define the text style
    val paint = Paint()
    paint.color = Color.BLACK // Set the text color
    paint.textSize = textSize
    paint.isAntiAlias = true
    paint.textAlign = Paint.Align.CENTER

    // Calculate the coordinates to draw the text in the center
    val xPos = canvas.width / 2
    val yPos = (canvas.height / 2 - (paint.descent() + paint.ascent()) / 2).toInt()

    // Draw the text onto the canvas
    canvas.drawText(text, xPos.toFloat(), yPos.toFloat(), paint)

    // Convert the bitmap to a BitmapDescriptor and return it
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}


fun bitmapDescriptorFromVectorWithNumber(number: Int): BitmapDescriptor {
    val size = 70 // Define the size of the marker icon
    val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    val paint = Paint().apply {
        color = Color.RED // Customize marker color
        textSize = 34f    // Customize text size
        typeface = Typeface.DEFAULT_BOLD
        textAlign = Paint.Align.CENTER
    }

    // Draw a circle for the marker background
    canvas.drawCircle(
        (size / 2).toFloat(), // Center X
        (size / 2).toFloat(), // Center Y
        (size / 2).toFloat(), // Radius
        Paint().apply {
            color = Color.WHITE // Background color
            style = Paint.Style.FILL
        }
    )

    // Draw the number on top of the circle
    canvas.drawText(
        number.toString(),
        (size / 2).toFloat(),
        (size / 2 - ((paint.descent() + paint.ascent()) / 2)), // Center vertically
        paint
    )

    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

fun bitmapDescriptorFromVectorWithNumberOrLetter(number: Int): BitmapDescriptor {
    val size = 70 // Define the size of the marker icon
    val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    // Calculate the corresponding letter for the number
    // Numbers 1-26 -> A-Z, 27-52 -> A-Z again, etc.
    val content = if (number in 1..26) {
        'A' + (number - 1) // Convert number to letter
    } else if (number > 26) {
        val normalizedNumber = (number - 1) % 26
        'A' + normalizedNumber // Wrap around after Z
    } else {
        number.toString() // Fallback for numbers less than 1
    }.toString()

    // Colors for customization
    val backgroundColor = Color.parseColor("#4CAF50") // Green background
    val textColor = Color.WHITE // White text

    // Text paint for drawing the content
    val paint = Paint().apply {
        color = textColor
        textSize = 34f
        typeface = Typeface.DEFAULT_BOLD
        textAlign = Paint.Align.CENTER
    }

    // Draw a circle for the marker background
    canvas.drawCircle(
        (size / 2).toFloat(), // Center X
        (size / 2).toFloat(), // Center Y
        (size / 2).toFloat(), // Radius
        Paint().apply {
            color = backgroundColor
            style = Paint.Style.FILL
        }
    )

    // Draw the content (number or letter) on top of the circle
    canvas.drawText(
        content.toString(),
        (size / 2).toFloat(),
        (size / 2 - ((paint.descent() + paint.ascent()) / 2)), // Center vertically
        paint
    )

    return BitmapDescriptorFactory.fromBitmap(bitmap)
}


fun createWavyPolyline(
    start: LatLng,
    end: LatLng,
    amplitude: Double, // Height of the wave
    wavelength: Double, // Length of one wave cycle
    segments: Int,
): PolylineOptions {
    val polylineOptions = PolylineOptions().apply {
        color(0xFF9678B6.toInt()) // Line color
        width(2f) // Line width
    }

    // Calculate the total distance between start and end points
    val latDistance = end.latitude - start.latitude
    val lngDistance = end.longitude - start.longitude

    // Loop over the number of segments to build the wavy polyline
    for (i in 0..segments) {
        // The parameter `t` will range from 0 to 1 as we loop through the segments
        val t = i / segments.toDouble()

        // Calculate the current point between the start and end points
        val latitude = start.latitude + t * latDistance
        val longitude = start.longitude + t * lngDistance

        // Add the sine wave effect to BOTH latitude and longitude to make it noticeable
        val sineWaveLat = amplitude * sin(2 * PI * t * (1 / wavelength))
        val sineWaveLng = amplitude * sin(2 * PI * t * (1 / wavelength))

        // Apply the sine wave to both latitude and longitude (this will ensure it wiggles)
        val wavyLatitude =
            latitude + sineWaveLat * 0.01 // Adjust scaling for more noticeable effect
        val wavyLongitude =
            longitude + sineWaveLng * 0.0005 // Adjust scaling for more noticeable effect

        // Add the modified point to the polyline
        polylineOptions.add(LatLng(wavyLatitude, wavyLongitude))

    }

    return polylineOptions
}


fun drawCustomDashedPolylineWithCircles(map: GoogleMap, points: List<LatLng>) {
    // Define the pattern items
    val pattern: List<PatternItem> = listOf(
        Dash(20f), // Length of the dash
        Gap(30f)   // Length of the gap
    )

    // Create the polyline options
    val polylineOptions = PolylineOptions()
        .addAll(points)
        .pattern(pattern)
        .width(2f) // Width of the polyline
        .color(0xFF9678B6.toInt()) // Color of the polyline (blue)

    // Add the polyline to the map
    val polyline = pipelinePolylines.add(map.addPolyline(polylineOptions))

    // Add small circles at the end of each dash
    val dashLength = 20f
    for (i in 0 until points.size - 1) {
        val start = points[i]
        val end = points[i + 1]
        val segmentLength = FloatArray(1)
        android.location.Location.distanceBetween(
            start.latitude, start.longitude,
            end.latitude, end.longitude,
            segmentLength
        )
        val numDashes = (segmentLength[0] / (dashLength * 2)).toInt()
        for (j in 0 until numDashes) {
            val fraction = (j * 2 + 1) * dashLength / segmentLength[0]
            val lat = start.latitude + fraction * (end.latitude - start.latitude)
            val lng = start.longitude + fraction * (end.longitude - start.longitude)
            val circlePosition = LatLng(lat, lng)
            allCircles.add(
                map.addCircle(
                    CircleOptions()
                        .center(circlePosition)
                        .radius(2.0) // Radius of the circle in meters
                        .strokeColor(0xFF9678B6.toInt()) // Stroke color of the circle
                        .fillColor(0xFF9678B6.toInt()) // Fill color of the circle
                        .strokeWidth(2f) // Stroke width of the circle
                )
            )
        }
    }
}


fun clearAllPolylines() {
    for (polyline in allPolylines) {
        polyline.remove()  // Remove polyline from the map
    }
    allPolylines.clear()  // Clear the list after removing the polylines
}

fun clearPipelineLinesAndCircles() {
    for (polyline in pipelinePolylines) {
        polyline.remove()  // Remove polyline from the map
    }
    for (circle in allCircles) {
        circle.remove()
    }
    pipelinePolylines.clear()
}


fun getMidpoint(pos1: LatLng?, pos2: LatLng?): LatLng {
    if (pos1 == null || pos2 == null) return LatLng(0.0, 0.0)

    val lat = (pos1.latitude + pos2.latitude) / 2
    val lng = (pos1.longitude + pos2.longitude) / 2
    return LatLng(lat, lng)
}

fun updateTextViewPosition(
    map: GoogleMap,
    midpoint: LatLng?,
    textView: TextView,
    distanceText: String
) {
    if (midpoint == null) return

    val projection = map.projection
    val screenPosition = projection.toScreenLocation(midpoint)

    // Update the text and position of the TextView
    textView.text = distanceText
    textView.x = screenPosition.x.toFloat()
    textView.y = screenPosition.y.toFloat()
    textView.visibility = View.VISIBLE
}


fun textAsBitmap(text: String?, textSize: Float, textColor: Int): BitmapDescriptor {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    paint.textSize = textSize
    paint.color = textColor
    paint.textAlign = Paint.Align.LEFT
    val baseline = -paint.ascent() // ascent() is negative
    val width = (paint.measureText(text) + 0.5f).toInt() // round
    val height = (baseline + paint.descent() + 0.5f).toInt()
    val image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(image)
    canvas.drawText(text!!, 0f, baseline, paint)
    return BitmapDescriptorFactory.fromBitmap(image)
}

fun Double.toFormattedString(): String {
    return if (this % 1.0 == 0.0) {
        this.toInt().toString()
    } else {
        this.toString()
    }
}