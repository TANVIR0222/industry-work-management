package com.example.to_do.utile

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.to_do.R

object utile {
    private var dialog : AlertDialog ? = null

    fun showDialog (context: Context){
        dialog = AlertDialog.Builder(context).setView(R.layout.dialog_bar).setCancelable(false).create()
        dialog!!.setCanceledOnTouchOutside(true)
        dialog !!.show()

    }

    fun hideDialog (){

        dialog?.dismiss()

    }

    fun showToast(contect:Context , message : String){
        Toast.makeText(contect, message, Toast.LENGTH_SHORT).show()

    }
}