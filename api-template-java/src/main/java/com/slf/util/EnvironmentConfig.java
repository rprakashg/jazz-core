// =========================================================================
// Copyright 2017 T-Mobile USA, Inc.
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// =========================================================================

package com.slf.util;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.slf.exceptions.BadRequestException;
import com.slf.model.Request;

/**
 * The environment configuration reader class. Environment configurations can be specified in a properties file. 
 * Each environment will be having a separate properties file. For ex. dev.properties for 'DEV' 
 * 
 * Usage:
 * EnvironmentConfig configObject = new EnvironmentConfig(stage);
 * String restUri = configObject.getConfig("ES_URL");

 * @author DSundar3
 * @version 
 *
 */
public class EnvironmentConfig {
	
	static final Logger logger = Logger.getLogger(EnvironmentConfig.class);
	
	private static Properties props = new Properties();
	private static String stage = null; 

	public EnvironmentConfig(Request input) throws Exception {
		super();
		
		if(null != input.getStage()) {
			stage = input.getStage().toLowerCase();
		}

		if(stage.isEmpty()) {
			throw new BadRequestException("Invalid Stage. Can't load ENV configurations");
		}
		
		String configFile = "/"+stage+".properties";
		logger.info("Loading configuration file for env..:"+configFile);
		props.load(this.getClass().getResourceAsStream(configFile));
	}
	
	
	public String getConfig(String key) {
		if(props != null) {
			String value = props.getProperty(key);
			return value;
		}
		return null;
	}


	@Override
	public String toString() {
		return "Loaded config for "+stage;
	}
	
	
}


