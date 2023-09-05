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
        String str_time = sdf.format(new Date(timeMillis));
        // take actions as needed
        //  reading < LO_LEVEL, raise alarm2
        int gluLevel = this.rdr.getGlucoseLevel();
        String alarm = "";
        
        if (this.rdr.getGlucoseLevel() < LO_LEVEL) {
        	this.out.alarm2();
        	alarm = "ALARM_2";
        }
        //  LO_LEVEL <= reading < HI_LEVEL  normal
        else if (LO_LEVEL <= gluLevel && gluLevel < HI_LEVEL) {
        	this.out.resetAlarms();
        	alarm = "";
        }
        //  HI_LEVEL <= reading < CRITICAL_HI, deliver 1 unit
        else if (HI_LEVEL <= gluLevel && gluLevel < CRITICAL_HI) {
        	this.out.pumpOneUnit();
        }
        //  CRITICAL_HI <= reading,  raise alarm1, deliver 1 unit
        else if (CRITICAL_HI <= gluLevel) {
        	this.out.alarm1();
        	this.out.pumpOneUnit();
        	alarm = "ALARM_1";
        }
	    // create log record as a string value
        //  MM-dd HH:mm <glucose level> <action> <alarm>
        String logRecord = str_time + " " + gluLevel + " " + alarm;
        // keep at most MAX_LOG_SIZE log records
        
        if (this.log.size() < MAX_LOG_SIZE) {
        	this.log.add(0, logRecord);
        }
        
        else {
        	this.log.clear();
        	this.log.add(0, logRecord);
        }

	}
	
	public synchronized String[] getLog(int skip, int limit) {
		//TO DO
		// return the most recent log records 
        // skip – where to start from the most recent record
	    //      0 – start with most recent record
		if (skip == 0) {
			for (int i = 0; i < limit; i++) {
				
			}
			return log;
		}
		
		else {
			for (int i = skip; i < limit; i++) {
				this.log.get(i);
			}
		}
        // limit – maximum number of log records to return 

	}
}
