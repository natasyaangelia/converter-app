package com.converter.dynamicfeatures.characterslist.ui.detail

import org.junit.Assert
import org.junit.Test

class ConverterDetailViewStateTest {

    lateinit var state: ConverterDetailViewState

    @Test
    fun setStateAsLoading_ShouldBeSettled() {
        state = ConverterDetailViewState.Loading

        Assert.assertTrue(state.isLoading())
        Assert.assertFalse(state.isError())
        Assert.assertFalse(state.isConverted())
    }

    @Test
    fun setStateAsError_ShouldBeSettled() {
        state = ConverterDetailViewState.Error

        Assert.assertFalse(state.isLoading())
        Assert.assertTrue(state.isError())
        Assert.assertFalse(state.isConverted())
    }

    @Test
    fun setStateAsConverted_ShouldBeSettled() {
        state = ConverterDetailViewState.Converted

        Assert.assertFalse(state.isLoading())
        Assert.assertTrue(state.isConverted())
        Assert.assertFalse(state.isError())
    }
}