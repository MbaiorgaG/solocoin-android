package app.solocoin.solocoin.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import app.solocoin.solocoin.repo.SolocoinRepository
import app.solocoin.solocoin.util.Resource
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import retrofit2.Response


/**
 * Created by Aditya Sonel on 27/04/20.
 */

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class LoginSignupViewModel(private val repository: SolocoinRepository): ViewModel() {
    fun mobileLogin(body: JsonObject): LiveData<Resource<JsonObject?>> = liveData(Dispatchers.IO) {
        Log.d("xoxo", "body, vm: $body")
        if (body.size() != 0) {
            emit(Resource.loading(data = null))
            try {
                emit(Resource.success(data = repository.mobileLogin(body).body()))
            } catch (exception: Exception) {
                emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.wtf("xoxo", "onCleared!")
    }
}