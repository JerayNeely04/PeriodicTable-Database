package DomainModel.Mapper;

import DomainModel.Compound;
import datasource.ChemicalTableGateway;
import datasource.DataException;
import datasource.ElementTableGateway;

import java.util.ArrayList;

public class CompoundMapper {
    private Compound myCompound;

    public CompoundMapper(String name) throws CompoundNotFoundException {

    }

    public static void createCompound(String name) {

    }

    public static double getAtomicMass(ArrayList<String> elements) throws ChemicalNotFoundException, DataException, ElementNotFoundException {
        double totalMass = 0.0;

        for (String element:elements) {
            long id = ChemicalTableGateway.findByName(element).getId();
            ElementTableGateway gateway = new ElementTableGateway(id);
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
