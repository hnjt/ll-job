//package com.example.demo.quartz;
//
//import com.alibaba.fastjson.JSONObject;
//import com.richfit.job.dao.JobEntityRepository;
//import com.richfit.job.domain.JobEntity;
//import com.richfit.job.service.JobService;
//import com.utils.HttpClientUtil;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class quartzTests{
//
//
//	@Test
//	public void tests() {
//        System.out.println("ok!");
//	}
//
//
//	@Test
//	public void addQuartzJob () {
////		DynamicJob dynamicJob = new DynamicJob();
////		System.out.println(dynamicJob.getClass().toString());
////		System.out.println(dynamicJob.getClass().getName());
////		Class<DynamicJob> dynamicJobClass = DynamicJob.class;
//		try {
//			Class<?> aClass = Class.forName( "com.richfit.job.execute.DynamicJob" );
//			System.out.println(aClass.getName());
//			System.out.println(aClass.toString());
//		}catch (Exception e){
//			e.printStackTrace();
//		}
//	}
//
//	@Autowired
//	private JobEntityRepository jobEntityRepository;
//
//	@Test
//	public void getJobs (){
//		List<JobEntity> all = this.jobEntityRepository.findAll();
//		System.out.println(all);
//		ArrayList<JobEntity> list = new ArrayList<>();
//		all.forEach( list::add );
//		System.out.println(list);
//	}
//	@Value( "${auth-exception-ip}" )
//    private String authExceptionIp;
//	@Value( "${auth-exception-port}" )
//    private String authExceptionPort;
//	@Test
//    public void getJobExecute (){
//	    //String requestStr, String ipStr,String protStr,String methodStr ,Map<String ,String> param
//	    String requestStr = "get";
//	    String ipStr = authExceptionIp;
//	    String protStr = authExceptionPort;
//	    String methodStr = "/v3.0.1/codeitem/getCodeItem";
//	    String itemNo = "leak";
//        HashMap<String, String> paramsMap = new HashMap<>();
//        paramsMap.put( "itemNo", itemNo.toUpperCase());
//        String s = HttpClientUtil.doRequest( requestStr, ipStr, protStr, methodStr, paramsMap );
//		JSONObject jsonObject = JSONObject.parseObject( s ).getJSONObject( "data" );
//		Map<String, Object> map = jsonObject.getInnerMap();
//		System.out.println(map.get( "other" ));
//    }
//
//    @Test
//    public void getDate(){
//	    String data = "12-31";
//        String[] split = data.split( "-" );
//        System.out.println("月:" + split[0] + "日 : " + split[1]);
//    }
//
//    @Autowired
//    private JobService jobServiceImpl;
//    @Test
//    public void getPageJobs (){
//        HashMap<String, String> map = new HashMap<>();
////        map.put( "pageNum", "1");
////        map.put( "pageSize", "20");
////        map.put( "name", "月份测试");
////        map.put( "ip", "204");
////        map.put( "creator", "0322931fc0fb4846883ad30cdbde0dfe");
//        List<Map<String, Object>> jobs = this.jobServiceImpl.getJobs( map );
//        for (Map<String, Object> job : jobs) {
//            System.out.println(job);
//        }
//    }
//
//	@Value( "${iscp-task-asset-detect.ip}" )
//	private String assetDetectIp;
//	@Value( "${iscp-task-asset-detect.port}" )
//	private String assetDetectPort;
//
//	@Value( "${iscp-api-merge.ip}" )
//	private String leakIp;
//	@Value( "${iscp-api-merge.port}" )
//	private String leakPort;
//
//	@Test
//	public void getConfig (){
//		System.out.println(assetDetectIp + " : " + assetDetectPort);
//		System.out.println(leakIp + " : " + leakPort);
//	}
//}
