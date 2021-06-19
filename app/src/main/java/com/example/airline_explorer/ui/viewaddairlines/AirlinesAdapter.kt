package com.example.airline_explorer.ui.viewaddairlines

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.airline_explorer.R
import com.example.airline_explorer.data.model.Airline
import com.example.airline_explorer.databinding.ItemAirlineBinding


class AirlinesAdapter(
    private var airlinesList: List<Airline>? = null,
    private var itemClickListener: AirlineItemClickListener? = null
) : RecyclerView.Adapter<AirlinesAdapter.ViewHolder>() {

    private lateinit var inflater: LayoutInflater

    fun updateAirlines(airlinesList: List<Airline>?) {
        if (this.airlinesList === airlinesList) return
        this.airlinesList = airlinesList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (!::inflater.isInitialized) inflater = LayoutInflater.from(parent.context)

        val binding = DataBindingUtil.inflate<ItemAirlineBinding>(
            inflater,
            R.layout.item_airline,
            parent,
            false
        )
        binding.clickListener = itemClickListener

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.airline = airlinesList?.get(position)
    }

    override fun getItemCount(): Int {
        return airlinesList?.size ?: 0
    }

    class ViewHolder(internal val binding: ItemAirlineBinding) :
        RecyclerView.ViewHolder(binding.root)

    /**
     * Used in handling airlines items clicks
     */
    interface AirlineItemClickListener {
        fun onAirlineItemClick(airline: Airline)
    }

    companion object {
        private val TAG = AirlinesAdapter::class.simpleName
    }
}