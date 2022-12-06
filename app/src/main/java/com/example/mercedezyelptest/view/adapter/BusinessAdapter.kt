package com.example.mercedezyelptest.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mercedezyelptest.R
import com.example.mercedezyelptest.databinding.BusinessItemLayoutBinding
import com.example.mercedezyelptest.model.remote.Business
import com.example.mercedezyelptest.view.provideStringResource
import com.squareup.picasso.Picasso

class BusinessAdapter(private var dataSet: List<Business>, val openDetails: (Int)-> Unit): RecyclerView.Adapter<BusinessAdapter.BusinessViewHolder>() {

    fun updateDataSet(newList: List<Business>){
        dataSet = newList
        notifyDataSetChanged()
    }

    class BusinessViewHolder(private val binding: BusinessItemLayoutBinding):
        RecyclerView.ViewHolder(binding.root){

            fun onBind(dataItem: Business, openDetails: (Int)-> Unit){
                binding.apply {
                    itemBusinessName.text = dataItem.name
                    itemBusinessPrice.text = provideStringResource(R.string.price, dataItem.price)
                    itemBusinessDisplayLocation.text = provideStringResource(R.string.location, dataItem.location.display_address)
                    itemBusinessRating.text = provideStringResource(R.string.rating, dataItem.rating.toString())
                    Picasso.get().load(dataItem.image_url).into(itemBusinessImage)
                    root.setOnClickListener { openDetails(adapterPosition) }
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BusinessViewHolder(
            BusinessItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: BusinessViewHolder, position: Int) {
        holder.onBind(dataSet[position], openDetails)
    }

    override fun getItemCount() =
        dataSet.size
}