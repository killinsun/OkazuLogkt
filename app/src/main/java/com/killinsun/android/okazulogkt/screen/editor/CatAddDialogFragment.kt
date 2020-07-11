package com.killinsun.android.okazulogkt.screen.editor

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.killinsun.android.okazulogkt.R
import kotlinx.android.synthetic.main.custom_dialog_fragment.*

class CatAddDialogFragment(val title: String,
                           val description: String,
                           val positiveBtnLabel: String,
                           val negativeBtnLabel: String
): DialogFragment() {

    interface OnClickAddButtonListener {
        fun onClickDialogPositiveButton(categoryName: String)
    }

    var onClickPositiveButtonListener: OnClickAddButtonListener? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)

        when {
            context is OnClickAddButtonListener ->
                onClickPositiveButtonListener = context

            parentFragment is OnClickAddButtonListener ->
                onClickPositiveButtonListener = parentFragment as OnClickAddButtonListener
            else -> throw RuntimeException("$context must implement fffff")
        }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = Dialog(requireContext())
        val dialogWindow = dialog.window
        dialogWindow?.let {
            it.requestFeature(Window.FEATURE_NO_TITLE)
            it.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
            )
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        dialog.setContentView(R.layout.custom_dialog_fragment)

        dialog.dialogTitle.text = title
        dialog.descriptionTv.text = description
        dialog.positiveBtn.text = positiveBtnLabel
        dialog.negativeBtn.text = negativeBtnLabel

        dialog.positiveBtn.setOnClickListener { onClickPositiveButton(dialog.dialogInputTv.text.toString()) }
        dialog.negativeBtn.setOnClickListener { onClickNegativeButton() }

        return dialog
    }

    fun onClickPositiveButton(categoryName: String?) {
        // TODO: Nullならエラーを画面に出力させる
        if(categoryName == null) return

        Log.v("OkazuLog", "onClickPositiveButton $categoryName")
        onClickPositiveButtonListener?.onClickDialogPositiveButton(categoryName)
        dismiss()
    }

    fun onClickNegativeButton() {
        dismiss()
    }

}