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

    public static void createCompound(String name) throws CompoundNotFoundException {
        CompoundMapper.createCompound(name);
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

    public double getAtomicMass() throws DataException, ElementNotFoundException {
        return myCompound.getAtomicMass();
    }
}
