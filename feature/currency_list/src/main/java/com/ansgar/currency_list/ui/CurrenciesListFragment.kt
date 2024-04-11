package com.ansgar.currency_list.ui

import android.view.View
import com.ansgar.base.fragment.BaseFragment
import com.ansgar.design.createContentView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrenciesListFragment :
    BaseFragment<CurrenciesListEvents, CurrenciesListUiState, CurrenciesListViewModel>(
        CurrenciesListViewModel::class.java
    ) {

    override fun createView(): View = createContentView {
        CurrenciesView(viewModel = viewModel)
    }

    override fun observeEvent(event: CurrenciesListEvents) {
    }
}