package com.bc.promomer.service;

import java.io.File;

import com.bc.core.BackgroundChip;

public interface PromomerService {
   public void getCisCount(BackgroundChip backgroundChip, File upstreamFile, String cis,
         File[] inputFiles);
}
