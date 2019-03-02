package com.example.firemap

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.app.AlertDialog
import android.support.v4.app.DialogFragment

class FailureAlertDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setTitle(R.string.dialog_confirm_failure)
                .setPositiveButton(R.string.ok,
                    DialogInterface.OnClickListener { dialog, id ->
                        // Confirmation - do nothing
                        dismissAllowingStateLoss()
                    })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
