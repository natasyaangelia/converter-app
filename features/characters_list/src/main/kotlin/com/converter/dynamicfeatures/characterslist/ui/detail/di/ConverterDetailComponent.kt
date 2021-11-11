package com.converter.dynamicfeatures.characterslist.ui.detail.di

import com.converter.core.di.CoreComponent
import com.converter.core.di.scopes.FeatureScope
import com.converter.dynamicfeatures.characterslist.ui.detail.ConverterDetailFragment
import dagger.Component

/**
 * Class for which a fully-formed, dependency-injected implementation is to
 * be generated from [ConverterDetailModule].
 *
 * @see Component
 */
@FeatureScope
@Component(
    modules = [ConverterDetailModule::class],
    dependencies = [CoreComponent::class]
)
interface ConverterDetailComponent {

    /**
     * Inject dependencies on component.
     *
     * @param detailFragment Detail component.
     */
    fun inject(detailFragment: ConverterDetailFragment)
}
