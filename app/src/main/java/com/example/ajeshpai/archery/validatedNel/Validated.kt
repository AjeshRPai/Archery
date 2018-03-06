package com.example.ajeshpai.archery.validatedNel

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.widget.Toast
import arrow.data.*
import arrow.data.Validated
import arrow.syntax.validated.invalid
import arrow.syntax.validated.valid
import com.example.ajeshpai.archery.R
import kotlinx.android.synthetic.main.activity_validated.*
import java.util.regex.Pattern

class Validated : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_validated)

        signup_button.setOnClickListener {
            onValidation(validateData(email.text.toString(), number.text.toString()))
        }
    }

    private fun clearErrors() {
        emailWrapper.error = null
        numberWrapper.error = null
    }

    private fun handleInvalid(errors: Nel<ValidationError>) {
        errors.map { handleInvalidField(it) }.all.forEach { it.second.error = it.first }
    }

    private fun handleInvalidField(validationError: ValidationError): Pair<String, TextInputLayout> =
            when (validationError) {
                ValidationError.InvalidMail -> Pair(getString(R.string.invalid_mail), emailWrapper)
                ValidationError.InvalidPhoneNumber -> Pair(getString(R.string.invalid_phone_number), numberWrapper)
            }

    private fun handleValid(data: Data) {
        val message = "Mail: ${data.mail} Phone: ${data.phone}"
        showMessage(message)
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun onValidation(valid:Validated<Nel<ValidationError>, Data>){
        clearErrors()
        valid.fold(this::handleInvalid, this::handleValid)
    }



    fun validateData(mail: String, phoneNumber: String): Validated<Nel<ValidationError>, Data> {

        return Validated.applicative<Nel<ValidationError>>()
                    .map2(mail.validatedMail(),
                    phoneNumber.validatedPhoneNumber()) {
            Data(it.a, it.b)
        }.ev()

    }

    private fun String.validatedMail(): Validated<Nel<ValidationError>, String> =
            when {
                validMail(this) -> this.valid()
                else -> ValidationError.InvalidMail.nel().invalid()
            }

    private fun String.validatedPhoneNumber(): Validated<Nel<ValidationError>, String> =
            when {
                validNumber(this) -> this.valid()
                else -> ValidationError.InvalidPhoneNumber.nel().invalid()
            }


    sealed class ValidationError {
        object InvalidMail : ValidationError()
        object InvalidPhoneNumber : ValidationError()
    }

    data class Data(val mail: String, val phone: String)

    val VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)!!
    val VALID_PHONE_NUMBER_ADDRESS_REGEX = Pattern.compile("\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}", Pattern.CASE_INSENSITIVE)!!


    fun validMail(input: String): Boolean {
        val matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(input)
        return matcher.find()
    }

    fun validNumber(input: String): Boolean {
        val matcher = VALID_PHONE_NUMBER_ADDRESS_REGEX.matcher(input)
        return matcher.find()
    }

}
