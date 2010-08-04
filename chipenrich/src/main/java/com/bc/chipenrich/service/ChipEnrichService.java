package com.bc.chipenrich.service;

import java.io.File;
import java.io.InputStream;

import com.bc.chipenrich.domain.EnrichmentSummary;
import com.bc.core.BackgroundChip;
import com.bc.core.GO;
import com.bc.core.GeneDescriptorMap;
import com.bc.core.TranscriptionFactorFamily;

public interface ChipEnrichService {

   public BackgroundChip processBackgroundChip(InputStream backgroundChipFile);

   public GeneDescriptorMap<GO> processGoAnnotations(InputStream[] goAnnotationFiles,
         BackgroundChip backgroundChip);

   public GeneDescriptorMap<TranscriptionFactorFamily> processTranscriptionFactorFamily(
         InputStream tffFile, BackgroundChip backgroundChip);

   public EnrichmentSummary processEnrichment(BackgroundChip backgroundChip,
         GeneDescriptorMap<?> geneDescriptorMap, File[] inputFiles, String outputDirectory,
         boolean ignoreMultipleQLP, String type);
}
