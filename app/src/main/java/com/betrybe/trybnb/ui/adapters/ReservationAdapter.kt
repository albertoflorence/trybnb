package com.betrybe.trybnb.ui.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.betrybe.trybnb.R

class ReservationAdapter(private val reservationList: List<ReservationData>) :
    Adapter<ReservationAdapter.ReservationViewHolder>() {

    class ReservationViewHolder(view: View) : ViewHolder(view) {
        val isDepositPaid: ImageView = view.findViewById(R.id.depositpaid_item_reservation)
        val name: TextView = view.findViewById(R.id.name_item_reservation)
        val checkIn: TextView = view.findViewById(R.id.checkin_item_reservation)
        val checkOut: TextView = view.findViewById(R.id.checkout_item_reservation)
        val additionalNeeds: TextView = view.findViewById(R.id.additional_needs_item_reservation)
        val totalPrice: TextView = view.findViewById(R.id.total_price_item_reservation)
    }

    override fun getItemCount(): Int = reservationList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_reservation, parent, false)
        return ReservationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        val item = reservationList[position]
        holder.isDepositPaid.setImageResource(if (item.isDepositPaid)  R.drawable.ic_depositpaid_true else R.drawable.ic_depositpaid_false)
        holder.isDepositPaid.contentDescription = if (item.isDepositPaid) "deposito pago" else "deposito não pago"
        holder.name.text = item.name
        holder.checkIn.text = item.checkIn
        holder.checkOut.text = item.checkOut
        holder.additionalNeeds.text = item.additionalNeeds
        holder.totalPrice.text = item.totalPrice.toString()
    }

}