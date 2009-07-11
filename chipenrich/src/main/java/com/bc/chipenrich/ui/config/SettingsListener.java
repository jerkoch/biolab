package com.bc.chipenrich.ui.config;

import java.util.EventListener;

public interface SettingsListener extends EventListener {
   public void loadSettings(LoadSettingsEvent event);
   public void saveSettings(SaveSettingsEvent event);
}
