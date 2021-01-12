package com.dima.vkclient.ui.profile.adapter.career

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dima.vkclient.R
import com.dima.vkclient.data.domain.userprofile.CareerDomain

class CareerAdapter(private val careerList: List<CareerDomain>) :
    RecyclerView.Adapter<CareerAdapter.CareerViewHolder>() {

    override fun getItemCount(): Int = careerList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CareerViewHolder {
        return CareerViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_career, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CareerViewHolder, position: Int) {
        holder.bind(careerList[position])
    }

    class CareerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(careerDomain: CareerDomain) {

            itemView.findViewById<TextView>(R.id.companyName).text = careerDomain.company

            itemView.findViewById<TextView>(R.id.workPeriod).text = if (careerDomain.until == 0) {
                String.format(
                    itemView.context.getString(R.string.profile_career_work_since),
                    careerDomain.from
                )
            } else {
                String.format(
                    itemView.context.getString(R.string.profile_career_work_period),
                    careerDomain.from,
                    careerDomain.until
                )
            }

            itemView.findViewById<TextView>(R.id.workPosition).text = careerDomain.position

            itemView.findViewById<TextView>(R.id.workLocation).text = String.format(
                itemView.context.getString(R.string.profile_career_work_location),
                careerDomain.countryName,
                careerDomain.cityName
            )
        }
    }
}