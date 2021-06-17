package com.ldh.starwars_mvvm_clone.activities

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import com.ldh.starwars_mvvm_clone.R
import com.ldh.starwars_mvvm_clone.base.BaseActivity
import com.ldh.starwars_mvvm_clone.commons.hide
import com.ldh.starwars_mvvm_clone.commons.show
import com.ldh.starwars_mvvm_clone.databinding.ActivityDashboardBinding
import com.ldh.starwars_mvvm_clone.models.states.DashboardSearchViewState
import com.ldh.starwars_mvvm_clone.viewmodel.DashboardSearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class DashboardActivity : BaseActivity() {

    private val characterSearchViewModel by viewModel<DashboardSearchViewModel>()

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)

        bindViews()
        observeViews()
    }

    private fun bindViews() {
        handleUpButtonClick()
        handleInitialEditTextClick()
        handleTextChanges()
    }

    private fun observeViews() {
        observeSearchViewState()
    }

    private fun handleUpButtonClick() {
        binding.backToStartStateImageButton.setOnClickListener {
            binding.dashboardLayout.transitionToStart()
        }
    }

    private fun handleInitialEditTextClick() {
        binding.searchEditText.setOnFocusChangeListener { v, hasFocus ->
            binding.dashboardLayout.transitionToEnd()
        }
    }

    private fun handleTextChanges() {
        binding.searchEditText.doOnTextChanged { text, start, before, count ->
            text?.let { name ->
                if(name.length >= 2) {
                    binding.dashboardLayout.transitionToEnd()
                    characterSearchViewModel.executeCharacterSearch(name.toString())
                }
            }
        }
    }

    private fun observeSearchViewState() {
        characterSearchViewModel.searchViewState.observe(this) { state ->

            handleSearchLoading(state)
        }
    }

    private fun handleSearchLoading(state: DashboardSearchViewState) {
        if(state.isLoading) {
            binding.searchResultsRecyclerView.hide()
            binding.searchResultsProgressBar.show()
        } else {
            binding.searchResultsRecyclerView.show()
            binding.searchResultsProgressBar.hide()
        }
    }
}