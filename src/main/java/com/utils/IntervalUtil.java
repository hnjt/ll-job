package com.utils;

import java.util.*;


public class IntervalUtil {
	
	/**
	 * 判断parent是否包含child
	 * @param parentList
	 * @param child
	 * @return
	 */
	public static boolean contains(List<Interval> parentList,Interval child){
		//先做合并然后再比较
		List<Interval> mp = merge(parentList);
		boolean flag = false;
		for(Interval p:mp){
			if(child.getStart()>= p.getStart()&&child.getEnd()<=p.getEnd()){
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	
	
	/**
	 * 区间合并
	 * @param intervals
	 * @return
	 * For example, 
	 * Given [1,3],[2,6],[8,10],[15,18], 
	 * return [1,6],[8,10],[15,18]. 
	 */
	public static List<Interval> merge(List<Interval> intervals) {
        List<Interval> result = new LinkedList<>();

        if (intervals == null || intervals.size() < 1) {
            return result;
        }

        // 先对区间进行排序，使用一个匿名内部类
        Collections.sort(intervals);

        
//        Collections.sort(list, c);

        // 排序后，后一个元素（记为next）的start一定是不小于前一个（记为prev）start的，
        // 对于新添加的区间，如果next.start大于prev.end就说明这两个区间是分开的，要添
        // 加一个新的区间，否则说明next.start在[prev.start, prev.end]内，则只要看
        // next.end是否是大于prev.end，如果大于就要合并区间（扩大）
        Interval prev = null;
        for (Interval item : intervals) {
            if (prev == null || (item.start - prev.end >1)) {
                result.add(item);
                prev = item;
            } else if (prev.end < item.end) {
                prev.end = item.end;
            }

//            if (prev == null || prev.end < item.start) {
//                result.add(item);
//                prev = item;
//            } else if (prev.end < item.end) {
//                prev.end = item.end;
//            }
        }
        return result;
    }
	
	/**
	 * @param args
	 * @throws Exception 
	 */
//	public static void main(String[] args) {
//		//包含测试
//		Interval int1 = new Interval(1,3);
//		Interval int2 = new Interval(2,6);
//		Interval int3 = new Interval(8,10);
//		Interval int4 = new Interval(15,18);
//
//		Interval c = new Interval(6,9);
//		List<Interval> parent = new ArrayList<Interval>();
//		parent.add(int1);
//		parent.add(int2);
//		parent.add(int3);
//		parent.add(int4);
//		Collections.sort(parent);
//		System.out.println(contains(parent,c));
//
//
//	}
}
