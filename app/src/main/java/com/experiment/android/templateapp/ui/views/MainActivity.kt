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

        setupSpinners()

        observeViewState()

    }

    private fun setupSpinners() {

        ArrayAdapter.createFromResource(
            this,
            R.array.currency_array,
            R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(R.layout.simple_spinner_item)
            // Apply the adapter to the spinner
            binding.fromSpinner.adapter = adapter
        }
        binding.fromSpinner.onItemSelectedListener = this

        ArrayAdapter.createFromResource(
            this,
            R.array.currency_array,
            R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(R.layout.simple_spinner_item)
            // Apply the adapter to the spinner
            binding.toSpinner.adapter = adapter
        }
        binding.fromSpinner.onItemSelectedListener = this
        binding.toSpinner.onItemSelectedListener = this

        //TODO("Implement error handling when currency value to convert is null or empty or 0")

    }

    /**
     * Method to send request on send request button clicked
     */
    fun sendRequest(view: View) {
        val value = binding.etAmountToConvert.text.toString()
        if (!value.isNullOrBlank())
            mainActivityViewModel.setCurrencyValue(value.toDouble())

        mainActivityViewModel.convertCurrency(fromItem, toItem)
    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    private fun observeViewState() {
        mainActivityViewModel.state.observe(this, Observer { state ->
            if (state is ViewState.ShowData) {
                showData(state.data)
            } else if (state is ViewState.ShowError) {
                showError(state.error)
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
        parent: AdapterView<*>,
        view: View?,
        position: Int,
        id: Long
    ) {
        if (R.id.from_spinner == parent.id) {
            if (getString(R.string.select) != parent.getItemAtPosition(position))
                fromItem = parent.getItemAtPosition(position).toString()
        } else if (R.id.to_spinner == parent.id) {
            if (getString(R.string.select) != parent.getItemAtPosition(position))
                toItem = parent.getItemAtPosition(position).toString()
            //TODO("Implement error handling when 1st and 2nd spinner item are same")
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }
}