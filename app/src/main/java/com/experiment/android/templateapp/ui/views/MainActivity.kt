package com.experiment.android.templateapp.ui.views

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.experiment.android.templateapp.R
import com.experiment.android.templateapp.databinding.ActivityMainBinding
import com.experiment.android.templateapp.ui.viewmodels.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    private var fromItem: String = "EUR"
    private var toItem: String = "USD"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setSupportActionBar(binding.toolbar)

        setupViews()

        sendRequest()

        observeViewState()

    }

    private fun setupViews() {

        val value = binding.etAmountToConvert.text.toString()
        if (!value.isNullOrBlank())
            mainActivityViewModel.setCurrencyValue(value.toDouble())

        ArrayAdapter.createFromResource(
            this,
            R.array.currency_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.fromSpinner.adapter = adapter
        }
        binding.fromSpinner.onItemSelectedListener = this

        ArrayAdapter.createFromResource(
            this,
            R.array.currency_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.toSpinner.adapter = adapter
        }
        binding.fromSpinner.onItemSelectedListener = this
        binding.toSpinner.onItemSelectedListener = this

    }

    /**
     * Method to send request on send request button clicked
     */
    private fun sendRequest() {
        binding.btnSendRequest.setOnClickListener {
            mainActivityViewModel.getDataFromRemote(fromItem, toItem)
        }
    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    private fun observeViewState() {
        mainActivityViewModel.state.observe(this, Observer { state ->
            when (state) {
                is ViewState.ShowData -> {
                    showData(state.data)
                }
                is ViewState.ShowError -> {
                    showError(state.error)
                }
            }
        })
    }

    private fun showError(error: Throwable) {
        binding.tvResult.text = error.message
    }

    private fun showData(data: String) {
        binding.tvResult.text = data
    }

    override fun onItemSelected(
        adapterView: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long
    ) {
        println("Item selected $id $position")
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}