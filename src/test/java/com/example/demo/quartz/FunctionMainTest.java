//package com.example.demo.quartz;
//
//import com.richfit.job.domain.JobEntity;
//
//public class FunctionMainTest {
//
//    /**
//     * 对关键性if判断main方法测试
//     * starts 值必须是一，cnt值必须是n/0-60之间正整数
//     * 测试通过
//     * @param args
//     */
//    public static void main(String[] args) {
//        try {
//            JobEntity entity = new JobEntity();
//            entity.setCnt( "n" );
////            entity.setCnt( "s" );
////            entity.setCnt( "0" );
////            entity.setCnt( "60" );
////            entity.setCnt( "61" );
////            entity.setCnt( "20" );
////            entity.setCnt( "1" );
////            entity.setCnt( "-1" );
////            entity.setStatus( "1" );
//            entity.setStatus( "0" );
//            System.out.println(entity.toString());
//            if (entity.getStatus().equals("1")
//                    &&(
//                    entity.getCnt().toUpperCase().equals( "N" )||
//                            (
//                                    Integer.valueOf( entity.getCnt() )>0&&Integer.valueOf( entity.getCnt() )<=60
//                            )
//            )
//            ){
//                System.out.println("OPEN");
//            }else {
//                System.out.println("CLOSE");
//            }
//        }catch (NumberFormatException e){
//            System.out.println("CLOSE");
//        }
//    }
//}
