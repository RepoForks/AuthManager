package com.github.pavlospt.signindemo

import android.support.design.widget.BaseTransientBottomBar
import android.support.design.widget.MyBaseTransientBottomBar
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorListener
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.Button


class CredentialsSnackBar private constructor(parent: ViewGroup, content: View, contentViewCallback: ContentViewCallback) : MyBaseTransientBottomBar<CredentialsSnackBar>(parent, content, contentViewCallback) {

    companion object {

        @JvmStatic
        fun make(parent: ViewGroup,
                 @Duration duration: Int,
                 useButtonListener: () -> Unit = {},
                 deleteButtonListener: () -> Unit = {}
        ): CredentialsSnackBar {

            val inflater: LayoutInflater = LayoutInflater.from(parent.context)
            val contentView = inflater.inflate(R.layout.layout_credentials_buttons, parent, false)

            val contentViewCallback = ContentViewCallback(contentView)
            val credentialSnackbar = CredentialsSnackBar(parent, contentView, contentViewCallback)

            credentialSnackbar.duration = duration
            credentialSnackbar.setUseClickListener(useButtonListener)
            credentialSnackbar.setDeleteClickListener(deleteButtonListener)

            return credentialSnackbar
        }
    }

    fun setUseClickListener(useButtonListener: () -> Unit) {
        view
            .findViewById(R.id.use_button)?.setOnClickListener {
            useButtonListener()
            dismiss()
        }
    }

    fun setDeleteClickListener(deleteButtonListener: () -> Unit) {
        view
            .findViewById(R.id.delete_button)?.setOnClickListener {
            deleteButtonListener()
            dismiss()
        }
    }

    class ContentViewCallback(content: View?) : MyBaseTransientBottomBar.ContentViewCallback {

        private var useButton: Button
        private var deleteButton: Button

        init {
            useButton = content?.findViewById(R.id.use_button) as Button
            deleteButton = content?.findViewById(R.id.delete_button) as Button
        }

        override fun animateContentOut(delay: Int, duration: Int) {
            ViewCompat.animate(useButton).alpha(0f).setDuration(400).start()
            ViewCompat.animate(deleteButton).alpha(0f).setDuration(400).start()
        }

        override fun animateContentIn(delay: Int, duration: Int) {
            useButton.alpha = 0f
            deleteButton.alpha = 0f

            ViewCompat
                .animate(useButton)
                .alpha(1f)
                .setDuration(400)
                .start()

            ViewCompat
                .animate(deleteButton)
                .alpha(1f)
                .setDuration(400)
                .start()
        }

    }

}