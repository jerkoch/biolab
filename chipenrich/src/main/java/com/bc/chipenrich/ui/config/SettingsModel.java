package com.bc.chipenrich.ui.config;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JSplitPane;

public class SettingsModel implements SettingsListener {
   private static final String CFG_DIVIDERPOS_X = "view.divider.x";
   private static final String CFG_DIVIDERPOS_Y = "view.divider.y";
   private static final String CFG_ORIENTATION = "view.orientation";
   private static final String CFG_DETAILSWORDWRAP = "view.detailsWordWrap";

   private boolean detailsWrap = true;
   private int orientation = JSplitPane.VERTICAL_SPLIT;
   private int dividerX = 200;
   private int dividerY = 200;

   private transient final PropertyChangeSupport propertySupport = new PropertyChangeSupport(
         this);

   /**
    * @param listener
    */
   public void addPropertyChangeListener(PropertyChangeListener listener) {
      propertySupport.addPropertyChangeListener(listener);
   }

   /**
    * @param propertyName
    * @param listener
    */
   public void addPropertyChangeListener(String propertyName,
         PropertyChangeListener listener) {
      propertySupport.addPropertyChangeListener(propertyName, listener);
   }

   /**
    * @param propertyName
    * @param oldValue
    * @param newValue
    */
   private void firePropertyChange(String propertyName, boolean oldValue,
         boolean newValue) {
      propertySupport.firePropertyChange(propertyName, oldValue, newValue);
   }

   /**
    * @param propertyName
    * @param oldValue
    * @param newValue
    */
   private void firePropertyChange(String propertyName, int oldValue, int newValue) {
      propertySupport.firePropertyChange(propertyName, oldValue, newValue);
   }

   /**
    * @param propertyName
    * @return listeners flag
    */
   public boolean hasListeners(String propertyName) {
      return propertySupport.hasListeners(propertyName);
   }

   /**
    * @param listener
    */
   public void removePropertyChangeListener(PropertyChangeListener listener) {
      propertySupport.removePropertyChangeListener(listener);
   }

   public boolean isDetailsWrap() {
      return detailsWrap;
   }

   public void setDetailsWrap(boolean detailsWrap) {
      boolean old = this.detailsWrap;
      this.detailsWrap = detailsWrap;
      firePropertyChange("detailsWordWrap", old, detailsWrap);
   }

   public int getOrientation() {
      return orientation;
   }

   public void setOrientation(int orientation) {
      int old = this.orientation;
      this.orientation = orientation;
      firePropertyChange("orientation", old, orientation);

      if (orientation == JSplitPane.HORIZONTAL_SPLIT)
         firePropertyChange("dividerPos", old, dividerX);
      else
         firePropertyChange("dividerPos", old, dividerY);
   }
   
   public void toggleOrientation() {
      if (getOrientation() == JSplitPane.HORIZONTAL_SPLIT)
         setOrientation(JSplitPane.VERTICAL_SPLIT);
      else
         setOrientation(JSplitPane.HORIZONTAL_SPLIT);
   }

   public int getDividerX() {
      return dividerX;
   }

   public void setDividerX(int dividerX) {
      int old = this.dividerX;
      this.dividerX = dividerX;
      if (orientation == JSplitPane.HORIZONTAL_SPLIT)
         firePropertyChange("dividerPos", old, dividerX);
   }

   public int getDividerY() {
      return dividerY;
   }

   public void setDividerY(int dividerY) {
      int old = this.dividerY;
      this.dividerY = dividerY;
      if (orientation == JSplitPane.VERTICAL_SPLIT)
         firePropertyChange("dividerPos", old, dividerY);
   }
   
   public void loadSettings(LoadSettingsEvent event) {
      setOrientation(event.getInt(CFG_ORIENTATION));
      setDetailsWrap(event.getBoolean(CFG_DETAILSWORDWRAP));
      setDividerX(event.getInt(CFG_DIVIDERPOS_X));
      setDividerY(event.getInt(CFG_DIVIDERPOS_Y));
   }

   public void saveSettings(SaveSettingsEvent event) {
      event.saveSetting(CFG_ORIENTATION, getOrientation());
      event.saveSetting(CFG_DETAILSWORDWRAP, isDetailsWrap());
      event.saveSetting(CFG_DIVIDERPOS_X, getDividerX());
      event.saveSetting(CFG_DIVIDERPOS_Y, getDividerY());
   }
}
