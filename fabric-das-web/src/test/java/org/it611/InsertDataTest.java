package org.it611;

import org.it611.das.DasApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashMap;
import java.util.Random;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DasApplication.class)
@WebAppConfiguration
public class InsertDataTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 插入测试数据
     */
    @Test
    public void insertRabbishData2017() {


        for(int k =1; k<=12; k++){
            int m = 0;
            if(k==1 || k==3 || k==5 || k==7 || k==8 || k==10 || k==12){
                m = 31;
            }else if(k ==2){
                m = 28;
            }else{
                m = 30;
            }


            for(int i =1; i<=m; i++) {
                HashMap<String, Object> map = new HashMap();

                //申请总量 100-120
                int degreeCertNumTotal = new Random().nextInt(21)+ 100;
                int videoNumTotal = new Random().nextInt(21)+ 100;
                int audioNumTotal = new Random().nextInt(21)+ 100;
                int photoNumTotal = new Random().nextInt(21)+ 100;
                System.out.println(photoNumTotal);


                map.put("degreeCertNumTotal", String.valueOf(degreeCertNumTotal));
                map.put("videoNumTotal", String.valueOf(videoNumTotal));
                map.put("audioNumTotal", String.valueOf(audioNumTotal));
                map.put("photoNumTotal", String.valueOf(photoNumTotal));//100-150




                int degreeCertNumUnreviewed = new Random().nextInt(11)+ 5;
                int videoNumUnreviewed = new Random().nextInt(11)+ 5;
                int audioNumUnreviewed = new Random().nextInt(11)+ 5;
                int photoNumUnreviewed = new Random().nextInt(11)+ 5;


                //未审核5-15
                map.put("degreeCertNumUnReviewed", String.valueOf(degreeCertNumUnreviewed));
                map.put("videoNumUnReviewed", String.valueOf(videoNumUnreviewed));
                map.put("audioNumUnReviewed", String.valueOf(audioNumUnreviewed));
                map.put("photoNumUnReviewed", String.valueOf(photoNumUnreviewed));


                int degreeCertNumReviewed = new Random().nextInt(21)+ 50;
                int videoNumReviewed = new Random().nextInt(21)+ 50;
                int audioNumReviewed = new Random().nextInt(21)+ 50;
                int photoNumReviewed = new Random().nextInt(21)+ 50;

                //已审核50-70
                map.put("degreeCertNumReviewed", String.valueOf(degreeCertNumReviewed));
                map.put("videoNumReviewed", String.valueOf(videoNumReviewed));
                map.put("audioNumReviewed", String.valueOf(audioNumReviewed));
                map.put("photoNumReviewed", String.valueOf(photoNumReviewed));


                int degreeCertNumUnPass = new Random().nextInt(6)+ 10;//10-20
                int videoNumUnPass = new Random().nextInt(6)+ 10;
                int audioNumUnPass = new Random().nextInt(6)+ 10;
                int photoNumUnPass = new Random().nextInt(6)+ 10;

                //不通过//10-15
                map.put("degreeCertNumUnPass", String.valueOf(degreeCertNumUnPass));
                map.put("videoNumUnPass", String.valueOf(videoNumUnPass));
                map.put("audioNumUnPass", String.valueOf(audioNumUnPass));
                map.put("photoNumUnPass", String.valueOf(photoNumUnPass));//10-20



                int degreeCertCanceled = degreeCertNumTotal-degreeCertNumUnreviewed- degreeCertNumReviewed- degreeCertNumUnPass;
                int videoNumUnCanceled = videoNumTotal-videoNumUnreviewed-videoNumReviewed-videoNumUnPass;
                int audioNumUnCanceled = audioNumTotal-audioNumUnreviewed-audioNumReviewed-audioNumUnPass;
                int photoNumUnCanceled = photoNumTotal-photoNumUnreviewed-photoNumReviewed-photoNumUnPass;
                //注销
                map.put("degreeCertNumCanceled", String.valueOf(degreeCertCanceled));
                map.put("videoNumCanceled", String.valueOf(videoNumUnCanceled));
                map.put("audioNumCanceled", String.valueOf(audioNumUnCanceled));
                map.put("photoNumCanceled", String.valueOf(photoNumUnCanceled));

                String day = "";
                String month = "";
                if(i<10){
                    day = "0"+i;
                }else{
                    day = String.valueOf(i);
                }


                if(k<10){
                    month = "0"+k;
                }else{
                    month = String.valueOf(k);
                }


                map.put("time", "2017-"+month+"-"+day+" 00:00:00");
                mongoTemplate.save(map,"statisticsPerDay");
            }

        }




    }



    @Test
    public void insertRabbishData2018() {


        for(int k =1; k<=5; k++){
            int m = 0;
            if(k==1 || k==3 || k==5 || k==7 || k==8 || k==10 || k==12){
                m = 31;
            }else if(k ==2){
                m = 28;
            }else{
                m = 30;
            }


            for(int i =1; i<=m; i++) {
                HashMap<String, Object> map = new HashMap();

                //申请总量 100-120
                int degreeCertNumTotal = new Random().nextInt(21)+ 100;
                int videoNumTotal = new Random().nextInt(21)+ 100;
                int audioNumTotal = new Random().nextInt(21)+ 100;
                int photoNumTotal = new Random().nextInt(21)+ 100;
                System.out.println(photoNumTotal);


                map.put("degreeCertNumTotal", String.valueOf(degreeCertNumTotal));
                map.put("videoNumTotal", String.valueOf(videoNumTotal));
                map.put("audioNumTotal", String.valueOf(audioNumTotal));
                map.put("photoNumTotal", String.valueOf(photoNumTotal));//100-150




                int degreeCertNumUnreviewed = new Random().nextInt(11)+ 5;
                int videoNumUnreviewed = new Random().nextInt(11)+ 5;
                int audioNumUnreviewed = new Random().nextInt(11)+ 5;
                int photoNumUnreviewed = new Random().nextInt(11)+ 5;


                //未审核5-15
                map.put("degreeCertNumUnReviewed", String.valueOf(degreeCertNumUnreviewed));
                map.put("videoNumUnReviewed", String.valueOf(videoNumUnreviewed));
                map.put("audioNumUnReviewed", String.valueOf(audioNumUnreviewed));
                map.put("photoNumUnReviewed", String.valueOf(photoNumUnreviewed));


                int degreeCertNumReviewed = new Random().nextInt(21)+ 50;
                int videoNumReviewed = new Random().nextInt(21)+ 50;
                int audioNumReviewed = new Random().nextInt(21)+ 50;
                int photoNumReviewed = new Random().nextInt(21)+ 50;

                //已审核50-70
                map.put("degreeCertNumReviewed", String.valueOf(degreeCertNumReviewed));
                map.put("videoNumReviewed", String.valueOf(videoNumReviewed));
                map.put("audioNumReviewed", String.valueOf(audioNumReviewed));
                map.put("photoNumReviewed", String.valueOf(photoNumReviewed));


                int degreeCertNumUnPass = new Random().nextInt(6)+ 10;//10-20
                int videoNumUnPass = new Random().nextInt(6)+ 10;
                int audioNumUnPass = new Random().nextInt(6)+ 10;
                int photoNumUnPass = new Random().nextInt(6)+ 10;

                //不通过//10-15
                map.put("degreeCertNumUnPass", String.valueOf(degreeCertNumUnPass));
                map.put("videoNumUnPass", String.valueOf(videoNumUnPass));
                map.put("audioNumUnPass", String.valueOf(audioNumUnPass));
                map.put("photoNumUnPass", String.valueOf(photoNumUnPass));//10-20



                int degreeCertCanceled = degreeCertNumTotal-degreeCertNumUnreviewed- degreeCertNumReviewed- degreeCertNumUnPass;
                int videoNumUnCanceled = videoNumTotal-videoNumUnreviewed-videoNumReviewed-videoNumUnPass;
                int audioNumUnCanceled = audioNumTotal-audioNumUnreviewed-audioNumReviewed-audioNumUnPass;
                int photoNumUnCanceled = photoNumTotal-photoNumUnreviewed-photoNumReviewed-photoNumUnPass;
                //注销
                map.put("degreeCertNumCanceled", String.valueOf(degreeCertCanceled));
                map.put("videoNumCanceled", String.valueOf(videoNumUnCanceled));
                map.put("audioNumCanceled", String.valueOf(audioNumUnCanceled));
                map.put("photoNumCanceled", String.valueOf(photoNumUnCanceled));

                String day = "";
                String month = "";
                if(i<10){
                    day = "0"+i;
                }else{
                    day = String.valueOf(i);
                }


                if(k<10){
                    month = "0"+k;
                }else{
                    month = String.valueOf(k);
                }


                map.put("time", "2018-"+month+"-"+day+" 00:00:00");
                mongoTemplate.save(map,"statisticsPerDay");
            }

        }




    }
















    /**
     * 插入测试数据
     */
    @Test
    public void insertRabbishData_month2017() {


        //new Random().nextInt(101)+ 50;  生成 50-150

        //命名
        //resultMap.put("Unreviewed", state0);
        //resultMap.put("Reviewed", state1);
        //resultMap.put("Unpass", state2);
        //resultMap.put("Canceled", state3);

        for(int i =1; i<=12; i++) {
            HashMap<String, Object> map = new HashMap();

            //申请总量 100-120
            int degreeCertNumTotal = new Random().nextInt(21)+ 100;
            int videoNumTotal = new Random().nextInt(21)+ 100;
            int audioNumTotal = new Random().nextInt(21)+ 100;
            int photoNumTotal = new Random().nextInt(21)+ 100;
            System.out.println(photoNumTotal);


            map.put("degreeCertNumTotal", String.valueOf(degreeCertNumTotal*30));
            map.put("videoNumTotal", String.valueOf(videoNumTotal*30));
            map.put("audioNumTotal", String.valueOf(audioNumTotal*30));
            map.put("photoNumTotal", String.valueOf(photoNumTotal*30));//100-150




            int degreeCertNumUnreviewed = new Random().nextInt(11)+ 5;
            int videoNumUnreviewed = new Random().nextInt(11)+ 5;
            int audioNumUnreviewed = new Random().nextInt(11)+ 5;
            int photoNumUnreviewed = new Random().nextInt(11)+ 5;


            //未审核5-15
            map.put("degreeCertNumUnReviewed", String.valueOf(degreeCertNumUnreviewed*30));
            map.put("videoNumUnReviewed", String.valueOf(videoNumUnreviewed*30));
            map.put("audioNumUnReviewed", String.valueOf(audioNumUnreviewed*30));
            map.put("photoNumUnReviewed", String.valueOf(photoNumUnreviewed*30));


            int degreeCertNumReviewed = new Random().nextInt(21)+ 50;
            int videoNumReviewed = new Random().nextInt(21)+ 50;
            int audioNumReviewed = new Random().nextInt(21)+ 50;
            int photoNumReviewed = new Random().nextInt(21)+ 50;

            //已审核50-70
            map.put("degreeCertNumReviewed", String.valueOf(degreeCertNumReviewed*30));
            map.put("videoNumReviewed", String.valueOf(videoNumReviewed*30));
            map.put("audioNumReviewed", String.valueOf(audioNumReviewed*30));
            map.put("photoNumReviewed", String.valueOf(photoNumReviewed*30));


            int degreeCertNumUnPass = new Random().nextInt(6)+ 10;//10-20
            int videoNumUnPass = new Random().nextInt(6)+ 10;
            int audioNumUnPass = new Random().nextInt(6)+ 10;
            int photoNumUnPass = new Random().nextInt(6)+ 10;

            //不通过//10-15
            map.put("degreeCertNumUnPass", String.valueOf(degreeCertNumUnPass*30));
            map.put("videoNumUnPass", String.valueOf(videoNumUnPass*30));
            map.put("audioNumUnPass", String.valueOf(audioNumUnPass*30));
            map.put("photoNumUnPass", String.valueOf(photoNumUnPass*30));//10-20



            int degreeCertCanceled = degreeCertNumTotal-degreeCertNumUnreviewed- degreeCertNumReviewed- degreeCertNumUnPass;
            int videoNumUnCanceled = videoNumTotal-videoNumUnreviewed-videoNumReviewed-videoNumUnPass;
            int audioNumUnCanceled = audioNumTotal-audioNumUnreviewed-audioNumReviewed-audioNumUnPass;
            int photoNumUnCanceled = photoNumTotal-photoNumUnreviewed-photoNumReviewed-photoNumUnPass;
            //注销
            map.put("degreeCertNumCanceled", String.valueOf(degreeCertCanceled));
            map.put("videoNumCanceled", String.valueOf(videoNumUnCanceled));
            map.put("audioNumCanceled", String.valueOf(audioNumUnCanceled));
            map.put("photoNumCanceled", String.valueOf(photoNumUnCanceled));

            String month = "";
            if(i<10){
                month = "0"+i;
            }else{
                month = String.valueOf(i);
            }


            map.put("time", "2017-"+month+"-"+"01"+" 00:00:00");
            mongoTemplate.save(map,"statisticsPerMonth");
        }

    }







    /**
     * 插入测试数据
     */
    @Test
    public void insertRabbishData_month2018() {


        //new Random().nextInt(101)+ 50;  生成 50-150

        //命名
        //resultMap.put("Unreviewed", state0);
        //resultMap.put("Reviewed", state1);
        //resultMap.put("Unpass", state2);
        //resultMap.put("Canceled", state3);

        for(int i =1; i<=5; i++) {
            HashMap<String, Object> map = new HashMap();

            //申请总量 100-120
            int degreeCertNumTotal = new Random().nextInt(21)+ 100;
            int videoNumTotal = new Random().nextInt(21)+ 100;
            int audioNumTotal = new Random().nextInt(21)+ 100;
            int photoNumTotal = new Random().nextInt(21)+ 100;
            System.out.println(photoNumTotal);


            map.put("degreeCertNumTotal", String.valueOf(degreeCertNumTotal*30));
            map.put("videoNumTotal", String.valueOf(videoNumTotal*30));
            map.put("audioNumTotal", String.valueOf(audioNumTotal*30));
            map.put("photoNumTotal", String.valueOf(photoNumTotal*30));//100-150




            int degreeCertNumUnreviewed = new Random().nextInt(11)+ 5;
            int videoNumUnreviewed = new Random().nextInt(11)+ 5;
            int audioNumUnreviewed = new Random().nextInt(11)+ 5;
            int photoNumUnreviewed = new Random().nextInt(11)+ 5;


            //未审核5-15
            map.put("degreeCertNumUnReviewed", String.valueOf(degreeCertNumUnreviewed*30));
            map.put("videoNumUnReviewed", String.valueOf(videoNumUnreviewed*30));
            map.put("audioNumUnReviewed", String.valueOf(audioNumUnreviewed*30));
            map.put("photoNumUnReviewed", String.valueOf(photoNumUnreviewed*30));


            int degreeCertNumReviewed = new Random().nextInt(21)+ 50;
            int videoNumReviewed = new Random().nextInt(21)+ 50;
            int audioNumReviewed = new Random().nextInt(21)+ 50;
            int photoNumReviewed = new Random().nextInt(21)+ 50;

            //已审核50-70
            map.put("degreeCertNumReviewed", String.valueOf(degreeCertNumUnreviewed*30));
            map.put("videoNumReviewed", String.valueOf(videoNumUnreviewed*30));
            map.put("audioNumReviewed", String.valueOf(audioNumUnreviewed*30));
            map.put("photoNumReviewed", String.valueOf(photoNumUnreviewed*30));


            int degreeCertNumUnPass = new Random().nextInt(6)+ 10;//10-20
            int videoNumUnPass = new Random().nextInt(6)+ 10;
            int audioNumUnPass = new Random().nextInt(6)+ 10;
            int photoNumUnPass = new Random().nextInt(6)+ 10;

            //不通过//10-15
            map.put("degreeCertNumUnPass", String.valueOf(degreeCertNumUnPass*30));
            map.put("videoNumUnPass", String.valueOf(videoNumUnPass*30));
            map.put("audioNumUnPass", String.valueOf(audioNumUnPass*30));
            map.put("photoNumUnPass", String.valueOf(photoNumUnPass*30));//10-20



            int degreeCertCanceled = degreeCertNumTotal-degreeCertNumUnreviewed- degreeCertNumReviewed- degreeCertNumUnPass;
            int videoNumUnCanceled = videoNumTotal-videoNumUnreviewed-videoNumReviewed-videoNumUnPass;
            int audioNumUnCanceled = audioNumTotal-audioNumUnreviewed-audioNumReviewed-audioNumUnPass;
            int photoNumUnCanceled = photoNumTotal-photoNumUnreviewed-photoNumReviewed-photoNumUnPass;
            //注销
            map.put("degreeCertNumCanceled", String.valueOf(degreeCertCanceled));
            map.put("videoNumCanceled", String.valueOf(videoNumUnCanceled));
            map.put("audioNumCanceled", String.valueOf(audioNumUnCanceled));
            map.put("photoNumCanceled", String.valueOf(photoNumUnCanceled));

            String month = "";
            if(i<10){
                month = "0"+i;
            }else{
                month = String.valueOf(i);
            }


            map.put("time", "2018-"+month+"-"+"01"+" 00:00:00");
            mongoTemplate.save(map,"statisticsPerMonth");
        }

    }







}