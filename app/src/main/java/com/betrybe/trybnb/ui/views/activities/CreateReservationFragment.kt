package com.betrybe.trybnb.ui.views.activities

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.betrybe.trybnb.R
import com.betrybe.trybnb.common.ApiIdlingResource
import com.betrybe.trybnb.data.api.Api
import com.betrybe.trybnb.data.models.BookingDates
import com.betrybe.trybnb.data.models.BookingDetails
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateReservationFragment : Fragment() {

    private lateinit var mFirstNameCreateReservation: TextInputLayout
    private lateinit var mLastNameCreateReservation: TextInputLayout
    private lateinit var mCheckInCreateReservation: TextInputLayout
    private lateinit var mCheckoutCreateReservation: TextInputLayout
    private lateinit var mAdditionalNeedsCreateReservation: TextInputLayout
    private lateinit var mTotalPriceCreateReservation: TextInputLayout
    private lateinit var mDepositPaidCreateReservation: CheckBox
    private lateinit var mCreateReservationButton: Button
    private lateinit var mReservationMessage: TextView

    private val api = Api.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_reservation, container, false)

        mFirstNameCreateReservation = view.findViewById(R.id.first_name_create_reservation)
        mLastNameCreateReservation = view.findViewById(R.id.last_name_create_reservation)
        mCheckInCreateReservation = view.findViewById(R.id.checkin_create_reservation)
        mCheckoutCreateReservation = view.findViewById(R.id.checkout_create_reservation)
        mAdditionalNeedsCreateReservation =
            view.findViewById(R.id.additional_needs_create_reservation)
        mTotalPriceCreateReservation = view.findViewById(R.id.total_price_create_reservation)
        mDepositPaidCreateReservation = view.findViewById(R.id.depositpaid_create_reservation)
        mCreateReservationButton = view.findViewById(R.id.create_reservation_button)
        mReservationMessage = view.findViewById(R.id.reservation_message)

        mCreateReservationButton.setOnClickListener {
            if (!validateCreateReservation()) return@setOnClickListener
            createReservation(
                BookingDetails(
                    mFirstNameCreateReservation.editText?.text.toString(),
                    mLastNameCreateReservation.editText?.text.toString(),
                    mTotalPriceCreateReservation.editText?.text.toString().toDouble(),
                    mDepositPaidCreateReservation.isChecked,
                    BookingDates(
                        mCheckInCreateReservation.editText?.text.toString(),
                        mCheckoutCreateReservation.editText?.text.toString()
                    ),
                    mAdditionalNeedsCreateReservation.editText?.text.toString(),
                )
            )
        }

        return view
    }

    private fun validateCreateReservation(): Boolean {
        return listOf(
            required(mFirstNameCreateReservation, "Nome"),
            required(mLastNameCreateReservation, "Sobrenome"),
            required(mCheckInCreateReservation, "Checkin"),
            required(mCheckoutCreateReservation, "Checkout"),
            required(mAdditionalNeedsCreateReservation, "Necessidades Adicionais"),
            required(mTotalPriceCreateReservation, "Preço Total"),
        ).any { !it }
    }

    private fun required(textView: TextInputLayout, field: String): Boolean {
        val text = textView.editText?.text.toString()
        textView.error = if (text.isBlank()) "O campo $field é obrigatório" else null
        return textView.error != null
    }

    private fun createReservation(booking: BookingDetails) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                ApiIdlingResource.increment()
                withContext(Dispatchers.Main) {
                    api.createBooking(booking)
                    mReservationMessage.visibility = View.VISIBLE
                    ApiIdlingResource.decrement()
                }
            } catch (ex: Exception) {
                ApiIdlingResource.decrement()
                ex.printStackTrace()
            }
        }
    }
}