package com.converter.core.di

import android.content.Context
import com.converter.core.di.modules.ContextModule
import com.converter.core.di.modules.NetworkModule
import com.converter.core.di.modules.UtilsModule
import com.converter.core.network.repositiories.ConverterRepository
import com.converter.core.network.services.ConverterService
import com.converter.core.utils.ThemeUtils
import dagger.Component
import javax.inject.Singleton

/**
 * Core component that all module's components depend on.
 *
 * @see Component
 */
@Singleton
@Component(
    modules = [
        ContextModule::class,
        NetworkModule::class,
        UtilsModule::class
    ]
)
interface CoreComponent {

    fun context(): Context

    fun converterService(): ConverterService

    fun converterRepository(): ConverterRepository

    fun themeUtils(): ThemeUtils
}
