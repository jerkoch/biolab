package com.bc.path;

import org.apache.commons.math.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math.stat.correlation.SpearmansCorrelation;

public class CorrelationFactory {

	public static Correlation[] getAvailableCorrelations() {
		return new Correlation[] { new Pearsons(), new Spearmans() };
	}

	public static class Pearsons implements Correlation {
		public double calculate(double[] x, double[] y) {
			return new PearsonsCorrelation().correlation(x, y);
		}
		
		@Override
		public String toString() {
			return "Pearsons";
		}
	}

	public static class Spearmans implements Correlation {
		public double calculate(double[] x, double[] y) {
			return new SpearmansCorrelation().correlation(x, y);
		}
		
		@Override
		public String toString() {
			return "Spearmans";
		}
	}
}
