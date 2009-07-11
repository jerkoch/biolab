package com.bc.core;

/**
 * Abstract class which encapsulates a the common characteristics of a gene descriptor (GO, Affy,
 * TranscriptionFactoryFamiliy).
 * 
 * @author Jeremy Koch
 */
public abstract class GeneDescriptor {
   protected String id;
   protected String desc;

   /**
    * Constructs a new gene descriptor. 
    * @param id
    * @param desc
    */
   public GeneDescriptor(String id, String desc) {
      this.id = id;
      this.desc = desc;
   }

   /**
    * Returns the id.
    * @return
    */
   public String getId() {
      return id;
   }

   /**
    * Returns the description.
    * @return
    */
   public String getDescription() {
      return desc == null ? "" : desc;
   }

   /**
    * Sets the description.
    * @param desc the description.
    */
   public void setDescription(String desc) {
      this.desc = desc;
   }

   /*
    * (non-Javadoc)
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      }
      if (obj instanceof GeneDescriptor) {
         return id.equals(((GeneDescriptor) obj).getId());
      }
      return false;
   }

   /*
    * (non-Javadoc)
    * @see java.lang.Object#hashCode()
    */
   public int hashCode() {
      return id.hashCode();
   }

   /*
    * (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   public String toString() {
      return id;
   }
}
