package DomainModel.Controller;

import DomainModel.Element;
import DomainModel.Mapper.ElementMapper;
import DomainModel.Mapper.ElementNotFoundException;
import datasource.DataException;

public class ElementController
{
    public static void delete(String oxygen)
    {
    }

    public static Element[] getElementsBetween(int firstAtomicNumber,
                                               int lastAtomicNumber)
    {
        return null;
    }

    public static Element[] getAllElements()
    {
        return null;
    }

    public Element getMyElement()
    {
        return myElement;
    }

    private Element myElement;
    public ElementController(String name) throws DataException, ElementNotFoundException {
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

    public void persist() throws DataException {
        myElement.persist();
    }
}
