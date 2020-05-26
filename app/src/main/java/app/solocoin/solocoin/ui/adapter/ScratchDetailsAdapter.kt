package app.solocoin.solocoin.ui.adapter

// Author: Vijay Daita

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import app.solocoin.solocoin.R
import app.solocoin.solocoin.model.ScratchTicket
import java.util.*

/**
 * Created by Vijay Daita
 */
class ScratchDetailsAdapter(
    private val context: Activity,
    private val scratchArrayList: ArrayList<ScratchTicket?>
) :
    RecyclerView.Adapter<ScratchDetailsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder =
        ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_scratch_card, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setUpView(scratchArrayList[position])
    }

    override fun getItemCount() = scratchArrayList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var scratchCost: TextView? = null
        private var scratchReward: TextView? = null
        var scratchCardLayout: ConstraintLayout? = null

        init {
            with(itemView) {
                scratchCost = findViewById(R.id.scratch_cost)
                scratchReward = findViewById(R.id.scratch_reward)
                scratchCardLayout = findViewById(R.id.scratch_constraint_layout)
            }
        }

        fun setUpView(scratchTicket: ScratchTicket?) {
            scratchTicket?.let {
                scratchCost?.text = it.costRupees!!
                scratchReward?.text = it.rewardRupees!!
                // TODO: make network call when layout is clicked.
            }
        }

    }

}