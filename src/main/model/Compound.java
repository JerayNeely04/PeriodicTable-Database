package model;

import model.mapper.CompoundMapper;
import model.mapper.ElementNotFoundException;
import datasource.DataException;

import java.util.ArrayList;
import java.util.List;

public class Compound {
    private ArrayList<String> elements = new ArrayList<String>();

    private long id;
    private String name;

    public Compound(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addElement(String name)  {
        elements.add(name);
    }

    public void addElementToMadeOf(String name) throws ElementNotFoundException, DataException {
        CompoundMapper.addElement(id, name);
    }

    public void setName(String newName) {
        name = newName;
    }
    public long getId() { return id; }

    public String getName() {
        return name;
    }

    public List<String> getAllElements() {
        return elements;
    }

    public double getAtomicMass() throws DataException, ElementNotFoundException {
        return CompoundMapper.getAtomicMass(elements);
    }

}

