package com.example.ilia.examtask.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.IOException;
import java.io.Serializable;


@Root(name = "Valute")
public class Currency implements Serializable{
    @Attribute(name = "ID")
    private String id;

    @Element(name = "Name")
    private String name;

    @Element(name = "NumCode")
    private int numCode;

    @Element(name = "CharCode")
    private String charCode;

    @Element(name = "Nominal")
    private Double nominal;

    @Element(name = "Value")
    private Double value;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNumCode() {
        return numCode;
    }

    public String getCharCode() {
        return charCode;
    }

    public Double getNominal() {
        return nominal;
    }

    public Double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getCharCode()+ " - " + getName();
    }

    private void writeObject(java.io.ObjectOutputStream stream)
            throws IOException {
        stream.writeObject(id);
        stream.writeObject(name);
        stream.writeInt(numCode);
        stream.writeObject(charCode);
        stream.writeDouble(nominal);
        stream.writeDouble(value);

    }

    private void readObject(java.io.ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        id = (String) stream.readObject();
        name = (String) stream.readObject();
        numCode = stream.readInt();
        charCode = (String) stream.readObject();
        nominal = stream.readDouble();
        value = stream.readDouble();
    }
}
