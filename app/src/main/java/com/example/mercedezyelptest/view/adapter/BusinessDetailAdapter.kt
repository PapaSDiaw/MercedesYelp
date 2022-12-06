package com.example.mercedezyelptest.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mercedezyelptest.R
import com.example.mercedezyelptest.databinding.BusinessDetailItemLayoutBinding
import com.example.mercedezyelptest.model.remote.Business
import com.example.mercedezyelptest.view.provideStringResource
import com.squareup.picasso.Picasso

class BusinessDetailAdapter(private var dataSet: List<Business>):
    RecyclerView.Adapter<BusinessDetailAdapter.BusinessDetailViewHolder>() {

    fun updateDataSet(newList: List<Business>){
        dataSet = newList
        notifyDataSetChanged()
    }

    class BusinessDetailViewHolder(private val binding: BusinessDetailItemLayoutBinding):
            RecyclerView.ViewHolder(binding.root){
                fun onBind(dataItem: Business){
                    binding.apply {
                     itemBusinessDisplayLocation.text = provideStringResource(R.string.location, dataItem.location.display_address)
                     itemBusinessName.text = provideStringResource(R.string.name, dataItem.name)
                     itemBusinessPrice.text = provideStringResource(R.string.price, dataItem.price)
                     itemBusinessRating.text = provideStringResource(R.string.rating, dataItem.rating.toString())
                     itemBusinessDisplayDistance.text = provideStringResource(R.string.distance, dataItem.distance.toString())
                     itemBusinessDisplayPhone.text = provideStringResource(R.string.phone, dataItem.display_phone)
                     Picasso.get().load(dataItem.image_url).into(itemBusinessImage)
                    }
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BusinessDetailViewHolder(
            BusinessDetailItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: BusinessDetailViewHolder, position: Int) {
        holder.onBind(dataSet[position])
    }

    override fun getItemCount() =
        dataSet.size
}