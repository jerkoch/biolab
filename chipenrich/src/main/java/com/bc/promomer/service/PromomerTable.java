package com.bc.promomer.service;

import java.io.File;

import com.bc.core.BackgroundChip;

public interface PromomerTable {
	public void getCisCount(BackgroundChip backgroundChip, File AGIMotifTable, File motifFile,
			File[] inputFiles);
}
