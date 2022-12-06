package com.example.mercedezyelptest.view

import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.FragmentActivity
import com.example.mercedezyelptest.R
import com.example.mercedezyelptest.databinding.BusinessDetailItemLayoutBinding
import com.example.mercedezyelptest.databinding.BusinessItemLayoutBinding
import com.google.android.material.snackbar.Snackbar

fun BusinessItemLayoutBinding.provideStringResource(stringId: Int, template: String): String{
    return this.root.context.getString(stringId, template)
}
fun BusinessDetailItemLayoutBinding.provideStringResource(stringId: Int, template: String): String{
    return this.root.context.getString(stringId, template)
}
fun FragmentActivity.navigateToList(lat: Double, lon: Double){
    supportFragmentManager.beginTransaction()
        .replace(R.id.container, BusinessListFragment.newInstance(lat, lon))
        .commit()
}
fun FragmentActivity.navigateToDetails(position: Int){
    supportFragmentManager.beginTransaction()
        .replace(R.id.container, BusinessDetailFragment.newInstance(position))
        .commit()
}
fun FragmentActivity.displayErrorMessage(errorMessage: String){
    Snackbar.make(
        findViewById(R.id.container),
        errorMessage,
        Snackbar.LENGTH_INDEFINITE
    ).setAction(
        "Please refresh the list"
    ){  }
        .show()
}
fun FragmentActivity.showLoading(loading: Boolean){
    if (loading)
        findViewById<ProgressBar>(R.id.loading).visibility = View.VISIBLE
    else
        findViewById<ProgressBar>(R.id.loading).visibility = View.INVISIBLE
}