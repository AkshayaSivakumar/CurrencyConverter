package com.experiment.android.templateapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.experiment.android.templateapp.data.repo.DataRepository
import com.experiment.android.templateapp.ui.views.ViewState
import com.experiment.android.templateapp.utils.coroutines.ResultHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val dataRepository: DataRepository
) :
    ViewModel() {

    private val _state: MutableLiveData<ViewState> = MutableLiveData()
    val state: LiveData<ViewState> = _state

    init {
        _state.postValue(ViewState.ShowLoading)
    }

    private var currencyValue: Double = 0.0

    fun setCurrencyValue(value: Double) {
        currencyValue = value
    }


    fun convertCurrency(from: String, to: String) {
        getMultiplierData(from, to)
    }

    private fun getMultiplierData(from: String, to: String) {
        viewModelScope.launch {
            when (val response = dataRepository.getData(from, to)) {
                is ResultHandler.Error -> {
                    val throwable = response.throwable
                    Timber.d(throwable.toString())
                    _state.postValue(
                        ViewState.ShowError(
                            throwable
                        )
                    )
                }
                is ResultHandler.Success -> {
                    val result = response.data
                    Timber.d(result.toString())
                    val convertedCurrency =
                        convertCurrency(currencyValue, result.multiplier)
                    Timber.d("Converted Value $convertedCurrency")
                    _state.postValue(ViewState.ShowData(convertedCurrency))
                }
            }
        }
    }

    private fun convertCurrency(currencyValue: Double, multiplier: Double): String =
        (currencyValue * multiplier).toString()
}