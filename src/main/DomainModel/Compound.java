package DomainModel;

import DomainModel.Mapper.ChemicalNotFoundException;
import DomainModel.Mapper.CompoundMapper;
import DomainModel.Mapper.ElementNotFoundException;
import datasource.DataException;

import java.util.ArrayList;
import java.util.List;

public class Compound {
    private ArrayList<String> elements = new ArrayList<String>();
    private String name;

    public Compound(String name) {
        this.name = name;
    }

    public void addElement(String name) {
        elements.add(name);
    }

    public void setName(String newName) {
        name = newName;
    }

    public String getName() {
        return name;
    }

    public List<String> getAllElements() {
        return elements;
    }

    public double getAtomicMass() throws ChemicalNotFoundException, DataException, ElementNotFoundException {
        return CompoundMapper.getAtomicMass(elements);
    }

    public void persist() {
    }
}
