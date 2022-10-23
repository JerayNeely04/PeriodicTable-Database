package DomainModel.Controller;

import DomainModel.Element;
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
    public ElementController(String name)
    {
    }

    public ElementController(String name, int atomicNumber, double atomicMass)
    {
    }

    public void setAtomicNumber(int newAtomicNumber)
    {
    }

    public void setAtomicMass(double newAtomicMass)
    {
    }

    public void setName(String newName)
    {
    }

    public void persist()
    {
    }
}
