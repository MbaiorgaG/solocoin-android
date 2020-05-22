package app.solocoin.solocoin.repo

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by Aditya Sonel on 22/04/20.
 */

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("callbacks/mobile_login")
    suspend fun mobileLogin(@Body body: JsonObject): Response<JsonObject>

    @Headers("Content-Type: application/json")
    @POST("callbacks/mobile_sign_up")
    suspend fun mobileSignUp(@Body body: JsonObject): Response<JsonObject>

    @Headers("Content-Type: application/json")
    @GET("user/profile")
    suspend fun userData(@Header("Authorization") authToken: String): Response<JsonObject>

    @Headers("Content-Type: application/json")
    @PATCH("user")
    suspend fun userUpdate(@Header("Authorization") authToken: String, @Body body: JsonObject): Response<JsonObject>

    @Headers("Content-Type: application/json")
    @POST("notification_tokens")
    fun registerNotificationToken(@Body body: JsonObject): Response<JsonObject>

    @Headers("Content-Type: application/json")
    @POST("sessions/ping")
    fun pingSession(@Header("Authorization") authToken: String, @Body body: JsonObject): Response<JsonObject>

    @Headers("Content-Type: application/json")
    @GET("user/profile")
    fun getProfile(@Header("Authorization") authToken: String): Response<JsonObject>

    @GET("questions/daily")
    suspend fun getDailyQuiz(@Header("Authorization") authToken: String): Response<JsonObject>

    @GET("questions/weekly")
    suspend fun getWeeklyQuiz(@Header("Authorization") authToken: String): Response<JsonObject>

    @Headers("Content-Type: application/json")
    @POST("user_questions_answers")
    suspend fun submitQuizAnswer(@Header("Authorization") authToken: String, @Body body: JsonObject): Response<JsonObject>
}