package com.bc.file;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.bc.core.AGI;
import com.bc.core.AGIUpstream;

import junit.framework.TestCase;

public class UpstreamReaderTest extends TestCase {

   public void testNextAGI() {
      String text = ">AT1G01010 | chr1:2631-3630 FORWARD\nAC\nC\nA\n>AT1G01020 | chr1:2631-3630 FORWARD\nCA\nAC\n";
      InputStream is = new ByteArrayInputStream(text.getBytes());
      UpstreamReader ur = new UpstreamReader(is);

      AGIUpstream agiUpstream = ur.nextAGIUpstream();
      assertEquals(agiUpstream.getAgi(), AGI.createAGI("AT1G01010"));
      assertEquals(agiUpstream.getUpstream(), "ACCA");

      agiUpstream = ur.nextAGIUpstream();
      assertEquals(agiUpstream.getAgi(), AGI.createAGI("AT1G01020"));
      assertEquals(agiUpstream.getUpstream(), "CAAC");
   }

   public void testUpstream1000() {
      String text = ">AT1G01010 | chr1:2631-3630 FORWARD\n"
            + "ATATTGCTATTTCTGCCAATATTAAAACTTCACTTAGGAAGACTTGAACCTACCACACGTTAGTGACTAATGAGAGCCAC\n"
            + "TAGATAATTGCATGCATCCCACACTAGTACTAATTTTCTAGGGATATTAGAGTTTTCTAATCACCTACTTCCTACTATGT\n"
            + "GTATGTTATCTACTGGCGTGGATGCTTTTAAAGATGTTACGTTATTATTTTGTTCGGTTTGGAAAACGGCTCAATCGTTA\n"
            + "TGAGTTCGTAAGACACATACATTGTTCCATGATAAAATGCAACCCCACGAACCATTTGCGACAAGCAAAACAACATGGTC\n"
            + "AAAATTAAAAGCTAACAATTAGCCAGCGATTCAAAAAGTCAACCTTCTAGATGGATTTAACAACATATCGATAGGATTCA\n"
            + "AGATTAAAAATAAGCACACTCTTATTAATGTTAAAAAACGAATGAGATGAAAATATTTGGCGTGTTCACACACATAATCT\n"
            + "AGAAGACAGATTCGAGTTGCTCTCCTTTGTTTTGCTTTGGGAGGGACCCATTATTACCGCCCAGCAGCTTCCCAGCCTTC\n"
            + "CTTTATAAGGCTTAATTTATATTTATTTAAATTTTATATGTTCTTCTATTATAATACTAAAAGGGGAATACAAATTTCTA\n"
            + "CAGAGGATGATATTCAATCCACGGTTCACCCAAACCGATTTTATAAAATTTATTATTAAATCTTTTTTAATTGTTAAATT\n"
            + "GGTTTAAATCTGAACTCTGTTTACTTACATTGATTAAAATTCTAAACCATCATAAGTAAAAAATAATATGATTAAGACTA\n"
            + "ATAAATCTTAATAGTTAATACTACTCGGTTTACTACATGAAATTTCATACCATCAATTGTTTTAATAATCTTTAAAATTG\n"
            + "TTAGGACCGGTAAAACCATACCAATTAAACCGGAGATCCATATTAATTTAATTAAGAAAATAAAAATAAAAGGAATAAAT\n"
            + "TGTCTTATTTAAACGCTGACTTCACTGTCTTCCTCCCTCC";

      InputStream is = new ByteArrayInputStream(text.getBytes());
      UpstreamReader ur = new UpstreamReader(is);

      AGIUpstream agiUpstream = ur.nextAGIUpstream();
      assertEquals(agiUpstream.getAgi(), AGI.createAGI("AT1G01010"));
      assertEquals(agiUpstream.getUpstream().length(), 1000);
   }
}
