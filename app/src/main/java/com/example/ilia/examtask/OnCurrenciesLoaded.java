package com.example.ilia.examtask;

import com.example.ilia.examtask.model.CurrenciesList;

public interface OnCurrenciesLoaded {
    void OnCurrenciesLoadedSuccess(CurrenciesList loadedList);
    void OnCurrenciesLoadedError();
    void OnCurrenciesLoadedErrorWithCache();
}
