package com.converter.dynamicfeatures.characterslist.ui.detail

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import androidx.lifecycle.*
import com.converter.core.BuildConfig
import com.converter.core.model.Latest
import com.converter.core.network.repositiories.ConverterRepository
import com.converter.core.network.responses.Resource.*
import com.converter.core.utils.ErrorUtils
import javax.inject.Inject
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * View model responsible for preparing and managing the data for [ConverterDetailFragment].
 *
 * @see ViewModel
 */
class ConverterDetailViewModel @Inject constructor(
    @VisibleForTesting(otherwise = PRIVATE)
    val converterRepo: ConverterRepository
) : ViewModel() {

    var fromCountry = MutableLiveData("AFN")
    var toCountry = MutableLiveData("AFN")

    private val _dataConvert = MutableLiveData<Resource<Latest.Response>>()
    val dataConvert: MutableLiveData<Resource<Latest.Response>>
        get() = _dataConvert

    private val _state = MutableLiveData<ConverterDetailViewState>()
    val state: LiveData<ConverterDetailViewState>
        get() = _state

    fun getConvertAsync(amount: Double) {
        _dataConvert.postValue(Resource.loading(null))
        _state.postValue(ConverterDetailViewState.Loading)
        viewModelScope.launch {
            try {
                val response = converterRepo.getConvertedRateAsync(
                    BuildConfig.KEY_PUBLIC,
                    fromCountry.value.toString(),
                    toCountry.value.toString(),
                    amount
                )
                _dataConvert.postValue(Resource.success(response))
                _state.postValue(ConverterDetailViewState.Converted)
            } catch (t: Throwable) {
                _state.postValue(ConverterDetailViewState.Error)
                _dataConvert.postValue(
                    Resource.error(
                        ErrorUtils.getMsgFromError(t),
                        null,
                        t
                    )
                )
            }
        }
    }
}
