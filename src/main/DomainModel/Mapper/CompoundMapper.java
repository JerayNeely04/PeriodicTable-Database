package DomainModel.Mapper;

import DomainModel.Compound;
import datasource.ChemicalTableGateway;
import datasource.DataException;
import datasource.ElementTableGateway;
import datasource.madeOfTable;
import gatewayDTOs.ChemicalDTO;
import gatewayDTOs.ElementDTO;
import gatewayDTOs.madeOfDTO;

import javax.xml.crypto.Data;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class CompoundMapper {
    private Compound myCompound;

    public CompoundMapper(String name) throws CompoundNotFoundException {
        try {
            ChemicalDTO chemicalDTO = ChemicalTableGateway.findByName(name);
            long chemicalId = chemicalDTO.getId();
            myCompound = new Compound(chemicalId, name);

            ArrayList<madeOfDTO> madeOfDTOs = madeOfTable.findByCompoundID(chemicalId);
            for (madeOfDTO madeOfDTO:madeOfDTOs) {
                long elementID = madeOfDTO.getElementID();
                ChemicalTableGateway chemicalGateway = new ChemicalTableGateway(elementID);
                myCompound.addElement(chemicalGateway.getName());
            }
        } catch (ChemicalNotFoundException | DataException e) {
            throw new CompoundNotFoundException("Compound could not be mapped", e);
        }
    }

    public static void createCompound(String name) throws CompoundNotFoundException {
        try {
            ChemicalTableGateway.createChemical(name);
        } catch (ChemicalNotFoundException | DataException e) {
            throw new CompoundNotFoundException("Compound could not be created", e);
        }
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

    public static List<String> getAllElements(long compoundId) throws ElementNotFoundException {
        try {
            ArrayList<madeOfDTO> elementsDTOs = madeOfTable.findByCompoundID(compoundId);
            List<String> elements = new ArrayList<>();
            for (madeOfDTO element: elementsDTOs) {
                ChemicalTableGateway chemicalGateway = new ChemicalTableGateway(element.getElementID());
                elements.add(chemicalGateway.getName());
            }
            return elements;
        } catch (ChemicalNotFoundException | DataException e) {
            throw new ElementNotFoundException("Could not get all elements for compound", e);
        }
    }

    public static void addElement(long compoundId, String elementName) throws ElementNotFoundException {
        try {
            ChemicalDTO element = ChemicalTableGateway.findByName(elementName);
            long elementToAddID = element.getId();
            new madeOfTable(compoundId, elementToAddID);
        } catch (DataException | ChemicalNotFoundException e) {
            throw new ElementNotFoundException("Element could not be added", e);
        }
    }


    public static void delete(String name) throws CompoundNotFoundException {
        try {
            ChemicalDTO chemicalDTO = ChemicalTableGateway.findByName(name);
            long chemicalId = chemicalDTO.getId();

            ChemicalTableGateway chemicalGateway = new ChemicalTableGateway(chemicalId);
            chemicalGateway.delete();
        } catch (ChemicalNotFoundException | DataException e) {
            throw new CompoundNotFoundException("Compound could not be deleted", e);
        }
    }

    public Compound getMyCompound() {
        return myCompound;
    }
}
