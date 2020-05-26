package app.solocoin.solocoin.ui.home

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.solocoin.solocoin.R
import app.solocoin.solocoin.app.SolocoinApp
import app.solocoin.solocoin.model.Badge
import app.solocoin.solocoin.model.Level
import app.solocoin.solocoin.model.Milestones
import app.solocoin.solocoin.ui.adapter.MilestonesAdapter
import app.solocoin.solocoin.util.enums.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by Saurav Gupta on 22/05/2020
 */
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class MilestonesFragment : Fragment() {

    private lateinit var mAdapter: MilestonesAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var context: Activity

    private val viewModel: MilestonesFragmentViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        context = requireActivity()
        return inflater.inflate(R.layout.fragment_milestones, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        recyclerView = view.findViewById(R.id.milestones_recycler_view)
        swipeRefreshLayout = view.findViewById(R.id.milestones_sl)

        recyclerView.layoutManager = LinearLayoutManager(context)
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent)
        swipeRefreshLayout.setOnRefreshListener {
            updateUI()
            swipeRefreshLayout.isRefreshing = false
        }

        updateUI()
    }

    private fun updateUI() {
        mAdapter = MilestonesAdapter(context, ArrayList<Milestones>().apply {
            add(Milestones)
        })
        recyclerView.adapter = mAdapter
        updateWallet()
        updateBadges()
    }

    private fun updateWallet() {
        viewModel.userData().observe(viewLifecycleOwner, Observer { response ->
            Log.d(TAG, "$response")
            when (response.status) {
                Status.SUCCESS -> {
                    val balance = response.data?.get("wallet_balance")?.asString
                    SolocoinApp.sharedPrefs?.walletBalance = balance
                    Milestones.balance = balance
                }
                Status.ERROR -> {
                    Milestones.balance = SolocoinApp.sharedPrefs?.walletBalance
                }
                Status.LOADING -> {
                }
            }
        })
    }

    private fun updateLevelInfo(): ArrayList<Level?>? {
        return null
        TODO("Not yet implemented")
    }

    private fun updateBadges() {
        val badges = ArrayList<Badge?>().apply {
            add(Badge(null, "Common man", "Level 1", true))
            add(Badge(null, "Trainee", "Level 2", true))
            add(Badge(null, "Soldier", "Level 3", true))
            add(Badge(null, "Chief", "Level 4", true))
            add(Badge(null, "Commander", "Level 5", true))
        }
        Milestones.badges = badges
    }

    companion object {
        fun instance() = MilestonesFragment().apply {}
        private val TAG = MilestonesFragment::class.java.simpleName
    }
}
