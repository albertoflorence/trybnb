package com.betrybe.trybnb.ui.views.activities

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.betrybe.trybnb.R
import com.betrybe.trybnb.common.ApiIdlingResource
import com.betrybe.trybnb.data.api.Api
import com.betrybe.trybnb.data.models.BookingResponse
import com.betrybe.trybnb.ui.adapters.ReservationAdapter
import com.betrybe.trybnb.ui.adapters.ReservationData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReservationFragment : Fragment() {
    private lateinit var mReservationList: RecyclerView
    private val api = Api.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reservation, container, false)
        mReservationList = view.findViewById(R.id.reservation_recycler_view)

        mReservationList.layoutManager = LinearLayoutManager(context)
        fetchReservations()

        return view
    }

    private suspend fun getReservationsDetails(data: List<BookingResponse>): List<ReservationData> {
        val reservationList = mutableListOf<ReservationData>()

        data.forEach {
            val response = api.booking(it.bookingid)
            if (response.isSuccessful) {
                val r = response.body() ?: return@forEach
                reservationList.add(
                    ReservationData(
                        r.firstname + r.lastname,
                        r.bookingdates.checkin,
                        r.bookingdates.checkout,
                        r.additionalneeds,
                        r.totalprice,
                        r.depositpaid
                    )
                )
            } else {
                reservationList.add(
                    ReservationData(
                        it.bookingid,
                        "12/05/1992",
                        "18/05/1992",
                        "Api está quebrada ):",
                        100.0,
                        java.util.Random().nextBoolean()
                    )
                )
            }
        }
        return reservationList
    }


    private suspend fun getReservations(): List<BookingResponse> {
        val response = api.booking()
        if (response.isSuccessful) {
            val result = response.body() ?: return emptyList()
            return result.slice(0..5)
        }
        return emptyList()
    }

    private fun fetchReservations() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                ApiIdlingResource.increment()
                withContext(Dispatchers.Main) {
                    val ids = getReservations()
                    val reservationsList = getReservationsDetails(ids)
                    mReservationList.adapter = ReservationAdapter(reservationsList)
                }
                ApiIdlingResource.decrement()
            } catch (ex: Exception) {
                ApiIdlingResource.decrement()
                ex.printStackTrace()
            }
        }
    }

}