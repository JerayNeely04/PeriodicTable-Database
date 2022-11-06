package model.controller;

import datasource.DataException;
import model.Compound;
import model.mapper.CompoundMapper;
import model.mapper.CompoundNotFoundException;
import model.mapper.ElementNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class CompoundController
{
    public static void delete(String name) throws CompoundNotFoundException, DataException {
        CompoundMapper.delete(name);
    }

    public static void createCompound(String name) throws DataException {
        CompoundMapper.createCompound(name);
    }

    public Compound getMyCompound()
    {
        return myCompound;
    }

    private Compound myCompound;
    public CompoundController(String name) throws CompoundNotFoundException, DataException, ElementNotFoundException {
        myCompound = new CompoundMapper(name).getMyCompound();
    }

    public CompoundController(String name, int atomicNumber, double atomicMass) throws CompoundNotFoundException, DataException, ElementNotFoundException {
        myCompound = new CompoundMapper(name).getMyCompound();
    }

    public void setName(String newName)
    {
        myCompound.setName(newName);
    }


    public void addElement(String name) throws ElementNotFoundException, DataException {
        myCompound.addElement(name);
        myCompound.addElementToMadeOf(name);
    }

    public List<String> getElements() throws DataException, ElementNotFoundException {
        return myCompound.getAllElements();
    }

    public double getAtomicMass() throws DataException, ElementNotFoundException {
        return myCompound.getAtomicMass();
    }
}
