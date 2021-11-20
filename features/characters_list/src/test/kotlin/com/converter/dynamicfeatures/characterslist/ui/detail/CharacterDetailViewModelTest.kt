package com.converter.dynamicfeatures.characterslist.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.converter.core.model.Latest
import com.converter.core.network.repositiories.ConverterRepository
import com.converter.core.network.responses.Resource
import com.converter.libraries.testutils.rules.CoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ConverterDetailViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK(relaxed = true)
    lateinit var converterRepository: ConverterRepository
    @MockK(relaxed = true)
    lateinit var stateObserver: Observer<ConverterDetailViewState>
    @MockK(relaxed = true)
    lateinit var dataObserver: Observer<Resource.Resource<Latest.Response>>
    lateinit var viewModel: ConverterDetailViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = ConverterDetailViewModel(
            converterRepo = converterRepository
        )
        viewModel.state.observeForever(stateObserver)
        viewModel.dataConvert.observeForever(dataObserver)
    }

    @Test
    fun loadConverter_ShouldSetLoadingState() {
        viewModel.fromCountry.postValue("USD")
        viewModel.toCountry.postValue("IDR")
        viewModel.getConvertAsync(40.0)

        verify { stateObserver.onChanged(ConverterDetailViewState.Loading) }
    }

    @Test
    fun loadConverter_WhenError_ShouldBeErrorState() {
        viewModel.fromCountry.postValue("USD")
        viewModel.toCountry.postValue("IDR")
        viewModel.getConvertAsync(40.0)

        val expectedState: ConverterDetailViewState = ConverterDetailViewState.Error
        Assert.assertEquals(expectedState, viewModel.state.value)
        verify { stateObserver.onChanged(expectedState) }
    }

    @Test
    fun loadConverter_WhenSuccess_ShouldBeConvertedState() {
        viewModel.fromCountry.postValue("USD")
        viewModel.toCountry.postValue("IDR")
        viewModel.getConvertAsync(40.0)

        val expectedState: ConverterDetailViewState = ConverterDetailViewState.Converted
        Assert.assertEquals(expectedState, viewModel.state.value)
        verify { stateObserver.onChanged(expectedState) }
    }
}
