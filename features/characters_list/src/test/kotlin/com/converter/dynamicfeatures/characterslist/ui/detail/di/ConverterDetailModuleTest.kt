package com.converter.dynamicfeatures.characterslist.ui.detail.di

import androidx.lifecycle.ViewModel
import com.converter.commons.ui.extensions.viewModel
import com.converter.core.network.repositiories.ConverterRepository
import com.converter.dynamicfeatures.characterslist.ui.detail.*
import io.mockk.*
import io.mockk.impl.annotations.*
import org.junit.*
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class ConverterDetailModuleTest {

    @MockK
    lateinit var fragment: ConverterDetailFragment
    lateinit var module: ConverterDetailModule

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun initializeConverterDetailModule_ShouldSetUpCorrectly() {
        module = ConverterDetailModule(fragment)

        assertEquals(fragment, module.fragment)
    }

    @Test
    fun verifyProvidedConverterDetailViewModel() {
        mockkStatic("com.converter.commons.ui.extensions.FragmentExtensionsKt")

        every {
            fragment.viewModel(any(), any<() -> ViewModel>())
        } returns mockk<ConverterDetailViewModel>()

        val factoryCaptor = slot<() -> ConverterDetailViewModel>()
        val converterRepository = mockk<ConverterRepository>(relaxed = true)
        module = ConverterDetailModule(fragment)
        module.providesCharacterDetailViewModel(
            converterRepo = converterRepository
        )

        verify {
            fragment.viewModel(factory = capture(factoryCaptor))
        }

        factoryCaptor.captured().run {
            assertEquals(converterRepository, this.converterRepo)
        }
    }
}
