package model.mapper;

import datasource.DataException;
import datasource.ElementGateway;
import gatewayDTOs.ElementDTO;
import model.Compound;
import model.Element;

import java.util.ArrayList;

public class CompoundMapper {
    private Compound myCompound;


    public CompoundMapper(String name) throws CompoundNotFoundException {

    }

    public static void createCompound(String name) {

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
