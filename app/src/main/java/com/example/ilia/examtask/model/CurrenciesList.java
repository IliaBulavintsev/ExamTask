package com.example.ilia.examtask.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.RegistryMatcher;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.List;


@Root(name = "ValCurs")
public class CurrenciesList implements Serializable {
    private static final String TAG = "CurrenciesList";
    public static String FILE_NAME = "rates.xml";

    @Attribute(name = "Date")
    private String date;

    @Attribute(name = "name")
    private String name;

    @ElementList(entry = "Valute", inline = true)
    private List<Currency> currencies;

    public List<Currency> getCurrencies() {
        return currencies;
    }

    private void writeObject(java.io.ObjectOutputStream stream)
            throws IOException {
        stream.writeObject(date);
        stream.writeObject(name);
        stream.writeObject(currencies);

    }

    private void readObject(java.io.ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        date= (String) stream.readObject();
        name = (String) stream.readObject();
        currencies = (List<Currency>)stream.readObject();
    }


    public static CurrenciesList readFromStream(@NonNull InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(stream, Charset.forName("windows-1251")));

        RegistryMatcher m = new RegistryMatcher();
        m.bind(Double.class, new DoubleTransformer());
        Serializer serializer = new Persister(m);

        try {
            return serializer.read(CurrenciesList.class, reader);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    public static void writeToFile(Context context, CurrenciesList listLoaded) throws IOException {

        FileOutputStream output = null;
        try {
            output = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutput = new ObjectOutputStream(output);
            objectOutput.writeObject(listLoaded);


        } finally {
            if (output != null)
                output.close();
        }

    }

    public static  CurrenciesList readFromFile(Context context) throws IOException, ClassNotFoundException {

        FileInputStream fis = context.openFileInput(FILE_NAME);
        ObjectInputStream objectInputStream = new ObjectInputStream(fis);
        return (CurrenciesList) objectInputStream.readObject();
    }

}