package com.utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.exception.MyException;
import org.apache.commons.lang3.StringUtils;

/**
* IP工具类
* 
* @author bl
* @email kutekute00@gmail.com
* 
*/
public class IpUtils {
	//192.168.1.1/24
	private static final String maskStr = "(((1[0-9][0-9])|(2[0-4][0-9])|(25[0-5])|([1-9][0-9])|([0-9]))\\.){3}((1[0-9][0-9])|(2[0-4][0-9])|(25[0-5])|([1-9][0-9])|([0-9]))\\/(\\d|([1,2]\\d)|(3[0-2]))";
	//102.168.1.1-255
	private static final String rangStr = "(((1[0-9][0-9])|(2[0-4][0-9])|(25[0-5])|([1-9][0-9])|([0-9]))\\.){3}((1[0-9][0-9])|(2[0-4][0-9])|(25[0-5])|([1-9][0-9])|([0-9]))-((1[0-9][0-9])|(2[0-4][0-9])|(25[0-5])|([1-9][0-9])|([0-9]))";
	//192.168.1.1
	private static final String ipStr = "(((1[0-9][0-9])|(2[0-4][0-9])|(25[0-5])|([1-9][0-9])|([0-9]))\\.){3}((1[0-9][0-9])|(2[0-4][0-9])|(25[0-5])|([1-9][0-9])|([0-9]))";
	//102.168.1.1-102.168.8.254
	private static final String startAndEndStr = "(((1[0-9][0-9])|(2[0-4][0-9])|(25[0-5])|([1-9][0-9])|([0-9]))\\.){3}((1[0-9][0-9])|(2[0-4][0-9])|(25[0-5])|([1-9][0-9])|([0-9]))-(((1[0-9][0-9])|(2[0-4][0-9])|(25[0-5])|([1-9][0-9])|([0-9]))\\.){3}((1[0-9][0-9])|(2[0-4][0-9])|(25[0-5])|([1-9][0-9])|([0-9]))";
	
	private static final String[] STANDARDIPS={"1/24","1-","-254"};
	
	private static final String[] SPECIALIPS={"0/24","0-","-255"};
	/**
	 * 判断ip段是否在指定ip段范围内
	 * @param parentIps
	 * @param childIps
	 * @return
	 * @throws Exception
	 */
	public static boolean ipContains (String parentIps,String childIps){
		List<Interval> parentList = getIpIntervalList(parentIps);
		List<Interval> childList = getIpIntervalList(childIps);
		int successNum = 0;
		if(childList!=null && childList.size()>0){
			for(Interval child:childList){
				if(IntervalUtil.contains(parentList, child)){
					successNum++;
				}
			}
		}
		if(successNum==childList.size()){
			return true;
		}else{
			return false;
		}
	}
	
	

	/**
	 * 判断ip段是否在指定ip段范围内
	 * @param intervalList
	 * @param ips
	 * @return
	 * @throws Exception
	 */
	public static boolean ipContains (List<Interval> intervalList,String ips){
		List<Interval> childList = getIpIntervalList(ips);
		int successNum = 0;
		if(childList!=null && childList.size()>0){
			for(Interval child:childList){
				if(IntervalUtil.contains(intervalList, child)){
					successNum++;
				}else{
					throw new RuntimeException(child.getIps()+"不在授权扫描的范围内");
				}
			}
		}
		if(successNum==childList.size()){
			return true;
		}else{
			return false;
		}
	}
    
//    public static void main(String[] args) {
////        String ip="192.168.1.1";
////        String mask="24";
////        System.out.println(parseIpMaskRange(ip, mask).size());;
//    	try {
//    		// 本页调试spring未启动，不能调试查询语句
////    		List<Map<String,Object>> ipList = new UtilService().getSysRoleIpDetail("029db5a47d7f4d2c960932bb0ac648f9");
////    		String ipList = new UtilService().getRoleTaskAble("4c329362bd074af7aeaa6c1a17161744","");
////			getStartAndEndIpLong("192.168.1.1/24");
////			192.168.0.0-192.168.6.254;
////			192.168.0-6.*;
////			192.0-6.*.*;
////			192.*;
////			192.168.8;
////			System.out.println(ipContains("1.11.1.1-11.11.254.145;1.11.1.1-11.11.254.145;","11.11.184.144"));
//			List<Interval> list = getIpIntervalList("11.11.184.144;11.11.184.145;11.11.183.140-144;11.11.183.145;");
//			System.out.println(IntervalUtil.merge(list));
////    		System.out.println(InetAddress.getByName("10.86.14.7/jdtimas/" ));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    }
    

    /**
     * 获取ipv各段中Ip列表
     * @param ipv
     * @return
     */
    @SuppressWarnings("unchecked")
	public static List<String> getIpListAll(String ipv){
    	List<String> ipListAll = new ArrayList<String>();
    	try{
    		
    	List<Map<String,Object>> listIpMap = getStartAndEndIpLong(ipv);
			for(Map<String,Object> ipMap : listIpMap){
				ipListAll.addAll((List<String>)ipMap.get("IPLIST"));
			}
    	}catch (Exception e) {
    		e.printStackTrace();
    		ipListAll = null;
    	}
    	return ipListAll;
    }
    
    /**
     * 校验ip地址，注意不是地址段
     * @param ip
     * @return
     */
    public static boolean virifyIp(String ip){
    	ip = ip.replaceAll("[\\r\\n\\s]", "");
		String maskStr = "(((1[0-9][0-9])|(2[0-4][0-9])|(25[0-5])|([1-9][0-9])|([0-9]))\\.){3}((1[0-9][0-9])|(2[0-4][0-9])|(25[0-5])|([1-9][0-9])|([0-9]))";
		Pattern pattern = Pattern.compile(maskStr);
		Matcher matcher = pattern.matcher(ip);
		return matcher.matches();
		
		}
    /**
     * 获取ip段中的所有ip地址
     * @param ipv
     * @return
     * @throws Exception
     */
    public static List<Interval> getIpIntervalList(String ipv){
    	ipv = ipv.replaceAll("[\\r\\n\\s]", "");
    	List<Interval> list = new ArrayList<Interval>();
    	if(StringUtils.isNotEmpty(ipv)){
    		String ips[] = ipv.split(";");
    		if(ips.length>0){
    			for(String ip : ips){
    				//去掉空格，回车
    
    				if(StringUtils.isNotEmpty(ip)){
    					List<String> listStr = new ArrayList<String>();
        				listStr.add(maskStr);
        				listStr.add(rangStr);
        				listStr.add(ipStr);
        				listStr.add(startAndEndStr);
        				boolean flag = false;
        				for(int i =0 ;i<listStr.size();i++){
        					String ipRange = listStr.get(i);
        						Pattern pattern = Pattern.compile(ipRange);
                				Matcher matcher = pattern.matcher(ip);
                				if(matcher.matches()){
                					if(i==0){
                						Interval interval = getIpMaskInterval(ip);
                        				list.add(interval);
                        				flag = true;
                					}else if(i==1){
                						Interval interval = getIpRangeInterval(ip);
                        				list.add(interval);
                        				flag = true;
                					}else if(i==2){
                						Interval interval = getIpInterval(ip);
                        				list.add(interval);
                        				flag = true;
                					}else if(i==3){
                						Interval interval = getStartAndEndInterval(ip);
                						list.add(interval);
                        				flag = true;
                					}
                				}
        				}
        				if(!flag){
        					throw new RuntimeException("ip段："+ip+"不符合格式要求");
        				}
    				}
    			}
    		}
    	}
    	return list;
    }	
    	
    
    /**
     * 获取ip段中的所有ip地址
     * @param ipv
     * @return
     * @throws Exception
     */
    public static List<Map<String,Object>> getStartAndEndIpLong(String ipv){
    	ipv=changeIpToStandard(ipv);
    	ipv = ipv.replaceAll("[\\r\\n\\s]", "");
    	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
    	Set<String> ipSet = new HashSet<String>();
    	if(StringUtils.isNotEmpty(ipv)){
    		String ips[] = ipv.split(";");
    		if(ips.length>0){
    			for(String ip : ips){
    				//去掉空格，回车
//    				ip = ip.replaceAll("\r", "").replaceAll("\n", "").replaceAll(" ", "");
    				if(ipSet.contains(ip)){
    					throw new MyException("REPEAT IP","ip段："+ip+"重复");
    				}else{
    					ipSet.add(ip);
    				}
    				if(StringUtils.isNotEmpty(ip)){
    					List<String> listStr = new ArrayList<String>();
        				listStr.add(maskStr);
        				listStr.add(rangStr);
        				listStr.add(ipStr);
        				listStr.add(startAndEndStr);
        				boolean flag = false;
        				for(int i =0 ;i<listStr.size();i++){
        					String ipRange = listStr.get(i);
        					Pattern pattern = Pattern.compile(ipRange);
            				Matcher matcher = pattern.matcher(ip);
            				if(matcher.matches()){
            					if(i==0){
            						Map<String,Object> map = getSADIpMaskRange(ip);
                    				list.add(map);
                    				flag = true;
            					}else if(i==1){
            						Map<String,Object> map = getSADIpRange(ip);
                    				list.add(map);
                    				flag = true;
            					}else if(i==2){
            						Map<String,Object> map = getSADIp(ip);
                    				list.add(map);
                    				flag = true;
            					}else if(i==3){
            						Map<String,Object> map = getSADStartAndEnd(ip);
            						list.add(map);
                    				flag = true;
            					}
            					
            				}
        				}
        				if(!flag){
        					throw new RuntimeException("ip段："+ip+"不符合格式要求");
        				}
    				}
    				
    			}
    		}
    	}
    	return list;
    }
    /**
     * 获取子网掩码方式的ip列表
     * @param ipv
     * @return
     */
    public static Map<String,Object> getSADIpMaskRange(String ipv){
    	String ip = ipv.substring(0,ipv.indexOf("/"));
    	String mask = ipv.substring(ipv.indexOf("/")+1,ipv.length());
    	Long start =  getBeginIpLong(ip,  mask);
    	Long end =  getIpFromString(getEndIpStr(ip,  mask));
    	Map<String,Object> map = new HashMap<String,Object>();
    	List<String> ipList =  parseIpMaskRange(ip,mask);
    	map.put("START", start);
    	map.put("END", end);
    	map.put("SIZE", ipList.size());
    	map.put("IPV", ipv);
    	map.put("IPLIST", ipList);
    	return map;
    }
    /**
     * 获取子网掩码方式的ip区间
     * @param ipv
     * @return
     */
    public static Interval getIpMaskInterval(String ipv){
    	String ip = ipv.substring(0,ipv.indexOf("/"));
    	String mask = ipv.substring(ipv.indexOf("/")+1,ipv.length());
    	String ipEnd = getEndIpStr(ip,  mask);
    	
    	Long start =  getBeginIpLong(ip,  mask);
    	Long end =  getIpFromString(ipEnd);
    	Interval interval = new Interval(ip,ipEnd,start,end);
    	interval.setIps(ipv);
    	return interval;
    }
    /**
     * 获取ip段方式的ip列表
     * @param ipv
     * @return
     */
    public static Map<String,Object> getSADIpRange(String ipv){
    	//结束位
    	String endStr = ipv.substring(ipv.lastIndexOf("-")+1,ipv.length());
    	//开始位
    	String startStr = ipv.substring(ipv.lastIndexOf(".")+1,ipv.lastIndexOf("-"));
    	
    	int endInt = Integer.valueOf(endStr);
    	int startInt = Integer.valueOf(startStr);
    	
    	String ipHead = ipv.substring(0,ipv.lastIndexOf("."));
    	List<String> ipList = new ArrayList<String>();
    	for(int i = startInt;i<=endInt;i++){
    		String ip = ipHead+"."+String.valueOf(i);
    		ipList.add(ip);
    	}
    	
    	Long start = getIpFromString(ipHead+"."+startStr);
    	Long end = getIpFromString(ipHead+"."+endStr);
    	
 
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("START", start);
    	map.put("END", end);
    	map.put("IPV", ipv);
    	map.put("SIZE", ipList.size());
    	map.put("IPLIST", ipList);
    	return map;
    }
    
    
    /**
     * 获取ip段方式的ipInterval
     * @param ipv
     * @return
     */
    public static Interval getIpRangeInterval(String ipv){
    	//结束位
    	String endStr = ipv.substring(ipv.lastIndexOf("-")+1,ipv.length());
    	//开始位
    	String startStr = ipv.substring(ipv.lastIndexOf(".")+1,ipv.lastIndexOf("-"));
    	
    	int endInt = Integer.valueOf(endStr);
    	int startInt = Integer.valueOf(startStr);
    	
    	String ipHead = ipv.substring(0,ipv.lastIndexOf("."));
    	List<String> ipList = new ArrayList<String>();
    	for(int i = startInt;i<=endInt;i++){
    		String ip = ipHead+"."+String.valueOf(i);
    		ipList.add(ip);
    	}
    	
    	Long start = getIpFromString(ipHead+"."+startStr);
    	Long end = getIpFromString(ipHead+"."+endStr);
    	Interval interval = new Interval(start,end);
    	interval.setIps(ipv);
    	return interval;
    }
    
    /**
     * 根据起始，截止ip地址，获取ip列表
     * @param ipv
     * @return
     */
    public static Map<String,Object> getSADStartAndEnd(String ipv){
    	//结束位
    	String endStr = ipv.substring(ipv.lastIndexOf("-")+1,ipv.length());
    	//开始位
    	String startStr = ipv.substring(0,ipv.lastIndexOf("-"));
//    	
    	
    	Long start = getIpFromString(startStr);
    	Long end = getIpFromString(endStr);
    	
    	List<String> ipList = new ArrayList<String>();
    	for(Long i = start;i<=end;i++){
    		String ip = getIpFromLong(i);
    		ipList.add(ip);
    	}
    	
    	
    	if(start>end){
    		throw new MyException("REPEAT IP",ipv+"的截止ip比起始ip要大，不符合要求");
    	}
    	
 
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("START", start);
    	map.put("END", end);
    	map.put("IPV", ipv);
    	map.put("SIZE", ipList.size());
    	map.put("IPLIST", ipList);
    	return map;
    }
    
    
    /**
     * 根据起始，截止ip地址，获取ipInterval
     * @param ipv
     * @return
     */
    public static Interval getStartAndEndInterval(String ipv){
    	//结束位
    	String endStr = ipv.substring(ipv.lastIndexOf("-")+1,ipv.length());
    	//开始位
    	String startStr = ipv.substring(0,ipv.lastIndexOf("-"));
//    	
    	
    	Long start = getIpFromString(startStr);
    	Long end = getIpFromString(endStr);
    	
    	Interval interval = new Interval(start,end);
    	interval.setIps(ipv);
    	return interval;
    }
    
    /**
     * 获取单ip列表
     * @param ipv
     * @return
     */
    public static Map<String,Object> getSADIp(String ipv){
    	
    	List<String> ipList = new ArrayList<String>();

    	Long start = getIpFromString(ipv);
    	Long end = getIpFromString(ipv);
    	ipList.add(ipv);
 
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("START", start);
    	map.put("END", end);
    	map.put("SIZE", ipList.size());
    	map.put("IPV", ipv);
    	map.put("IPLIST", ipList);
    	return map;
    }
    
    /**
     * 获取单ip Interval
     * @param ipv
     * @return
     */
    public static Interval getIpInterval(String ipv){
    	Long start = getIpFromString(ipv);
    	Long end = getIpFromString(ipv);
    	Interval interval = new Interval(start,end);
    	interval.setIps(ipv);
    	return interval;
    	
    }
    
    public static String getIpRange(List<Map<String,Object>> ipList,String ip){
    	Long ipLong = getIpFromString(ip);
    	String ipRange = "";
    	if(ipList!=null && ipList.size()>0){
    		for(Map<String,Object> ips :ipList){
    			Long start = Long.valueOf(ips.get("START").toString());
    			Long end = Long.valueOf(ips.get("END").toString());
    			String ipv = ips.get("IPV").toString();
    			//ip在一个段内
    			if(ipLong>=start && ipLong<= end){
    				ipRange = ipv;
    				
    				break;
    			}
    		}
    	}
    	return ipRange;
    }
    
    public static List<String> parseIpMaskRange(String ip,String mask){
        List<String> list=new ArrayList<String>();
        if ("32".equals(mask)) {
            list.add(ip);
        }else{
            String startIp=getBeginIpStr(ip, mask);
            String endIp=getEndIpStr(ip, mask);
            
            if (!"31".equals(mask)) {
                String subStart=startIp.split("\\.")[0]+"."+startIp.split("\\.")[1]+"."+startIp.split("\\.")[2]+".";
                String subEnd=endIp.split("\\.")[0]+"."+endIp.split("\\.")[1]+"."+endIp.split("\\.")[2]+".";
                startIp=subStart+(Integer.valueOf(startIp.split("\\.")[3])+1);
                endIp=subEnd+(Integer.valueOf(endIp.split("\\.")[3])-1);
            }
            list=parseIpRange(startIp, endIp);
        }
        return list;
    }

    public static List<String> parseIpRange(String ipfrom, String ipto) {
        List<String> ips = new ArrayList<String>();
        String[] ipfromd = ipfrom.split("\\.");
        String[] iptod = ipto.split("\\.");
        int[] int_ipf = new int[4];
        int[] int_ipt = new int[4];
        for (int i = 0; i < 4; i++) {
            int_ipf[i] = Integer.parseInt(ipfromd[i]);
            int_ipt[i] = Integer.parseInt(iptod[i]);
        }
        for (int A = int_ipf[0]; A <= int_ipt[0]; A++) {
            for (int B = (A == int_ipf[0] ? int_ipf[1] : 0); B <= (A == int_ipt[0] ? int_ipt[1]
                    : 255); B++) {
                for (int C = (B == int_ipf[1] ? int_ipf[2] : 0); C <= (B == int_ipt[1] ? int_ipt[2]
                        : 255); C++) {
                    for (int D = (C == int_ipf[2] ? int_ipf[3] : 0); D <= (C == int_ipt[2] ? int_ipt[3]
                            : 255); D++) {
                        ips.add(new String(A + "." + B + "." + C + "." + D));
                    }
                }
            }
        }
        return ips;
    }
    
    /**
     * 把long类型的Ip转为一般Ip类型：xx.xx.xx.xx
     *
     * @param ip
     * @return
     */
    public static String getIpFromLong(Long ip)
    {
        String s1 = String.valueOf((ip & 4278190080L) / 16777216L);
        String s2 = String.valueOf((ip & 16711680L) / 65536L);
        String s3 = String.valueOf((ip & 65280L) / 256L);
        String s4 = String.valueOf(ip & 255L);
        return s1 + "." + s2 + "." + s3 + "." + s4;
    }
    /**
     * 把xx.xx.xx.xx类型的转为long类型的
     *
     * @param ip
     * @return
     */
    public static Long getIpFromString(String ip)
    {
        Long ipLong = 0L;
        String ipTemp = ip;
        ipLong = ipLong * 256
                + Long.parseLong(ipTemp.substring(0, ipTemp.indexOf(".")));
        ipTemp = ipTemp.substring(ipTemp.indexOf(".") + 1, ipTemp.length());
        ipLong = ipLong * 256
                + Long.parseLong(ipTemp.substring(0, ipTemp.indexOf(".")));
        ipTemp = ipTemp.substring(ipTemp.indexOf(".") + 1, ipTemp.length());
        ipLong = ipLong * 256
                + Long.parseLong(ipTemp.substring(0, ipTemp.indexOf(".")));
        ipTemp = ipTemp.substring(ipTemp.indexOf(".") + 1, ipTemp.length());
        ipLong = ipLong * 256 + Long.parseLong(ipTemp);
        return ipLong;
    }
    /**
     * 根据掩码位获取掩码
     *
     * @param maskBit
     *            掩码位数，如"28"、"30"
     * @return
     */
    public static String getMaskByMaskBit(String maskBit)
    {
        return StringUtils.isEmpty(maskBit) ? "error, maskBit is null !"
                : maskBitMap().get(maskBit);
    }
    
    /**
     * 根据 ip/掩码位 计算IP段的起始IP 如 IP串 218.240.38.69/30
     *
     * @param ip
     *            给定的IP，如218.240.38.69
     * @param maskBit
     *            给定的掩码位，如30
     * @return 起始IP的字符串表示
     */
    public static String getBeginIpStr(String ip, String maskBit)
    {
        return getIpFromLong(getBeginIpLong(ip, maskBit));
    }
    /**
     * 根据 ip/掩码位 计算IP段的起始IP 如 IP串 218.240.38.69/30
     *
     * @param ip
     *            给定的IP，如218.240.38.69
     * @param maskBit
     *            给定的掩码位，如30
     * @return 起始IP的长整型表示
     */
    public static Long getBeginIpLong(String ip, String maskBit)
    {
        return getIpFromString(ip) & getIpFromString(getMaskByMaskBit(maskBit));
    }
    /**
     * 根据 ip/掩码位 计算IP段的终止IP 如 IP串 218.240.38.69/30
     *
     * @param ip
     *            给定的IP，如218.240.38.69
     * @param maskBit
     *            给定的掩码位，如30
     * @return 终止IP的字符串表示
     */
    public static String getEndIpStr(String ip, String maskBit)
    {
        return getIpFromLong(getEndIpLong(ip, maskBit));
    }
    
     /**
     * 根据 ip/掩码位 计算IP段的终止IP 如 IP串 218.240.38.69/30
     *
     * @param ip
     *            给定的IP，如218.240.38.69
     * @param maskBit
     *            给定的掩码位，如30
     * @return 终止IP的长整型表示
     */
    public static Long getEndIpLong(String ip, String maskBit)
    {
    	long end = getBeginIpLong(ip, maskBit)
                + ~getIpFromString(getMaskByMaskBit(maskBit));
        return end;
    }
    
    
      /**
     * 根据子网掩码转换为掩码位 如 255.255.255.252转换为掩码位 为 30
     *
     * @param netmarks
     * @return
     */
    public static int getNetMask(String netmarks)
    {
        StringBuffer sbf;
        String str;
        int inetmask = 0, count = 0;
        String[] ipList = netmarks.split("\\.");
        for (int n = 0; n < ipList.length; n++)
        {
            sbf = toBin(Integer.parseInt(ipList[n]));
            str = sbf.reverse().toString();
            count = 0;
            for (int i = 0; i < str.length(); i++)
            {
                i = str.indexOf('1', i);
                if (i == -1)
                {
                    break;
                }
                count++;
            }
            inetmask += count;
        }
        return inetmask;
    }
    
    /**
     * 计算子网大小
     *
     * @param netmask
     *            掩码位
     * @return
     */
    public static int getPoolMax(int maskBit)
    {
        if (maskBit <= 0 || maskBit >= 32)
        {
            return 0;
        }
        return (int) Math.pow(2, 32 - maskBit) - 2;
    }
    private static StringBuffer toBin(int x)
    {
        StringBuffer result = new StringBuffer();
        result.append(x % 2);
        x /= 2;
        while (x > 0)
        {
            result.append(x % 2);
            x /= 2;
        }
        return result;
    }
    /*
     * 存储着所有的掩码位及对应的掩码 key:掩码位 value:掩码（x.x.x.x）
     */
    private static Map<String, String> maskBitMap()
    {
        Map<String, String> maskBit = new HashMap<String, String>();
        maskBit.put("1", "128.0.0.0");
        maskBit.put("2", "192.0.0.0");
        maskBit.put("3", "224.0.0.0");
        maskBit.put("4", "240.0.0.0");
        maskBit.put("5", "248.0.0.0");
        maskBit.put("6", "252.0.0.0");
        maskBit.put("7", "254.0.0.0");
        maskBit.put("8", "255.0.0.0");
        maskBit.put("9", "255.128.0.0");
        maskBit.put("10", "255.192.0.0");
        maskBit.put("11", "255.224.0.0");
        maskBit.put("12", "255.240.0.0");
        maskBit.put("13", "255.248.0.0");
        maskBit.put("14", "255.252.0.0");
        maskBit.put("15", "255.254.0.0");
        maskBit.put("16", "255.255.0.0");
        maskBit.put("17", "255.255.128.0");
        maskBit.put("18", "255.255.192.0");
        maskBit.put("19", "255.255.224.0");
        maskBit.put("20", "255.255.240.0");
        maskBit.put("21", "255.255.248.0");
        maskBit.put("22", "255.255.252.0");
        maskBit.put("23", "255.255.254.0");
        maskBit.put("24", "255.255.255.0");
        maskBit.put("25", "255.255.255.128");
        maskBit.put("26", "255.255.255.192");
        maskBit.put("27", "255.255.255.224");
        maskBit.put("28", "255.255.255.240");
        maskBit.put("29", "255.255.255.248");
        maskBit.put("30", "255.255.255.252");
        maskBit.put("31", "255.255.255.254");
        maskBit.put("32", "255.255.255.255");
        return maskBit;
    }
    
    /**
     * 根据掩码位获取掩码
     *
     * @param masks
     * @return
     */
    public static String getMaskByMaskBit(int masks)
    {
        String ret = "";
        if (masks == 1)
            ret = "128.0.0.0";
        else if (masks == 2)
            ret = "192.0.0.0";
        else if (masks == 3)
            ret = "224.0.0.0";
        else if (masks == 4)
            ret = "240.0.0.0";
        else if (masks == 5)
            ret = "248.0.0.0";
        else if (masks == 6)
            ret = "252.0.0.0";
        else if (masks == 7)
            ret = "254.0.0.0";
        else if (masks == 8)
            ret = "255.0.0.0";
        else if (masks == 9)
            ret = "255.128.0.0";
        else if (masks == 10)
            ret = "255.192.0.0";
        else if (masks == 11)
            ret = "255.224.0.0";
        else if (masks == 12)
            ret = "255.240.0.0";
        else if (masks == 13)
            ret = "255.248.0.0";
        else if (masks == 14)
            ret = "255.252.0.0";
        else if (masks == 15)
            ret = "255.254.0.0";
        else if (masks == 16)
            ret = "255.255.0.0";
        else if (masks == 17)
            ret = "255.255.128.0";
        else if (masks == 18)
            ret = "255.255.192.0";
        else if (masks == 19)
            ret = "255.255.224.0";
        else if (masks == 20)
            ret = "255.255.240.0";
        else if (masks == 21)
            ret = "255.255.248.0";
        else if (masks == 22)
            ret = "255.255.252.0";
        else if (masks == 23)
            ret = "255.255.254.0";
        else if (masks == 24)
            ret = "255.255.255.0";
        else if (masks == 25)
            ret = "255.255.255.128";
        else if (masks == 26)
            ret = "255.255.255.192";
        else if (masks == 27)
            ret = "255.255.255.224";
        else if (masks == 28)
            ret = "255.255.255.240";
        else if (masks == 29)
            ret = "255.255.255.248";
        else if (masks == 30)
            ret = "255.255.255.252";
        else if (masks == 31)
            ret = "255.255.255.254";
        else if (masks == 32)
            ret = "255.255.255.255";
        return ret;
    }
    

//    public static boolean IsIPLimit(LimitUserIpList limitUser,String ip) throws Exception{
// 	   boolean ret=false;
// 	   if (limitUser==null) {
// 		   ret=true;
// 	   }else if(limitUser.isLimit() && limitUser.getIpList()==null){
// 		   ret=true;
// 	   }else if (limitUser.isLimit() && !limitUser.getIpList().contains(ip)){
// 		   ret=true;
// 	   }
// 	   return ret;
//    }
    public static String changeIpToStandard(String ips){
    	String ip=ips;
    	for (int i = 0; i < SPECIALIPS.length; i++) {
    		ip=ip.replace(SPECIALIPS[i], STANDARDIPS[i]);
		}
    	return ip;
    }
}