package com.example.firemap

class FMCoordinate(coord: String) {
    private val latitude: MatchResult?
    private val longitude: MatchResult?
    private val coordFilter = Regex("""\d{1,3}d\s?\d{1,2}\'\d{1,2}\.\d*\"[N|S|E|W]""")

    init {
        latitude = coordFilter.find(coord)
        longitude = latitude?.next()
    }
    fun getLatitude() = latitude?.value
    fun getLongitude() = longitude?.value
}
class DMS(coord: String) {
    private var degrees: Double
    private var minutes: Double
    private var seconds: Double
    private val coordSeparator = Regex("""\d{1,3}\.?\d{1,3}""")
    init {
        val degreesMatch = coordSeparator.find(coord)
        val minutesMatch = degreesMatch?.next()
        val secondsMatch = minutesMatch?.next()

        degrees = degreesMatch?.value!!.toDouble()
        minutes = minutesMatch?.value!!.toDouble()
        seconds = secondsMatch?.value!!.toDouble()
    }
    fun getDegrees() = degrees
    fun getMinutes() = minutes
    fun getSeconds() = seconds
    fun getDecimal() : Double {
        return degrees + minutes / 60.0 + seconds / 3600.0
    }
}