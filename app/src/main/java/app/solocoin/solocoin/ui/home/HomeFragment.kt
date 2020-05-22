package app.solocoin.solocoin.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

import app.solocoin.solocoin.R
import app.solocoin.solocoin.app.SolocoinApp.Companion.sharedPrefs
import app.solocoin.solocoin.util.GlobalUtils
import app.solocoin.solocoin.util.enums.Status
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class HomeFragment : Fragment() {
    private val TAG = HomeFragment::class.simpleName

    private val viewModel: HomeFragmentViewModel by viewModel()

    private var tvHomeDuration: TextView?= null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvHomeDuration = view.findViewById(R.id.time)

        updateTime()

        quiz_viewpager.adapter = QuizFragmentAdapter(this)

        TabLayoutMediator(quiz_tablayout, quiz_viewpager) { tab, position ->
            tab.text = tabHeading[position]
        }.attach()





    }

    private fun updateTime() {
        viewModel.userData().observe(this, Observer { response ->
            Log.d(TAG, "$response")
            when (response.status) {
                Status.SUCCESS -> {
                    val duration = response.data?.get("home_duration_in_seconds")?.asLong
                    if (duration != 0L && duration != null) {
                        tvHomeDuration?.text = GlobalUtils.formattedHomeDuration(duration)
                        sharedPrefs?.homeDuration = duration
                    }
                }
                Status.ERROR -> {
                    if (sharedPrefs?.homeDuration != 0L) {
                        tvHomeDuration?.text =
                            GlobalUtils.formattedHomeDuration(sharedPrefs?.homeDuration)
                    }
                }
                Status.LOADING -> {
                }
            }
        })
    }

    companion object {
        fun instance() = HomeFragment().apply {}
        private const val TAB_COUNT = 2
        private val tabHeading = arrayOf("DAILY", "WEEKLY")
    }

    private class QuizFragmentAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int {
            return TAB_COUNT
        }

        override fun createFragment(position: Int): Fragment {
            return QuizFragment(position)
        }
    }
}
