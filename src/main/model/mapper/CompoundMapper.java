package model.mapper;

import GatewayDTO.madeOfDTO;
import datasource.DataException;
import datasource.MadeOfTableGateway;
import datasource.SingleTableGateway;
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
        try {
            SingleTableGateway gateway = new SingleTableGateway(name);
            long id = gateway.getId();

            myCompound = new Compound(id, name);
            ArrayList<madeOfDTO> elements = MadeOfTableGateway.findByCompoundID(id);
            for (madeOfDTO e:elements) {
                long elementID = e.getElementID();
                SingleTableGateway elementGateway = new SingleTableGateway(elementID);
                myCompound.addElement(elementGateway.getName());
            }
        } catch (DataException e) {
            throw new CompoundNotFoundException("Could not map compound", e);
        }
    }

    /**
     * Creates a new compound in the database
     * @param name of the compound to create
     * @throws CompoundNotFoundException
     */
    public static void createCompound(String name) throws DataException {
        SingleTableGateway.createCompound(name);
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
            SingleTableGateway elementGateway = new SingleTableGateway(element);
            totalMass += elementGateway.getAtomicMass();
        }

        return totalMass;
    }

    public static void delete(String name) throws DataException {
        SingleTableGateway gateway = new SingleTableGateway(name);
        gateway.delete();
    }

    public static void addElement(long compoundId, String elementName) throws ElementNotFoundException {
        try {
            SingleTableGateway elementGateway = new SingleTableGateway(elementName);
            long elementToAddID = elementGateway.getId();
            new MadeOfTableGateway(compoundId, elementToAddID);
        } catch (DataException e) {
            throw new ElementNotFoundException("Could not map compound", e);
        }
    }

    public static List<String> getAllElements(long id) throws DataException, ElementNotFoundException {
        ArrayList<madeOfDTO> elementsDTOs = MadeOfTableGateway.findByCompoundID(id);
        List<String> elements = new ArrayList<>();

        for (madeOfDTO element: elementsDTOs) {
            SingleTableGateway elementGateway = new SingleTableGateway(element.getElementID());
            elements.add(elementGateway.getName());
        }
        return elements;
    }


    public Compound getMyCompound() {
        return myCompound;
    }
}
