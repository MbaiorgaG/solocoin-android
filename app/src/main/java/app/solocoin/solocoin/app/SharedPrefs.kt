package app.solocoin.solocoin.app

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

/**
 * Created by Aditya Sonel on 22/04/20.
 */

class SharedPrefs(context: Context) {
    private val instance: SharedPreferences = context.getSharedPreferences("${context.packageName}_preferences", MODE_PRIVATE)

    private val auth_token = "auth_token"
    var authToken: String?
        get() = instance.getString(auth_token, null)
        set(value) = instance.edit().putString(auth_token, value).apply()

    private val country_code = "country_code"
    var countryCode: String?
        get() = instance.getString(country_code, null)
        set(value) = instance.edit().putString(country_code, value).apply()

    private val mobile_number = "mobile_number"
    var mobileNumber: String?
        get() = instance.getString(mobile_number, null)
        set(value) = instance.edit().putString(mobile_number, value).apply()

    private val id_token = "id_token"
    var idToken: String?
        get() = instance.getString(id_token, null)
        set(value) = instance.edit().putString(id_token, value).apply()

    private val latitude = "latitude"
    var userLat: Long
        get() = instance.getLong(latitude, 0)
        set(value) = instance.edit().putLong(latitude, value).apply()

    private val longitude = "longitude"
    var userLong: Long
        get() = instance.getLong(longitude, 0)
        set(value) = instance.edit().putLong(longitude, value).apply()

    fun clearSession() {
        instance.edit()
            .clear()
            .apply()
    }
}