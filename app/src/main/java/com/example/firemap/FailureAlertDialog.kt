package com.example.firemap

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.app.AlertDialog
import android.support.v4.app.DialogFragment

class FailureAlertDialog : DialogFragment() {

    private var dialogTitle: CharSequence = "Conversion Failed"  // defaults
    private var dialogMessage: CharSequence? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            if (dialogMessage != null) { builder.setMessage(dialogMessage) }
            builder.setTitle(dialogTitle)
                .setPositiveButton(R.string.ok,
                    DialogInterface.OnClickListener { dialog, id ->
                        // Require Confirmation - do nothing
                        dismissAllowingStateLoss()
                    })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    // Fragments should all have empty constructors, so we must provide setters for the title/dialogMessage
    // String -> CharSequence coercion
    fun setTitle(title: String) {
        dialogTitle = title
    }

    fun setMessage(message: String) {
        dialogMessage = message
    }

}
