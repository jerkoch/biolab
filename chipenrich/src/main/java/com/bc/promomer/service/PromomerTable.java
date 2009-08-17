package com.bc.promomer.service;

import java.io.File;

import com.bc.core.BackgroundChip;
import com.bc.file.AGIMotifReader;

public interface PromomerTable {
	public void getCisCount(BackgroundChip backgroundChip, File motifFile,
			File[] inputFiles, AGIMotifReader tableReader, String outputDir);
}
