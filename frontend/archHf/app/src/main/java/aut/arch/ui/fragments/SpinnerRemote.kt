package aut.arch.ui.fragments

import android.view.View
import aut.arch.databinding.ActivityMainBinding

object SpinnerRemote {

    private lateinit var activityMainBinding: ActivityMainBinding

    fun startSpinner(){
        activityMainBinding.loginProgressSpinnerLayout.visibility = View.VISIBLE
        activityMainBinding.executePendingBindings()
    }

    fun stopSpinner(){
        activityMainBinding.loginProgressSpinnerLayout.visibility = View.GONE
        activityMainBinding.executePendingBindings()
    }

    fun setBinding(activityMainBinding: ActivityMainBinding){
        this.activityMainBinding = activityMainBinding
    }
}