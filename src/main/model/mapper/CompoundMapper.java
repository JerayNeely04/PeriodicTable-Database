package model.mapper;

import datasource.CompoundGateway;
import datasource.DataException;
import datasource.ElementGateway;
import datasource.MadeOfGateway;
import gatewayDTOs.CompoundDTO;
import gatewayDTOs.ElementDTO;
import gatewayDTOs.MadeOfDTO;
import model.Compound;
import model.Element;

import java.util.ArrayList;
import java.util.List;

public class CompoundMapper {
    private Compound myCompound;

    /**
     * Finds a compound in the database and maps it to a new compound object
     * @param name the name of the compound to find
     * @throws CompoundNotFoundException
     * @throws DataException
     * @throws ElementNotFoundException
     */
    public CompoundMapper(String name) throws CompoundNotFoundException, DataException, ElementNotFoundException {
        CompoundDTO compoundDTO = CompoundGateway.findByName(name);
        long id = compoundDTO.getId();

        myCompound = new Compound(id, name);
        ArrayList<MadeOfDTO> elements = MadeOfGateway.findByCompoundID(id);
        for (MadeOfDTO e:elements) {
            long elementID = e.getElementID();
            ElementGateway gateway = new ElementGateway(elementID);
            myCompound.addElement(gateway.getName());
        }
    }

    /**
     * Creates a new compound in the database
     * @param name of the compound to create
     * @throws CompoundNotFoundException
     */
    public static void createCompound(String name) throws CompoundNotFoundException {
        new CompoundGateway(name);
    }

    /**
     * Gets the total atomic mass of a compound using all of its element components
     * @param elements the list of elements that make up the compound
     * @return the total atomic mass of the compound
     * @throws DataException
     * @throws ElementNotFoundException
     */
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

    public static void delete(String name) throws CompoundNotFoundException, DataException {
        CompoundDTO compound =  CompoundGateway.findByName(name);
        CompoundGateway gateway = new CompoundGateway(compound.getId());
        gateway.delete();
    }

    public static void addElement(long compoundId, String elementName) throws ElementNotFoundException, DataException {
        ElementDTO element = ElementGateway.findByName(elementName);
        long elementToAddID = element.getId();
        new MadeOfGateway(compoundId, elementToAddID);
    }

    public static List<String> getAllElements(long id) throws DataException, ElementNotFoundException {
        ArrayList<MadeOfDTO> elementsDTOs = MadeOfGateway.findByCompoundID(id);
        List<String> elements = new ArrayList<>();
        for (MadeOfDTO element: elementsDTOs) {
            ElementGateway gateway = new ElementGateway(element.getElementID());
            elements.add(gateway.getName());
        }
        return elements;
    }


    public Compound getMyCompound() {
        return myCompound;
    }
}
