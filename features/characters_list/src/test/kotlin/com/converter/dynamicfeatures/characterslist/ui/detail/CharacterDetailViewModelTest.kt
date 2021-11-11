package com.converter.dynamicfeatures.characterslist.ui.detail

import com.converter.core.model.Latest
import com.converter.core.network.responses.Resource
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
    lateinit var stateObserver: Observer<ConverterDetailViewState>
    @MockK(relaxed = true)
    lateinit var dataObserver: Observer<Latest.Response>
    lateinit var viewModel: ConverterDetailViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = ConverterDetailViewModel(
            converterRepository = converterRepository
        )
        viewModel.state.observeForever(stateObserver)
        viewModel.data.observeForever(dataObserver)
    }

    @Test
    fun loadCharacterDetail_ShouldSetLoadingState() {
        viewModel.getConvertAsync(40.0)

        verify { stateObserver.onChanged(viewModel.dataConvert.value.status == Resource.Status.LOADING) }
    }

    @Test
    fun loadCharacterDetail_WhenError_ShouldBeErrorState() {
        viewModel.getConvertAsync(40.0)

        val expectedState: Resource = Resource.Status.ERROR
        Assert.assertEquals(expectedState, viewModel.dataConvert.value.status)
        verify { stateObserver.onChanged(expectedState) }
    }

    @Test
    fun loadCharacterDetail_WhenSuccess_ShouldPostDataResult() {
        val characterResponse = mockk<Resource<Latest.Response>>()
        coEvery { converterRepository.getConvertedRateAsync(any(), any(), any(), any()) } returns characterResponse
    }
}
