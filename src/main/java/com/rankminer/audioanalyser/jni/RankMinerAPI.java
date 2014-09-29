package com.rankminer.audioanalyser.jni;



public class RankMinerAPI {
	public static native double[] processFile(byte[] data);
	public static native double[] aggregate(double[][] source);
	public static native byte[] train(double[][] data, int[] targetValues);
	public static native double[] predict(double[][] src, byte[] model);

}
