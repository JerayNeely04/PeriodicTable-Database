package model.controller;

import datasource.DataException;
import model.Element;
import model.mapper.ElementMapper;
import model.mapper.ElementNotFoundException;

import java.util.List;

public class ElementController
{
    public static void delete(String name) throws ElementNotFoundException {
        ElementMapper.delete(name);
    }

    public static Element[] getElementsBetween(int firstAtomicNumber, int lastAtomicNumber) throws ElementNotFoundException {
        return ElementMapper.getElementsBetween(firstAtomicNumber, lastAtomicNumber);
    }

    public static Element[] getAllElements() {
        return ElementMapper.getAllElements();
    }

    public Element getMyElement()
    {
        return myElement;
    }

    private Element myElement;
    public ElementController(String name) throws ElementNotFoundException {
        myElement = new ElementMapper(name).getMyElement();
    }

    public ElementController(String name, int atomicNumber, double atomicMass) throws ElementNotFoundException {
        myElement = new ElementMapper(name, atomicNumber, atomicMass).getMyElement();
    }

    public void setAtomicNumber(int newAtomicNumber)
    {
        myElement.setAtomicNumber(newAtomicNumber);
    }

    public void setAtomicMass(double newAtomicMass)
    {
        myElement.setAtomicMass(newAtomicMass);
    }

    public void setName(String newName)
    {
        myElement.setName(newName);
    }

    public void persist() throws DataException, ElementNotFoundException {
        myElement.persist();
    }

    public List<String> getCompoundsContaining() {
        return null;
    }
}
