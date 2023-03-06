package com.example.todoapp.ui.home.base

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity :AppCompatActivity() {

    var progressDialog: ProgressDialog?=null

    fun ShowLoadingDialog(){

        progressDialog= ProgressDialog(this)
        progressDialog?.setMessage("Loading....")
        progressDialog?.show()

    }

    fun hideLoading(){
        progressDialog?.dismiss()
    }

    var alertDialog : AlertDialog?=null

    fun ShowMassage(
        message : String,
        posActionTitle : String? =null,
        posAction : DialogInterface.OnClickListener?=null,
        negActionTitle : String?=null,
        negAction  : DialogInterface.OnClickListener?=null,
        cancelable : Boolean=true,
    ){
        var messageDialogBuilder=AlertDialog.Builder(this)
        messageDialogBuilder.setMessage(message)

        if(posActionTitle !=null){
            messageDialogBuilder.setPositiveButton(posActionTitle,
            posAction?:DialogInterface.OnClickListener{dialog,p1->dialog?.dismiss()})
        }

        if(negActionTitle !=null){
            messageDialogBuilder.setNegativeButton(negActionTitle,
            negAction?:DialogInterface.OnClickListener{dialogInterface, i -> dialogInterface?.dismiss() })
        }

        messageDialogBuilder.setCancelable(cancelable)
        alertDialog=messageDialogBuilder.show()
    }

}