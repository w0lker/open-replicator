package com.google.code.or;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.or.binlog.BinlogEventListener;
import com.google.code.or.binlog.BinlogEventV4;

public class OpenReplicatorTest {
	//
	private static final Logger LOGGER = LoggerFactory.getLogger(OpenReplicatorTest.class);

	/**
	 * 
	 */
	public static void main(String args[]) throws Exception {
		//
		final OpenReplicator or = new OpenReplicator();
		or.setUser("so");
		or.setPassword("123456");
		or.setHost("n3.secoo-inc.com");
		or.setPort(3306);
		or.setServerId(999);
		or.setBinlogPosition(120);
		or.setBinlogFileName("mysql-bin.000005");
		or.setBinlogEventListener(new BinlogEventListener() {
		    public void onEvents(BinlogEventV4 event) {
		    	LOGGER.info("{}", event);
		    }
		});
		or.start();

		//
		LOGGER.info("press 'q' to stop");
		final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		for(String line = br.readLine(); line != null; line = br.readLine()) {
		    if(line.equals("q")) {
		        or.stop(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
		        break;
		    }
		}
	}
}
