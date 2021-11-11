package com.converter.dynamicfeatures.characterslist.ui.detail

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.converter.core.BuildConfig
import com.converter.core.model.Latest
import com.converter.core.network.repositiories.ConverterRepository
import com.converter.core.network.responses.Resource.*
import com.converter.core.utils.ErrorUtils
import javax.inject.Inject
import kotlinx.coroutines.launch

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

    fun getConvertAsync(amount: Double) {
        _dataConvert.postValue(Resource.loading(null))
        viewModelScope.launch {
            try {
                val response = converterRepo.getConvertedRateAsync(
                    BuildConfig.KEY_PUBLIC,
                    fromCountry.value.toString(),
                    toCountry.value.toString(),
                    amount
                )
                _dataConvert.postValue(Resource.success(response))
            } catch (t: Throwable) {
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
