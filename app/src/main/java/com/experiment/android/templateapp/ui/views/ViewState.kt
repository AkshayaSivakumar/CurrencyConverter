package com.experiment.android.templateapp.ui.views

import com.experiment.android.templateapp.models.ResponseData

sealed class ViewState {
    object ShowLoading : ViewState()
    class ShowError(val error: Throwable) : ViewState()
    class ShowData(val data: String) : ViewState()
}