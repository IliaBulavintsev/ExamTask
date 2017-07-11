package com.example.ilia.examtask.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ilia.examtask.OnCurrenciesLoaded;
import com.example.ilia.examtask.R;
import com.example.ilia.examtask.controller.LoadCurrencies;
import com.example.ilia.examtask.model.CurrenciesList;
import com.example.ilia.examtask.model.Currency;
import com.example.ilia.examtask.storage.CurrenciesStorage;
import com.example.ilia.examtask.storage.Storage;

import java.util.List;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity implements OnCurrenciesLoaded{

    private static final String TAG = "MainActivity";
    private Spinner spinnerFrom;
    private Spinner spinnerTo;
    private EditText editTextFrom;
    private TextView textViewTo;
    private Button buttonConvert;
    private LinearLayout linearLayoutConvert;
    private TextView textViewStub;
    private CurrenciesStorage currenciesStorage;
    private LoadCurrencies loader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerFrom = (Spinner) findViewById(R.id.spinner_from);
        spinnerTo = (Spinner) findViewById(R.id.spinner_to);
        editTextFrom = (EditText) findViewById(R.id.edit_from);
        textViewTo = (TextView) findViewById(R.id.text_to);
        buttonConvert = (Button) findViewById(R.id.button_convert);
        linearLayoutConvert = (LinearLayout) findViewById(R.id.liner_layout_converter);
        textViewStub = (TextView) findViewById(R.id.text_stub);

        currenciesStorage = ((Storage)getApplication()).getStorage();

        loader = new LoadCurrencies(MainActivity.this);
        loader.execute();


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void OnCurrenciesLoadedSuccess(CurrenciesList loadedList) {
        Log.e(TAG, "OnCurrenciesLoadedSuccess: " + loadedList);
        currenciesStorage.setLoadedList(loadedList);
        settingAdapter(spinnerFrom, currenciesStorage.getLoadedList().getCurrencies());
        settingAdapter(spinnerTo, currenciesStorage.getLoadedList().getCurrencies());
        linearLayoutConvert.setVisibility(View.VISIBLE);
        textViewStub.setVisibility(View.GONE);
    }

    @Override
    public void OnCurrenciesLoadedError() {
        Log.e(TAG, "OnCurrenciesLoadedError: " );
        if (!currenciesStorage.isReady()){
            Log.e(TAG, "onCreate: without Cache!");

        } else {
            Log.e(TAG, "onCreate: with Cache!");

        }

    }

    private void settingAdapter(Spinner spinner, List<Currency> currenciesList) {
        ArrayAdapter<Currency> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                currenciesList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
