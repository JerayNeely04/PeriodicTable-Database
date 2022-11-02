package model.mapper;

import datasource.CompoundGateway;
import datasource.DataException;
import datasource.ElementGateway;
import gatewayDTOs.CompoundDTO;
import gatewayDTOs.ElementDTO;
import model.Compound;
import model.Element;

import java.util.ArrayList;

public class CompoundMapper {
    private Compound myCompound;


    public CompoundMapper(String name) throws CompoundNotFoundException {
        try {
            CompoundDTO compoundDTO = CompoundGateway.findByName(name);
            long id = compoundDTO.getId();

            myCompound = new Compound(id, name);
        } catch (CompoundNotFoundException e) {
            throw new CompoundNotFoundException("Compound: " + name + " could not mapped", e);
        }
    }

    public static void createCompound(String name) throws CompoundNotFoundException {
        try {
            CompoundGateway gateway = new CompoundGateway(name);
        } catch (CompoundNotFoundException e) {
            throw new CompoundNotFoundException("Cannot create compound");
        }
    }

    public static double getAtomicMass(ArrayList<String> elements) throws DataException, ElementNotFoundException {
        double totalMass = 0.0;

        for (String element:elements) {
            ElementDTO elementDTO = ElementGateway.findByName(element);
            long id = elementDTO.getId();

            ElementGateway gateway = new ElementGateway(id);
            totalMass += gateway.getAtomicMass();
        }

        return totalMass;
    }

    public static void delete(String name) {
    }

    public Compound getMyCompound() {
        return myCompound;
    }
}
