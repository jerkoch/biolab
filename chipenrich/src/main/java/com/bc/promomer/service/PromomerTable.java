package com.bc.promomer.service;

import java.io.File;
import java.io.InputStream;

import com.bc.core.BackgroundChip;
import com.bc.file.AGIMotifReader;

public interface PromomerTable {
	public void getCisCount(BackgroundChip backgroundChip, String motifFileName,
			File inputFile, AGIMotifReader tableReader, String outputDir);
}
