package app.solocoin.solocoin.util

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View
import android.view.inputmethod.InputMethodManager
import app.solocoin.solocoin.app.SolocoinApp.Companion.sharedPrefs
import app.solocoin.solocoin.ui.SplashActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi


/**
 * Created by Aditya Sonel on 22/04/20.
 */
class GlobalUtils {
    companion object {

        /**
         * Public function for detecting is network available or not
         */
        fun isNetworkAvailable(context: Context): Boolean {
            var isConnected = false
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
            if (activeNetwork != null && activeNetwork.isConnected)
                isConnected = true
            return isConnected
        }

        /**
         * For starting desired activity as a new task,
         * intent_flags are added for clear back stack.
         * @param context: context of current fragment/activity
         * @param intent: activity class need to start
         *
         * Note: call finish() method after this function execution
         */
        fun startActivityAsNewStack(intent: Intent, context: Context) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(intent)
        }

        /**
         * For closing keyboard explicitly
         * @param context: context of current activity/fragment
         * @param view: keyboard is currently attached to
         */
        fun closeKeyboard(context: Context, view: View) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }

        @InternalCoroutinesApi
        @ExperimentalCoroutinesApi
        fun logout(context: Context) {
            sharedPrefs?.clearSession()
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(context, SplashActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(intent)
        }

        fun getSessionType(): String {
            return ""
            TODO("Find difference between current and pref location return status")
        }

    }
}