package com.cst438;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class InsulinPump {
	private static final int HI_LEVEL = 130;
	private static final int LO_LEVEL = 70;
	private static final int CRITICAL_HI = 220;
	private static final int MAX_LOG_SIZE = 2000;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
	
	private ArrayList<String> log = new ArrayList<>();
	private DeviceReader rdr;
	private DeviceOutput out;
	
	public InsulinPump(DeviceReader rdr, DeviceOutput out) {
		this.rdr = rdr;
		this.out = out;		
	}	
	public synchronized void check(long timeMillis) {
		//TO DO
        // check is called every one minute 
        // timeMillis – current unix time in milliseconds 
		// read the glucose level
		int reading = rdr.getGlucoseLevel();
		
		String s = sdf.format(new java.util.Date(timeMillis));
		// take actions as needed
		String action = "";
		String alarm = "";
		
        //  reading < LO_LEVEL, raise alarm2
		if (reading < LO_LEVEL) {
			action = "NO_ACTION";
			alarm = "ALARM_2";
			out.alarm2();

		}
        //  LO_LEVEL <= reading < HI_LEVEL  normal
		else if (LO_LEVEL <= reading && reading < HI_LEVEL) {
			action = "NO_ACTION";
			alarm = "NO_ALARM";
		}
        //  HI_LEVEL <= reading < CRITICAL_HI, deliver 1 unit
		else if (HI_LEVEL <= reading && reading < CRITICAL_HI) {
			action = "PUMP_UNIT";
			out.pumpOneUnit();
		}
		//  CRITICAL_HI <= reading,  raise alarm1, deliver 1 unit 
		else if (CRITICAL_HI <= reading) {
			action = "PUMP_UNIT";
			alarm = "ALARM_1";
			out.alarm1();
			out.pumpOneUnit();
		}
		// create log record as a string value
        //  MM-dd HH:mm <glucose level> <action> <alarm>
        // keep at most MAX_LOG_SIZE log records 
		String logRecord = s + " " + rdr.getGlucoseLevel() + " " + action + " " + alarm + "\n";
		if (log.size() < MAX_LOG_SIZE) {
			log.add(logRecord);
		}
		
		else {
			log.clear();
			log.add(logRecord);
		}
	}
	
	public synchronized String[] getLog(int skip, int limit) {
		//TO DO
		for(int i = skip; i < limit; i++) {
			System.out.print(log.get(i));
		}
		return null;
	}
}
