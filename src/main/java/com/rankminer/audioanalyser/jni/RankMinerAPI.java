package com.rankminer.audioanalyser.jni;


/**
 * JNI class which loads the rankminer dll and provides api to calculate feature vectors.
 * @author achavan
 *
 */
public class RankMinerAPI {
	public static native double[] extractFeatures(byte[] data);
	public static native double[] aggregate(double[][] source);
	public static native byte[] train(double[][] data, int[] targetValues);
	public static native double[] predict(double[][] src, byte[] model);
	
	static {
		System.out.println("Loading library");
		System.loadLibrary("rankminer_2_1");		
	}
}
