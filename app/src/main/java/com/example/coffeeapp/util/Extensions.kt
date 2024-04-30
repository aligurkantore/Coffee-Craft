package com.example.coffeeapp.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.coffeeapp.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun ImageView.loadImage(url: String) {
    Glide.with(this.context)
        .load(url)
        .centerCrop()
        .into(this)
}

fun <T> LiveData<T>.observeNonNull(owner: LifecycleOwner, observer: (T) -> Unit) {
    this.observe(owner) { data ->
        data?.let(observer)
    }
}

fun EditText.togglePasswordVisibility() {
    val isVisible = transformationMethod != null
    val drawableId = if (isVisible) R.drawable.ic_eye_open else R.drawable.ic_eye_close
    val lock = ContextCompat.getDrawable(context, R.drawable.ic_lock)
    val drawable = ContextCompat.getDrawable(context, drawableId)
    transformationMethod =
        if (isVisible) null else PasswordTransformationMethod.getInstance()
    setSelection(text?.length ?: 0)
    drawable?.let { setCompoundDrawablesWithIntrinsicBounds(lock, null, it, null) }
}

@SuppressLint("InflateParams")
fun Fragment.setUpBottomSheetDialog(
    onSendClick: (String) -> Unit,
) {
    val dialog = BottomSheetDialog(requireContext(), R.style.DialogStyle)
    val view = layoutInflater.inflate(R.layout.fragment_forgot_password_bottom_sheet_dialog, null)
    dialog.setContentView(view)
    dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
    dialog.show()

    val edmail = view.findViewById<EditText>(R.id.edit_text_send_email)
    val buttonSend = view.findViewById<AppCompatButton>(R.id.button_send)
    val buttonCancel = view.findViewById<AppCompatButton>(R.id.button_cancel)

    buttonSend.setOnClickListener {
        val email = edmail.text.toString().trim()
        onSendClick(email)
        dialog.dismiss()
    }

    buttonCancel.setOnClickListener {
        dialog.dismiss()
    }
}


fun Context.changeLanguage(languageCode: String): Boolean {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)
    val config = Configuration()
    config.locale = locale
    resources.updateConfiguration(config, resources.displayMetrics)
    return true
}

fun formatDate(date: Date): String {
    val sdf = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
    return sdf.format(date)
}

fun showMessage(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_LONG).show()
}

fun Context.showCustomClickableToast(message: String) {
    val inflater = LayoutInflater.from(this)
    val layout: View = inflater.inflate(R.layout.custom_toast, null)

    val text: TextView = layout.findViewById(R.id.text_toast_message)
    text.text = message

    with (Toast(this)) {
        setGravity(Gravity.CENTER_VERTICAL, 0, 0)
        duration = Toast.LENGTH_LONG
        view = layout
        show()
    }
/*
    layout.setOnClickListener {
        val intent = Intent(this@showCustomClickableToast, destination)
        startActivity(intent)
    }

 */
}

fun String.containsTurkishCharacters(): Boolean {
    val turkishCharacters = listOf('ç', 'ğ', 'ı', 'ö', 'ş', 'ü', 'Ç', 'Ğ', 'İ', 'Ö', 'Ş', 'Ü')
    return any { turkishCharacters.contains(it) }
}

fun Fragment.navigateSafe(id: Int) {
    findNavController().navigate(id)
}

fun Fragment.navigateSafeWithArgs(id: Int, args: Bundle) {
    findNavController().navigate(id, args)
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.inVisible() {
    visibility = View.INVISIBLE
}

infix fun View.visibleIf(b: Boolean) {
    if (b) visible() else gone()
}

infix fun View.inVisibleIf(b: Boolean) {
    if (b) inVisible() else visible()
}

infix fun View.goneIf(b: Boolean) {
    if (b) gone() else visible()
}