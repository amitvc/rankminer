package com.rankminer.audioanalyser.jni;

public class DummyRankMinerImpl extends RankMinerAPI {
	
	public static double[] processFile(byte[] data) {
		return new double[] { 1.0D, 5.0D, 24.0D };
	}
	
	public static native double[] aggregate(double[][] source);
	public static native byte[] train(double[][] data, int[] targetValues);
	public static native double[] predict(double[][] src, byte[] model);


}
