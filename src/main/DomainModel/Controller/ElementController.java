package DomainModel.Controller;

import DomainModel.Element;
import DomainModel.Mapper.ChemicalNotFoundException;
import DomainModel.Mapper.ElementMapper;
import DomainModel.Mapper.ElementNotFoundException;
import datasource.DataException;
import gatewayDTOs.ElementDTO;

import java.util.ArrayList;
import java.util.List;

public class ElementController
{
    public static void delete(String name) throws ChemicalNotFoundException, DataException {
        ElementMapper.delete(name);
    }

    public static Element[] getElementsBetween(int firstAtomicNumber, int lastAtomicNumber) throws DataException, ElementNotFoundException {
        return ElementMapper.getElementsBetween(firstAtomicNumber, lastAtomicNumber);
    }

    public static Element[] getAllElements() throws DataException, ElementNotFoundException {
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

    public ElementController(String name, int atomicNumber, double atomicMass) throws DataException, ElementNotFoundException {
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
