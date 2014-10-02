package com.rankminer.audioanalyser.jni;

import org.apache.log4j.Logger;

import com.rankminer.audioanalyser.config.Client;

/**
 * 
 * @author achavan
 *
 */
public class CoreRankMinerImpl extends RankMinerAPI {
	private static final Logger LOGGER = Logger
			.getLogger(CoreRankMinerImpl.class);
	static {
		LOGGER.error("loading library   rankminer_2.1.dll");
		System.loadLibrary("rankminer_2.1");
	}
}
