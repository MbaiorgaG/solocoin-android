package app.solocoin.solocoin.ui.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import app.solocoin.solocoin.BuildConfig
import app.solocoin.solocoin.R
import app.solocoin.solocoin.app.SolocoinApp.Companion.sharedPrefs
import app.solocoin.solocoin.util.AppDialog
import app.solocoin.solocoin.util.GlobalUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class ProfileFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //invite-btn
        view.findViewById<TextView>(R.id.tv_invite).setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.invite_subject))
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.invite_message, BuildConfig.APPLICATION_ID))
            startActivity(Intent.createChooser(shareIntent, getString(R.string.invite_title)))
        }
        //invite-btn

        //privacy-policy-btn
        view.findViewById<TextView>(R.id.tv_pp).setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_pp))))
        }
        //privacy-policy-btn

        //terms-condition-btn
        view.findViewById<TextView>(R.id.tv_tnc).setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_tnc))))
        }
        //terms-condition-btn

        //logout-btn
        view.findViewById<TextView>(R.id.tv_logout).setOnClickListener {
            val nInfo =
                activity.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE).activeNetworkInfo
            val connected = nInfo != null && nInfo.isAvailable && nInfo.isConnected
            if(connected){
                val logoutDialog = AppDialog.instance(getString(R.string.confirm), getString(R.string.tag_logout), object: AppDialog.AppDialogListener {
                    override fun onClickConfirm() {
                        GlobalUtils.logout(context!!)
                        sharedPrefs?.let{
                            it.loggedIn = false
                        }
                        activity?.finish()
                    }

                    override fun onClickCancel() {}
                }, getString(R.string.logout), getString(R.string.cancel))
                logoutDialog.show(childFragmentManager, logoutDialog.tag)
            } else {
                val logoutDialog = AppDialog.instance(getString(R.string.unable_logout), getString(R.string.tag_logout), object: AppDialog.AppDialogListener {
                    override fun onClickConfirm() {}

                    override fun onClickCancel() {}
                }, getString(R.string.okay))
                logoutDialog.show(childFragmentManager, logoutDialog.tag)
            }

        }
        //logout-btn
    }

    companion object {
        fun instance() = ProfileFragment()
    }
}
