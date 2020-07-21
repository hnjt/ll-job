package com.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Interval implements Serializable, Comparable<Interval> {
	      long start, end;
	      String ipStart;
	      String ipEnd;
	      String ips;
	      long size;
	      
	      
	      
	      
	    public String getIpStart() {
			return ipStart;
		}
		public void setIpStart(String ipStart) {
			this.ipStart = ipStart;
		}
		public String getIpEnd() {
			return ipEnd;
		}
		public void setIpEnd(String ipEnd) {
			this.ipEnd = ipEnd;
		}
		public long getSize() {
			return size;
		}
		public void setSize(long size) {
			this.size = size;
		}
		public String getIps() {
			return ips;
		}
		public void setIps(String ips) {
			this.ips = ips;
		}
		public Interval(long start, long end) {
	          this.start = start;
	          this.end = end;
	      }
		
		public Interval(String ipStart,String ipEnd,long start, long end) {
	          this.start = start;
	          this.end = end;
	          this.ipStart = ipStart;
	          this.ipEnd = ipEnd;
	          this.size = end -start +1;
	      }
		public Interval() {

	      }
		public long getStart() {
			return start;
		}
		public void setStart(long start) {
			this.start = start;
		}
		public long getEnd() {
			return end;
		}
		public void setEnd(long end) {
			this.end = end;
		}

		@Override
		public String toString(){
			JSONObject obj = new JSONObject();
			obj.put("start", this.getStart());
			obj.put("end", this.getEnd());
			obj.put("ipStart", this.getIpStart());
			obj.put("ipEnd", this.getIpEnd());
			return obj.toString();
			
		}


		@Override
		public int compareTo(Interval o) {

			if (this.start > o.start)
				return 1;
			if (this.start < o.start)
				return -1;
			if (this.end > o.end)
				return 1;
			if (this.end < o.end)
				return -1;
			return 0;
		}
}
