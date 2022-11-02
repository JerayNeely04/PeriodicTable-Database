package DomainModel.Controller;

import DomainModel.Compound;
import DomainModel.Element;
import DomainModel.Mapper.*;
import datasource.DataException;

import java.util.ArrayList;
import java.util.List;

public class CompoundController
{
    public static void delete(String name) throws CompoundNotFoundException, DataException {
        CompoundMapper.delete(name);
    }

    public static void createCompound(String name) throws CompoundNotFoundException {
        new CompoundMapper(name);
    }

    public Compound getMyCompound()
    {
        return myCompound;
    }

    private Compound myCompound;
    public CompoundController(String name) throws CompoundNotFoundException {
        myCompound = new CompoundMapper(name).getMyCompound();
    }

    public CompoundController(String name, int atomicNumber, double atomicMass) throws CompoundNotFoundException {
        myCompound = new CompoundMapper(name).getMyCompound();
    }

    public void setName(String newName)
    {
        myCompound.setName(newName);
    }

    public void persist() throws DataException, ElementNotFoundException {
        myCompound.persist();
    }

    public void addElement(String name) {
        myCompound.addElement(name);
    }

    public List<String> getElements() {
        return myCompound.getAllElements();
    }

    public double getAtomicMass() throws ChemicalNotFoundException, DataException, ElementNotFoundException {
        return myCompound.getAtomicMass();
    }
}
