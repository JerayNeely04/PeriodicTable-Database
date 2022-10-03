package gatewayDTOs;

public class MetalDTO {
   private  long id;
   private   String name;
   private   long atomicNumber;
   private   double atomicMass;

   public MetalDTO(long id, String name, long atomicNumber, double atomicMass){

      this.id = id;
      this.name = name;
      this.atomicNumber = atomicNumber;
      this.atomicMass = atomicMass;
   }

   public void setId(long id) {
      this.id = id;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setAtomicNumber(long atomicNumber) {
      this.atomicNumber = atomicNumber;
   }

   public void setAtomicMass(double atomicMass) {
      this.atomicMass = atomicMass;
   }

   /**
    *
    * @return the ID
    */
   public long getId() {
      return id;
   }

   /**
    *
    * @return the name of the metal
    */
   public String getName() {
      return name;
   }

   /**
    *
    * @return the atomic number
    */
   public long getAtomicNumber() {
      return atomicNumber;
   }

   /**
    *
    * @return the atomicmass
    */
   public double getAtomicMass() {
      return atomicMass;
   }
}
