package com.converter.core.di

import com.converter.core.BuildConfig
import com.converter.core.di.modules.NetworkModule
import com.converter.core.network.services.ConverterService
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit

class NetworkModuleTest {

    private lateinit var networkModule: NetworkModule

    @Before
    fun setUp() {
        networkModule = NetworkModule()
    }

    @Test
    fun verifyProvidedHttpLoggingInterceptor() {
        val interceptor = networkModule.provideHttpLoggingInterceptor()
        assertEquals(HttpLoggingInterceptor.Level.BODY, interceptor.level)
    }

    @Test
    fun verifyProvidedHttpClient() {
        val interceptor = mockk<HttpLoggingInterceptor>()
        val httpClient = networkModule.provideHttpClient(interceptor)

        assertEquals(1, httpClient.interceptors.size)
        assertEquals(interceptor, httpClient.interceptors.first())
    }

    @Test
    fun verifyProvidedRetrofitBuilder() {
        val retrofit = networkModule.provideRetrofitBuilder()

        assertEquals(BuildConfig.BASE_URL, retrofit.baseUrl().toUrl().toString())
    }

    @Test
    fun verifyProvidedConverterService() {
        val retrofit = mockk<Retrofit>()
        val converterService = mockk<ConverterService>()
        val serviceClassCaptor = slot<Class<*>>()

        every { retrofit.create<ConverterService>(any()) } returns converterService

        networkModule.provideConverterService(retrofit)

        verify { retrofit.create(capture(serviceClassCaptor)) }
        assertEquals(ConverterService::class.java, serviceClassCaptor.captured)
    }

    @Test
    fun verifyProvidedConverterRepository() {
        val converterService = mockk<ConverterService>()
        val converterRepository = networkModule.provideConverterRepository(converterService)

        assertEquals(converterService, converterRepository.service)
    }
}
