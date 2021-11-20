package com.converter.dynamicfeatures.characterslist.ui.detail.di

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import com.converter.commons.ui.extensions.viewModel
import com.converter.core.di.scopes.FeatureScope
import com.converter.core.network.repositiories.ConverterRepository
import com.converter.dynamicfeatures.characterslist.ui.detail.ConverterDetailFragment
import com.converter.dynamicfeatures.characterslist.ui.detail.ConverterDetailViewModel
import dagger.Module
import dagger.Provides

@Module
class ConverterDetailModule(
    @VisibleForTesting(otherwise = PRIVATE)
    val fragment: ConverterDetailFragment
) {

    @FeatureScope
    @Provides
    fun providesCharacterDetailViewModel(
        converterRepo: ConverterRepository
    ) = fragment.viewModel {
        ConverterDetailViewModel(
            converterRepo = converterRepo
        )
    }
}
