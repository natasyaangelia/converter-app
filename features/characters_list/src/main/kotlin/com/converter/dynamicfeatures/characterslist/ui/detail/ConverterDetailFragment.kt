package com.converter.dynamicfeatures.characterslist.ui.detail

import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.converter.android.SampleApp.Companion.coreComponent
import com.converter.commons.ui.base.BaseFragment
import com.converter.commons.ui.extensions.observe
import com.converter.core.model.Latest
import com.converter.core.network.responses.Resource.*
import com.converter.core.utils.Utility
import com.converter.core.utils.Utility.hideKeyboard
import com.converter.dynamicfeatures.characterslist.R
import com.converter.dynamicfeatures.characterslist.databinding.FragmentCharacterDetailBinding
import com.converter.dynamicfeatures.characterslist.ui.detail.di.ConverterDetailModule
import com.converter.dynamicfeatures.characterslist.ui.detail.di.DaggerConverterDetailComponent
import java.util.*
import javax.inject.Inject

class ConverterDetailFragment :
    BaseFragment<FragmentCharacterDetailBinding, ConverterDetailViewModel>(
        layoutId = R.layout.fragment_character_detail
    ) {

    private lateinit var binding: FragmentCharacterDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.dataConvert, ::onViewConverterChange)
        onReadyAction()
    }

    override fun onInitDependencyInjection() {
        DaggerConverterDetailComponent
            .builder()
            .coreComponent(coreComponent(requireContext()))
            .converterDetailModule(ConverterDetailModule(this))
            .build()
            .inject(this)
    }

    override fun onInitDataBinding() {
        binding = getViewDataBinding()
        viewBinding.viewModel = viewModel
    }

    private fun onViewConverterChange(viewState: Resource<Latest.Response>) {
        when (viewState.status) {
            Status.LOADING -> {
                hideKeyboard(requireActivity())
                showLoadingBar()
            }
            Status.SUCCESS -> {
                hideLoadingBar()
                viewState.data?.let {
                    if (it.status == "success") {
                        it.rates?.let { rates ->
                            val map = rates
                            map.keys.forEach {

                                val rateForAmount = map[it]?.rate_for_amount
                                val formattedString = String.format("%,.2f", rateForAmount)
                                binding.etSecondCurrency.setText(formattedString)

                            }
                        }

                    } else {
                        showSnackbar("Ooops! something went wrong, Try again")
                    }
                } ?: run {
                    viewState.message?.let { msg -> showSnackbar(msg) }
                }
            }
            Status.ERROR -> {
                hideLoadingBar()
                viewState.message?.let { msg -> showSnackbar(msg) }
            }
            else -> {
                hideLoadingBar()
            }
        }
    }

    private fun onReadyAction() {
        initSpinner()

        binding.btnConvert.setOnClickListener() {

            //check if the input is empty
            val numberToConvert = binding.etFirstCurrency.text.toString()

            if(numberToConvert.isEmpty() || numberToConvert == "0"){
                showSnackbar("Input a value in the first text field, result will be shown in the second text field")
            }

            //check if internet is available
            else if (!Utility.isNetworkAvailable(requireContext())){
                showSnackbar("You are not connected to the internet")
            }

            //carry on and convert the value
            else{
                val amount = binding.etFirstCurrency.text.toString().toDouble()

                viewModel.getConvertAsync(amount)
            }
        }

    }

    private fun showSnackbar(message: String) {
        Snackbar.make(
            requireView(),
            message,
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun showLoadingBar() {
        binding.prgLoading.visibility = View.VISIBLE
        binding.btnConvert.visibility = View.GONE
    }

    private fun hideLoadingBar() {
        binding.prgLoading.visibility = View.GONE
        binding.btnConvert.visibility = View.VISIBLE
    }

    private fun initSpinner(){

        //set items in the spinner i.e a list of all countries
        binding.spnFirstCountry.setItems( getAllCountries() )

        //hide key board when spinner shows (For some weird reasons, this isn't so effective as I am using a custom Material Spinner)
        binding.spnFirstCountry.setOnClickListener {
            hideKeyboard(requireActivity())
        }

        //Handle selected item, by getting the item and storing the value in a  variable - selectedItem1
        binding.spnFirstCountry.setOnItemSelectedListener { view, position, id, item ->
            //Set the currency code for each country as hint
            val countryCode = getCountryCode(item.toString())
            val currencySymbol = getSymbol(countryCode)
            viewModel.fromCountry.value = currencySymbol.toString()
        }

        //hide key board when spinner shows
        binding.spnSecondCountry.setOnClickListener {
            hideKeyboard(requireActivity())
        }

        //set items on second spinner i.e - a list of all countries
        binding.spnSecondCountry.setItems( getAllCountries() )


        //Handle selected item, by getting the item and storing the value in a  variable - selectedItem2,
        binding.spnSecondCountry.setOnItemSelectedListener { view, position, id, item ->
            //Set the currency code for each country as hint
            val countryCode = getCountryCode(item.toString())
            val currencySymbol = getSymbol(countryCode)
            viewModel.toCountry.value = currencySymbol.toString()
        }

    }

    /**
     * A method for getting a country's code from the country name
     * e.g Nigeria - NG
     */

    private fun getCountryCode(countryName: String) = Locale.getISOCountries().find { Locale("", it).displayCountry == countryName }


    /**
     * A method for getting all countries in the world - about 256 or so
     */

    private fun getAllCountries(): ArrayList<String> {

        val locales = Locale.getAvailableLocales()
        val countries = ArrayList<String>()
        for (locale in locales) {
            val country = locale.displayCountry
            if (country.trim { it <= ' ' }.isNotEmpty() && !countries.contains(country)) {
                countries.add(country)
            }
        }
        countries.sort()

        return countries
    }

    /**
     * A method for getting a country's currency symbol from the country's code
     * e.g USA - USD
     */

    private fun getSymbol(countryCode: String?): String? {
        val availableLocales = Locale.getAvailableLocales()
        for (i in availableLocales.indices) {
            if (availableLocales[i].country == countryCode
            ) return Currency.getInstance(availableLocales[i]).currencyCode
        }
        return ""
    }
}
