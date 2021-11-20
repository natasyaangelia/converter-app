package com.converter.dynamicfeatures.characterslist.ui.detail

import com.converter.commons.ui.base.BaseViewState

sealed class ConverterDetailViewState : BaseViewState {

    object Loading : ConverterDetailViewState()

    object Error : ConverterDetailViewState()

    object Converted : ConverterDetailViewState()

    fun isLoading() = this is Loading

    fun isError() = this is Error

    fun isConverted() = this is Converted

}
