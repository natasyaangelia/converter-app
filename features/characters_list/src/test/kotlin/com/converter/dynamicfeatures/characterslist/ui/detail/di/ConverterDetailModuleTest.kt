package com.converter.dynamicfeatures.characterslist.ui.detail.di

import com.converter.core.network.repositiories.ConverterRepository
import com.converter.dynamicfeatures.characterslist.ui.detail.*
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.*
import org.junit.*

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
        module.providesConverterDetailViewModel(
            converterRepository = converterRepository
        )

        verify {
            fragment.viewModel(factory = capture(factoryCaptor))
        }

        factoryCaptor.captured().run {
            assertEquals(converterRepository, this.converterRepository)
        }
    }
}
