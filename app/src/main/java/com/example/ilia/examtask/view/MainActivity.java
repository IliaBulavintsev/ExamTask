package com.example.ilia.examtask.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ilia.examtask.OnCurrenciesLoaded;
import com.example.ilia.examtask.R;
import com.example.ilia.examtask.controller.Calculator;
import com.example.ilia.examtask.controller.LoadCurrencies;
import com.example.ilia.examtask.controller.LoadCurrenciesFromCache;
import com.example.ilia.examtask.controller.NetworkReceiver;
import com.example.ilia.examtask.model.CurrenciesList;
import com.example.ilia.examtask.model.Currency;

import java.text.DecimalFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnCurrenciesLoaded, NetworkReceiver.OnNetworkChanges {

    private static final String TAG = "MainActivity";
    private Spinner spinnerFrom;
    private Spinner spinnerTo;
    private EditText editTextFrom;
    private TextView textViewTo;
    private Button buttonConvert;
    private ScrollView scrollLayoutConvert;
    private ProgressBar textViewStub;
    private TextView textViewNocacheNointernet;
    private LoadCurrencies loader;
    private LoadCurrenciesFromCache loaderCache;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerFrom = (Spinner) findViewById(R.id.spinner_from);
        spinnerTo = (Spinner) findViewById(R.id.spinner_to);
        editTextFrom = (EditText) findViewById(R.id.edit_from);
        textViewTo = (TextView) findViewById(R.id.text_to);
        buttonConvert = (Button) findViewById(R.id.button_convert);
        scrollLayoutConvert = (ScrollView) findViewById(R.id.scroll_layout_converter);
        textViewStub = (ProgressBar) findViewById(R.id.text_stub);
        textViewNocacheNointernet = (TextView) findViewById(R.id.text_nocache_nointernet);

        NetworkReceiver.setListener(this);
        editTextFrom.addTextChangedListener(new EditTextWatcher());
        buttonConvert.setOnClickListener(new ButtonClickListener());

        showScrollLayoutConverter(false);
        showNocacheNointernet(false);
        showProgressBar(true);

        if (loader != null && !loader.isCancelled()) {
            return;
        }
        loader = new LoadCurrencies(MainActivity.this, getApplicationContext());
        loader.execute();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (loader != null) {
            loader.cancel(false);
        }
        if (loaderCache != null) {
            loaderCache.cancel(false);
        }
    }


    @Override
    public void OnCurrenciesLoadedSuccess(CurrenciesList loadedList) {
        settingAdapter(spinnerFrom, loadedList.getCurrencies());
        settingAdapter(spinnerTo, loadedList.getCurrencies());
        showScrollLayoutConverter(true);
        showNocacheNointernet(false);
        showProgressBar(false);
    }

    @Override
    public void OnCurrenciesLoadedError() {
        if (loaderCache != null && !loaderCache.isCancelled()) {
            return;
        }
        loaderCache = new LoadCurrenciesFromCache(MainActivity.this, getApplicationContext());
        loaderCache.execute();
        showScrollLayoutConverter(false);
        showNocacheNointernet(false);
        showProgressBar(true);
    }

    @Override
    public void OnCurrenciesLoadedErrorWithCache() {
        showScrollLayoutConverter(false);
        showNocacheNointernet(true);
        showProgressBar(false);
    }

    private void settingAdapter(Spinner spinner, List<Currency> currenciesList) {
        ArrayAdapter<Currency> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                currenciesList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public void OnNetworkChanges() {
        if (scrollLayoutConvert.getVisibility() == View.GONE && loader != null && !loader.isCancelled()){
            showScrollLayoutConverter(false);
            showNocacheNointernet(false);
            showProgressBar(true);
            loader = new LoadCurrencies(MainActivity.this, getApplicationContext());
            loader.execute();
        }
    }

    private void showProgressBar(boolean show) {
        textViewStub.setVisibility(show? View.VISIBLE : View.GONE);
    }

    private void showScrollLayoutConverter(boolean show) {
        scrollLayoutConvert.setVisibility(show? View.VISIBLE : View.GONE);
    }

    private void showNocacheNointernet(boolean show) {
        textViewNocacheNointernet.setVisibility(show? View.VISIBLE : View.GONE);
    }

    private class EditTextWatcher implements TextWatcher{
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if(s.toString().trim().length()==0){
                buttonConvert.setEnabled(false);
            } else {
                buttonConvert.setEnabled(true);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

    }

    private class ButtonClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            String amount = editTextFrom.getText().toString().trim();

            Currency from;
            Currency to;
            from = (Currency) spinnerFrom.getSelectedItem();
            to = (Currency) spinnerTo.getSelectedItem();
            Calculator calculator = new Calculator(amount, from.getNominal(), from.getValue(), to.getNominal(), to.getValue());
            double result = calculator.performCalculation();
            String resultString = new DecimalFormat("#0.00").format(result) + " " + to.getCharCode();
            textViewTo.setText(resultString);

        }
    }
}
