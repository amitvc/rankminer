package com.rankminer.audioanalyser.jni;

/**
 * 
 * @author achavan
 *
 */
public class CoreRankMinerImpl extends RankMinerAPI {
	static {
		System.loadLibrary("clifi_gpu.so");
	}
}
