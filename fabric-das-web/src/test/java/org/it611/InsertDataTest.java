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
                int degreeCertNumTotal = (new Random().nextInt(21)+ 100);
                int videoNumTotal = (new Random().nextInt(21)+ 100);
                int audioNumTotal = (new Random().nextInt(21)+ 100);
                int photoNumTotal = (new Random().nextInt(21)+ 100);
                //新增
                int syxxzlNumTotal = (new Random().nextInt(21)+ 100);
                int drivingLicenceNumTotal = (new Random().nextInt(21)+ 100);

                System.out.println(photoNumTotal);


                map.put("degreeCertNumTotal", degreeCertNumTotal);
                map.put("videoNumTotal", videoNumTotal);
                map.put("audioNumTotal", audioNumTotal);
                map.put("photoNumTotal", photoNumTotal);//100-150
                //新增
                map.put("syxxzlNumTotal", syxxzlNumTotal);
                map.put("drivingLicenceNumTotal", drivingLicenceNumTotal);



                int degreeCertNumUnreviewed = (new Random().nextInt(11)+ 5);
                int videoNumUnreviewed = (new Random().nextInt(11)+ 5);
                int audioNumUnreviewed = (new Random().nextInt(11)+ 5);
                int photoNumUnreviewed = (new Random().nextInt(11)+ 5);
                //新增
                int syxxzlNumUnreviewed = (new Random().nextInt(11)+ 5);
                int drivingLicenceNumUnreviewed = (new Random().nextInt(11)+ 5);



                //未审核5-15
                map.put("degreeCertNumUnReviewed", degreeCertNumUnreviewed);
                map.put("videoNumUnReviewed", videoNumUnreviewed);
                map.put("audioNumUnReviewed", audioNumUnreviewed);
                map.put("photoNumUnReviewed", photoNumUnreviewed);
                //新增
                map.put("syxxzlNumUnreviewed",syxxzlNumUnreviewed);
                map.put("drivingLicenceNumUnreviewed", drivingLicenceNumUnreviewed);



                int degreeCertNumReviewed = (new Random().nextInt(21)+ 50);
                int videoNumReviewed = (new Random().nextInt(21)+ 50);
                int audioNumReviewed = (new Random().nextInt(21)+ 50);
                int photoNumReviewed = (new Random().nextInt(21)+ 50);
                //新增
                int syxxzlNumReviewed = (new Random().nextInt(21)+ 50);
                int drivingLicenceNumReviewed = (new Random().nextInt(21)+ 50);

                //已审核50-70
                map.put("degreeCertNumReviewed", degreeCertNumReviewed);
                map.put("videoNumReviewed", videoNumReviewed);
                map.put("audioNumReviewed", audioNumReviewed);
                map.put("photoNumReviewed", photoNumReviewed);
                //新增
                map.put("syxxzlNumReviewed", syxxzlNumReviewed);
                map.put("drivingLicenceNumReviewed", drivingLicenceNumReviewed);




                int degreeCertNumUnPass = (new Random().nextInt(6)+ 10);//10-20
                int videoNumUnPass = (new Random().nextInt(6)+ 10);
                int audioNumUnPass = (new Random().nextInt(6)+ 10);
                int photoNumUnPass = (new Random().nextInt(6)+ 10);
                //新增
                int syxxzlNumUnPass = (new Random().nextInt(6)+ 10);
                int drivingLicenceNumUnPass = (new Random().nextInt(6)+ 10);


                //不通过//10-15
                map.put("degreeCertNumUnPass", degreeCertNumUnPass);
                map.put("videoNumUnPass", videoNumUnPass);
                map.put("audioNumUnPass", audioNumUnPass);
                map.put("photoNumUnPass", photoNumUnPass);//10-20
                map.put("syxxzlNumUnPass", syxxzlNumUnPass);
                map.put("drivingLicenceNumUnPass", drivingLicenceNumUnPass);




                int degreeCertCanceled = degreeCertNumTotal-degreeCertNumUnreviewed- degreeCertNumReviewed- degreeCertNumUnPass;
                int videoNumUnCanceled = videoNumTotal-videoNumUnreviewed-videoNumReviewed-videoNumUnPass;
                int audioNumUnCanceled = audioNumTotal-audioNumUnreviewed-audioNumReviewed-audioNumUnPass;
                int photoNumUnCanceled = photoNumTotal-photoNumUnreviewed-photoNumReviewed-photoNumUnPass;
                //新增
                int syxxzlNumUnCanceled = syxxzlNumTotal-syxxzlNumUnreviewed-syxxzlNumReviewed-syxxzlNumUnPass;
                int drivingLicencedNumCanceled = drivingLicenceNumTotal-drivingLicenceNumUnreviewed-drivingLicenceNumReviewed-drivingLicenceNumUnPass;

                //注销
                map.put("degreeCertNumCanceled", degreeCertCanceled);
                map.put("videoNumCanceled", videoNumUnCanceled);
                map.put("audioNumCanceled", audioNumUnCanceled);
                map.put("photoNumCanceled", photoNumUnCanceled);
                map.put("syxxzlNumUnCanceled",syxxzlNumUnCanceled);
                map.put("drivingLicencedNumCanceled",drivingLicencedNumCanceled);


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
              /*  HashMap<String, Object> map = new HashMap();

                //申请总量 100-120
                int degreeCertNumTotal = (new Random().nextInt(21)+ 100);
                int videoNumTotal = (new Random().nextInt(21)+ 100);
                int audioNumTotal = (new Random().nextInt(21)+ 100);
                int photoNumTotal = (new Random().nextInt(21)+ 100);
                System.out.println(photoNumTotal);


                map.put("degreeCertNumTotal", degreeCertNumTotal*30);
                map.put("videoNumTotal", videoNumTotal*30);
                map.put("audioNumTotal", audioNumTotal*30);
                map.put("photoNumTotal", photoNumTotal*30);//100-150




                int degreeCertNumUnreviewed = (new Random().nextInt(11)+ 5);
                int videoNumUnreviewed = (new Random().nextInt(11)+ 5);
                int audioNumUnreviewed = (new Random().nextInt(11)+ 5);
                int photoNumUnreviewed = (new Random().nextInt(11)+ 5);


                //未审核5-15
                map.put("degreeCertNumUnReviewed", degreeCertNumUnreviewed);
                map.put("videoNumUnReviewed", videoNumUnreviewed);
                map.put("audioNumUnReviewed", audioNumUnreviewed);
                map.put("photoNumUnReviewed", photoNumUnreviewed);


                int degreeCertNumReviewed = (new Random().nextInt(21)+ 50);
                int videoNumReviewed = (new Random().nextInt(21)+ 50);
                int audioNumReviewed = (new Random().nextInt(21)+ 50);
                int photoNumReviewed = (new Random().nextInt(21)+ 50);

                //已审核50-70
                map.put("degreeCertNumReviewed", degreeCertNumReviewed);
                map.put("videoNumReviewed", videoNumReviewed);
                map.put("audioNumReviewed", audioNumReviewed);
                map.put("photoNumReviewed", photoNumReviewed);


                int degreeCertNumUnPass = (new Random().nextInt(6)+ 10);//10-20
                int videoNumUnPass = (new Random().nextInt(6)+ 10);
                int audioNumUnPass = (new Random().nextInt(6)+ 10);
                int photoNumUnPass = (new Random().nextInt(6)+ 10);

                //不通过//10-15
                map.put("degreeCertNumUnPass", degreeCertNumUnPass);
                map.put("videoNumUnPass", videoNumUnPass);
                map.put("audioNumUnPass", audioNumUnPass);
                map.put("photoNumUnPass", photoNumUnPass);//10-20



                int degreeCertCanceled = degreeCertNumTotal-degreeCertNumUnreviewed- degreeCertNumReviewed- degreeCertNumUnPass;
                int videoNumUnCanceled = videoNumTotal-videoNumUnreviewed-videoNumReviewed-videoNumUnPass;
                int audioNumUnCanceled = audioNumTotal-audioNumUnreviewed-audioNumReviewed-audioNumUnPass;
                int photoNumUnCanceled = photoNumTotal-photoNumUnreviewed-photoNumReviewed-photoNumUnPass;
                //注销
                map.put("degreeCertNumCanceled", degreeCertCanceled);
                map.put("videoNumCanceled", videoNumUnCanceled);
                map.put("audioNumCanceled", audioNumUnCanceled);
                map.put("photoNumCanceled", photoNumUnCanceled);*/


                HashMap<String, Object> map = new HashMap();

                //申请总量 100-120
                int degreeCertNumTotal = (new Random().nextInt(21)+ 100);
                int videoNumTotal = (new Random().nextInt(21)+ 100);
                int audioNumTotal = (new Random().nextInt(21)+ 100);
                int photoNumTotal = (new Random().nextInt(21)+ 100);
                //新增
                int syxxzlNumTotal = (new Random().nextInt(21)+ 100);
                int drivingLicenceNumTotal = (new Random().nextInt(21)+ 100);

                System.out.println(photoNumTotal);


                map.put("degreeCertNumTotal", degreeCertNumTotal);
                map.put("videoNumTotal", videoNumTotal);
                map.put("audioNumTotal", audioNumTotal);
                map.put("photoNumTotal", photoNumTotal);//100-150
                //新增
                map.put("syxxzlNumTotal", syxxzlNumTotal);
                map.put("drivingLicenceNumTotal", drivingLicenceNumTotal);



                int degreeCertNumUnreviewed = (new Random().nextInt(11)+ 5);
                int videoNumUnreviewed = (new Random().nextInt(11)+ 5);
                int audioNumUnreviewed = (new Random().nextInt(11)+ 5);
                int photoNumUnreviewed = (new Random().nextInt(11)+ 5);
                //新增
                int syxxzlNumUnreviewed = (new Random().nextInt(11)+ 5);
                int drivingLicenceNumUnreviewed = (new Random().nextInt(11)+ 5);



                //未审核5-15
                map.put("degreeCertNumUnReviewed", degreeCertNumUnreviewed);
                map.put("videoNumUnReviewed", videoNumUnreviewed);
                map.put("audioNumUnReviewed", audioNumUnreviewed);
                map.put("photoNumUnReviewed", photoNumUnreviewed);
                //新增
                map.put("syxxzlNumUnreviewed",syxxzlNumUnreviewed);
                map.put("drivingLicenceNumUnreviewed", drivingLicenceNumUnreviewed);



                int degreeCertNumReviewed = (new Random().nextInt(21)+ 50);
                int videoNumReviewed = (new Random().nextInt(21)+ 50);
                int audioNumReviewed = (new Random().nextInt(21)+ 50);
                int photoNumReviewed = (new Random().nextInt(21)+ 50);
                //新增
                int syxxzlNumReviewed = (new Random().nextInt(21)+ 50);
                int drivingLicenceNumReviewed = (new Random().nextInt(21)+ 50);

                //已审核50-70
                map.put("degreeCertNumReviewed", degreeCertNumReviewed);
                map.put("videoNumReviewed", videoNumReviewed);
                map.put("audioNumReviewed", audioNumReviewed);
                map.put("photoNumReviewed", photoNumReviewed);
                //新增
                map.put("syxxzlNumReviewed", syxxzlNumReviewed);
                map.put("drivingLicenceNumReviewed", drivingLicenceNumReviewed);




                int degreeCertNumUnPass = (new Random().nextInt(6)+ 10);//10-20
                int videoNumUnPass = (new Random().nextInt(6)+ 10);
                int audioNumUnPass = (new Random().nextInt(6)+ 10);
                int photoNumUnPass = (new Random().nextInt(6)+ 10);
                //新增
                int syxxzlNumUnPass = (new Random().nextInt(6)+ 10);
                int drivingLicenceNumUnPass = (new Random().nextInt(6)+ 10);


                //不通过//10-15
                map.put("degreeCertNumUnPass", degreeCertNumUnPass);
                map.put("videoNumUnPass", videoNumUnPass);
                map.put("audioNumUnPass", audioNumUnPass);
                map.put("photoNumUnPass", photoNumUnPass);//10-20
                map.put("syxxzlNumUnPass", syxxzlNumUnPass);
                map.put("drivingLicenceNumUnPass", drivingLicenceNumUnPass);




                int degreeCertCanceled = degreeCertNumTotal-degreeCertNumUnreviewed- degreeCertNumReviewed- degreeCertNumUnPass;
                int videoNumUnCanceled = videoNumTotal-videoNumUnreviewed-videoNumReviewed-videoNumUnPass;
                int audioNumUnCanceled = audioNumTotal-audioNumUnreviewed-audioNumReviewed-audioNumUnPass;
                int photoNumUnCanceled = photoNumTotal-photoNumUnreviewed-photoNumReviewed-photoNumUnPass;
                //新增
                int syxxzlNumUnCanceled = syxxzlNumTotal-syxxzlNumUnreviewed-syxxzlNumReviewed-syxxzlNumUnPass;
                int drivingLicencedNumCanceled = drivingLicenceNumTotal-drivingLicenceNumUnreviewed-drivingLicenceNumReviewed-drivingLicenceNumUnPass;

                //注销
                map.put("degreeCertNumCanceled", degreeCertCanceled);
                map.put("videoNumCanceled", videoNumUnCanceled);
                map.put("audioNumCanceled", audioNumUnCanceled);
                map.put("photoNumCanceled", photoNumUnCanceled);
                map.put("syxxzlNumUnCanceled",syxxzlNumUnCanceled);
                map.put("drivingLicencedNumCanceled",drivingLicencedNumCanceled);


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




    @Test
    public void insertRabbishData6yueri() {


            for(int i =1; i<=17; i++) {
               /* HashMap<String, Object> map = new HashMap();

                //申请总量 100-120
                int degreeCertNumTotal = (new Random().nextInt(21)+ 100);
                int videoNumTotal = (new Random().nextInt(21)+ 100);
                int audioNumTotal = (new Random().nextInt(21)+ 100);
                int photoNumTotal = (new Random().nextInt(21)+ 100);
                System.out.println(photoNumTotal);


                map.put("degreeCertNumTotal", degreeCertNumTotal);
                map.put("videoNumTotal", videoNumTotal);
                map.put("audioNumTotal", audioNumTotal);
                map.put("photoNumTotal", photoNumTotal);//100-150




                int degreeCertNumUnreviewed = (new Random().nextInt(11)+ 5);
                int videoNumUnreviewed = (new Random().nextInt(11)+ 5);
                int audioNumUnreviewed = (new Random().nextInt(11)+ 5);
                int photoNumUnreviewed = (new Random().nextInt(11)+ 5);


                //未审核5-15
                map.put("degreeCertNumUnReviewed", degreeCertNumUnreviewed);
                map.put("videoNumUnReviewed", videoNumUnreviewed);
                map.put("audioNumUnReviewed", audioNumUnreviewed);
                map.put("photoNumUnReviewed", photoNumUnreviewed);


                int degreeCertNumReviewed = (new Random().nextInt(21)+ 50);
                int videoNumReviewed = (new Random().nextInt(21)+ 50);
                int audioNumReviewed = (new Random().nextInt(21)+ 50);
                int photoNumReviewed = (new Random().nextInt(21)+ 50);

                //已审核50-70
                map.put("degreeCertNumReviewed", degreeCertNumReviewed);
                map.put("videoNumReviewed", videoNumReviewed);
                map.put("audioNumReviewed", audioNumReviewed);
                map.put("photoNumReviewed", photoNumReviewed);


                int degreeCertNumUnPass = (new Random().nextInt(6)+ 10);//10-20
                int videoNumUnPass = (new Random().nextInt(6)+ 10);
                int audioNumUnPass = (new Random().nextInt(6)+ 10);
                int photoNumUnPass = (new Random().nextInt(6)+ 10);

                //不通过//10-15
                map.put("degreeCertNumUnPass", degreeCertNumUnPass);
                map.put("videoNumUnPass", videoNumUnPass);
                map.put("audioNumUnPass", audioNumUnPass);
                map.put("photoNumUnPass", photoNumUnPass);//10-20



                int degreeCertCanceled = degreeCertNumTotal-degreeCertNumUnreviewed- degreeCertNumReviewed- degreeCertNumUnPass;
                int videoNumUnCanceled = videoNumTotal-videoNumUnreviewed-videoNumReviewed-videoNumUnPass;
                int audioNumUnCanceled = audioNumTotal-audioNumUnreviewed-audioNumReviewed-audioNumUnPass;
                int photoNumUnCanceled = photoNumTotal-photoNumUnreviewed-photoNumReviewed-photoNumUnPass;
                //注销
                map.put("degreeCertNumCanceled", degreeCertCanceled);
                map.put("videoNumCanceled", videoNumUnCanceled);
                map.put("audioNumCanceled", audioNumUnCanceled);
                map.put("photoNumCanceled", photoNumUnCanceled);*/

                HashMap<String, Object> map = new HashMap();

                //申请总量 100-120
                int degreeCertNumTotal = (new Random().nextInt(21)+ 100);
                int videoNumTotal = (new Random().nextInt(21)+ 100);
                int audioNumTotal = (new Random().nextInt(21)+ 100);
                int photoNumTotal = (new Random().nextInt(21)+ 100);
                //新增
                int syxxzlNumTotal = (new Random().nextInt(21)+ 100);
                int drivingLicenceNumTotal = (new Random().nextInt(21)+ 100);

                System.out.println(photoNumTotal);


                map.put("degreeCertNumTotal", degreeCertNumTotal);
                map.put("videoNumTotal", videoNumTotal);
                map.put("audioNumTotal", audioNumTotal);
                map.put("photoNumTotal", photoNumTotal);//100-150
                //新增
                map.put("syxxzlNumTotal", syxxzlNumTotal);
                map.put("drivingLicenceNumTotal", drivingLicenceNumTotal);



                int degreeCertNumUnreviewed = (new Random().nextInt(11)+ 5);
                int videoNumUnreviewed = (new Random().nextInt(11)+ 5);
                int audioNumUnreviewed = (new Random().nextInt(11)+ 5);
                int photoNumUnreviewed = (new Random().nextInt(11)+ 5);
                //新增
                int syxxzlNumUnreviewed = (new Random().nextInt(11)+ 5);
                int drivingLicenceNumUnreviewed = (new Random().nextInt(11)+ 5);



                //未审核5-15
                map.put("degreeCertNumUnReviewed", degreeCertNumUnreviewed);
                map.put("videoNumUnReviewed", videoNumUnreviewed);
                map.put("audioNumUnReviewed", audioNumUnreviewed);
                map.put("photoNumUnReviewed", photoNumUnreviewed);
                //新增
                map.put("syxxzlNumUnreviewed",syxxzlNumUnreviewed);
                map.put("drivingLicenceNumUnreviewed", drivingLicenceNumUnreviewed);



                int degreeCertNumReviewed = (new Random().nextInt(21)+ 50);
                int videoNumReviewed = (new Random().nextInt(21)+ 50);
                int audioNumReviewed = (new Random().nextInt(21)+ 50);
                int photoNumReviewed = (new Random().nextInt(21)+ 50);
                //新增
                int syxxzlNumReviewed = (new Random().nextInt(21)+ 50);
                int drivingLicenceNumReviewed = (new Random().nextInt(21)+ 50);

                //已审核50-70
                map.put("degreeCertNumReviewed", degreeCertNumReviewed);
                map.put("videoNumReviewed", videoNumReviewed);
                map.put("audioNumReviewed", audioNumReviewed);
                map.put("photoNumReviewed", photoNumReviewed);
                //新增
                map.put("syxxzlNumReviewed", syxxzlNumReviewed);
                map.put("drivingLicenceNumReviewed", drivingLicenceNumReviewed);




                int degreeCertNumUnPass = (new Random().nextInt(6)+ 10);//10-20
                int videoNumUnPass = (new Random().nextInt(6)+ 10);
                int audioNumUnPass = (new Random().nextInt(6)+ 10);
                int photoNumUnPass = (new Random().nextInt(6)+ 10);
                //新增
                int syxxzlNumUnPass = (new Random().nextInt(6)+ 10);
                int drivingLicenceNumUnPass = (new Random().nextInt(6)+ 10);


                //不通过//10-15
                map.put("degreeCertNumUnPass", degreeCertNumUnPass);
                map.put("videoNumUnPass", videoNumUnPass);
                map.put("audioNumUnPass", audioNumUnPass);
                map.put("photoNumUnPass", photoNumUnPass);//10-20
                map.put("syxxzlNumUnPass", syxxzlNumUnPass);
                map.put("drivingLicenceNumUnPass", drivingLicenceNumUnPass);




                int degreeCertCanceled = degreeCertNumTotal-degreeCertNumUnreviewed- degreeCertNumReviewed- degreeCertNumUnPass;
                int videoNumUnCanceled = videoNumTotal-videoNumUnreviewed-videoNumReviewed-videoNumUnPass;
                int audioNumUnCanceled = audioNumTotal-audioNumUnreviewed-audioNumReviewed-audioNumUnPass;
                int photoNumUnCanceled = photoNumTotal-photoNumUnreviewed-photoNumReviewed-photoNumUnPass;
                //新增
                int syxxzlNumUnCanceled = syxxzlNumTotal-syxxzlNumUnreviewed-syxxzlNumReviewed-syxxzlNumUnPass;
                int drivingLicencedNumCanceled = drivingLicenceNumTotal-drivingLicenceNumUnreviewed-drivingLicenceNumReviewed-drivingLicenceNumUnPass;

                //注销
                map.put("degreeCertNumCanceled", degreeCertCanceled);
                map.put("videoNumCanceled", videoNumUnCanceled);
                map.put("audioNumCanceled", audioNumUnCanceled);
                map.put("photoNumCanceled", photoNumUnCanceled);
                map.put("syxxzlNumUnCanceled",syxxzlNumUnCanceled);
                map.put("drivingLicencedNumCanceled",drivingLicencedNumCanceled);

                String day = "";
                if(i<10){
                    day = "0"+String.valueOf(i);
                }else{
                    day = String.valueOf(i);
                }
                map.put("time", "2018-"+"06-"+day+" 00:00:00");
                mongoTemplate.save(map,"statisticsPerDay");
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
            int degreeCertNumTotal = (new Random().nextInt(21)+ 100)*30;
            int videoNumTotal = (new Random().nextInt(21)+ 100)*30;
            int audioNumTotal = (new Random().nextInt(21)+ 100)*30;
            int photoNumTotal = (new Random().nextInt(21)+ 100)*30;


            int syxxzlNumTotal = (new Random().nextInt(21)+ 100)*30;
            int drivingLicenceNumTotal = (new Random().nextInt(21)+ 100)*30;
            System.out.println(photoNumTotal);


            map.put("degreeCertNumTotal", degreeCertNumTotal);
            map.put("videoNumTotal", videoNumTotal);
            map.put("audioNumTotal", audioNumTotal);
            map.put("photoNumTotal", photoNumTotal);//100-150
            //新增
            map.put("syxxzlNumTotal", syxxzlNumTotal);
            map.put("drivingLicenceNumTotal", drivingLicenceNumTotal);



            int degreeCertNumUnreviewed = (new Random().nextInt(11)+ 5)*30;
            int videoNumUnreviewed = (new Random().nextInt(11)+ 5)*30;
            int audioNumUnreviewed = (new Random().nextInt(11)+ 5)*30;
            int photoNumUnreviewed = (new Random().nextInt(11)+ 5)*30;
            //新增
            int syxxzlNumUnreviewed = (new Random().nextInt(11)+ 5)*30;
            int drivingLicenceNumUnreviewed = (new Random().nextInt(11)+ 5)*30;


            //未审核5-15
            map.put("degreeCertNumUnReviewed", degreeCertNumUnreviewed);
            map.put("videoNumUnReviewed", videoNumUnreviewed);
            map.put("audioNumUnReviewed", audioNumUnreviewed);
            map.put("photoNumUnReviewed", photoNumUnreviewed);
            //新增
            map.put("syxxzlNumUnreviewed",syxxzlNumUnreviewed);
            map.put("drivingLicenceNumUnreviewed", drivingLicenceNumUnreviewed);

            int degreeCertNumReviewed = (new Random().nextInt(21)+ 50)*30;
            int videoNumReviewed = (new Random().nextInt(21)+ 50)*30;
            int audioNumReviewed = (new Random().nextInt(21)+ 50)*30;
            int photoNumReviewed = (new Random().nextInt(21)+ 50)*30;
            //新增
            int syxxzlNumReviewed = (new Random().nextInt(21)+ 50)*30;
            int drivingLicenceNumReviewed = (new Random().nextInt(21)+ 50)*30;


            //已审核50-70
            map.put("degreeCertNumReviewed", degreeCertNumReviewed);
            map.put("videoNumReviewed", videoNumReviewed);
            map.put("audioNumReviewed", audioNumReviewed);
            map.put("photoNumReviewed", photoNumReviewed);
            //新增
            map.put("syxxzlNumReviewed", syxxzlNumReviewed);
            map.put("drivingLicenceNumReviewed", drivingLicenceNumReviewed);



            int degreeCertNumUnPass = (new Random().nextInt(6)+ 10)*30;//10-20
            int videoNumUnPass = (new Random().nextInt(6)+ 10)*30;
            int audioNumUnPass = (new Random().nextInt(6)+ 10)*30;
            int photoNumUnPass = (new Random().nextInt(6)+ 10)*30;
            //新增
            int syxxzlNumUnPass = (new Random().nextInt(6)+ 10)*30;
            int drivingLicenceNumUnPass = (new Random().nextInt(6)+ 10)*30;



            //不通过//10-15
            map.put("degreeCertNumUnPass", degreeCertNumUnPass);
            map.put("videoNumUnPass", videoNumUnPass);
            map.put("audioNumUnPass", audioNumUnPass);
            map.put("photoNumUnPass", photoNumUnPass);//10-20
            map.put("syxxzlNumUnPass", syxxzlNumUnPass);
            map.put("drivingLicenceNumUnPass", drivingLicenceNumUnPass);



            int degreeCertCanceled = degreeCertNumTotal-degreeCertNumUnreviewed- degreeCertNumReviewed- degreeCertNumUnPass;
            int videoNumUnCanceled = videoNumTotal-videoNumUnreviewed-videoNumReviewed-videoNumUnPass;
            int audioNumUnCanceled = audioNumTotal-audioNumUnreviewed-audioNumReviewed-audioNumUnPass;
            int photoNumUnCanceled = photoNumTotal-photoNumUnreviewed-photoNumReviewed-photoNumUnPass;
            //新增
            int syxxzlNumUnCanceled = syxxzlNumTotal-syxxzlNumUnreviewed-syxxzlNumReviewed-syxxzlNumUnPass;
            int drivingLicencedNumCanceled = drivingLicenceNumTotal-drivingLicenceNumUnreviewed-drivingLicenceNumReviewed-drivingLicenceNumUnPass;


            //注销
            map.put("degreeCertNumCanceled", degreeCertCanceled);
            map.put("videoNumCanceled", videoNumUnCanceled);
            map.put("audioNumCanceled", audioNumUnCanceled);
            map.put("photoNumCanceled", photoNumUnCanceled);
            map.put("syxxzlNumUnCanceled",syxxzlNumUnCanceled);
            map.put("drivingLicencedNumCanceled",drivingLicencedNumCanceled);

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

        for(int i =1; i<=6; i++) {
           /* HashMap<String, Object> map = new HashMap();

            //申请总量 100-120
            int degreeCertNumTotal = (new Random().nextInt(21)+ 100)*30;
            int videoNumTotal = (new Random().nextInt(21)+ 100)*30;
            int audioNumTotal = (new Random().nextInt(21)+ 100)*30;
            int photoNumTotal = (new Random().nextInt(21)+ 100)*30;
            System.out.println(photoNumTotal);


            map.put("degreeCertNumTotal", degreeCertNumTotal);
            map.put("videoNumTotal", videoNumTotal);
            map.put("audioNumTotal", audioNumTotal);
            map.put("photoNumTotal", photoNumTotal);//100-150




            int degreeCertNumUnreviewed = (new Random().nextInt(11)+ 5)*30;
            int videoNumUnreviewed = (new Random().nextInt(11)+ 5)*30;
            int audioNumUnreviewed = (new Random().nextInt(11)+ 5)*30;
            int photoNumUnreviewed = (new Random().nextInt(11)+ 5)*30;


            //未审核5-15
            map.put("degreeCertNumUnReviewed", degreeCertNumUnreviewed);
            map.put("videoNumUnReviewed", videoNumUnreviewed);
            map.put("audioNumUnReviewed", audioNumUnreviewed);
            map.put("photoNumUnReviewed", photoNumUnreviewed);


            int degreeCertNumReviewed = (new Random().nextInt(21)+ 50)*30;
            int videoNumReviewed = (new Random().nextInt(21)+ 50)*30;
            int audioNumReviewed = (new Random().nextInt(21)+ 50)*30;
            int photoNumReviewed = (new Random().nextInt(21)+ 50)*30;

            //已审核50-70
            map.put("degreeCertNumReviewed", degreeCertNumReviewed);
            map.put("videoNumReviewed", videoNumReviewed);
            map.put("audioNumReviewed", audioNumReviewed);
            map.put("photoNumReviewed", photoNumReviewed);


            int degreeCertNumUnPass = (new Random().nextInt(6)+ 10)*30;//10-20
            int videoNumUnPass = (new Random().nextInt(6)+ 10)*30;
            int audioNumUnPass = (new Random().nextInt(6)+ 10)*30;
            int photoNumUnPass = (new Random().nextInt(6)+ 10)*30;

            //不通过//10-15
            map.put("degreeCertNumUnPass", degreeCertNumUnPass);
            map.put("videoNumUnPass", videoNumUnPass);
            map.put("audioNumUnPass", audioNumUnPass);
            map.put("photoNumUnPass", photoNumUnPass);//10-20



            int degreeCertCanceled = degreeCertNumTotal-degreeCertNumUnreviewed- degreeCertNumReviewed- degreeCertNumUnPass;
            int videoNumUnCanceled = videoNumTotal-videoNumUnreviewed-videoNumReviewed-videoNumUnPass;
            int audioNumUnCanceled = audioNumTotal-audioNumUnreviewed-audioNumReviewed-audioNumUnPass;
            int photoNumUnCanceled = photoNumTotal-photoNumUnreviewed-photoNumReviewed-photoNumUnPass;
            //注销
            map.put("degreeCertNumCanceled", degreeCertCanceled);
            map.put("videoNumCanceled", videoNumUnCanceled);
            map.put("audioNumCanceled", audioNumUnCanceled);
            map.put("photoNumCanceled", photoNumUnCanceled);*/

            HashMap<String, Object> map = new HashMap();

            //申请总量 100-120
            int degreeCertNumTotal = (new Random().nextInt(21)+ 100)*30;
            int videoNumTotal = (new Random().nextInt(21)+ 100)*30;
            int audioNumTotal = (new Random().nextInt(21)+ 100)*30;
            int photoNumTotal = (new Random().nextInt(21)+ 100)*30;


            int syxxzlNumTotal = (new Random().nextInt(21)+ 100)*30;
            int drivingLicenceNumTotal = (new Random().nextInt(21)+ 100)*30;
            System.out.println(photoNumTotal);


            map.put("degreeCertNumTotal", degreeCertNumTotal);
            map.put("videoNumTotal", videoNumTotal);
            map.put("audioNumTotal", audioNumTotal);
            map.put("photoNumTotal", photoNumTotal);//100-150
            //新增
            map.put("syxxzlNumTotal", syxxzlNumTotal);
            map.put("drivingLicenceNumTotal", drivingLicenceNumTotal);



            int degreeCertNumUnreviewed = (new Random().nextInt(11)+ 5)*30;
            int videoNumUnreviewed = (new Random().nextInt(11)+ 5)*30;
            int audioNumUnreviewed = (new Random().nextInt(11)+ 5)*30;
            int photoNumUnreviewed = (new Random().nextInt(11)+ 5)*30;
            //新增
            int syxxzlNumUnreviewed = (new Random().nextInt(11)+ 5)*30;
            int drivingLicenceNumUnreviewed = (new Random().nextInt(11)+ 5)*30;


            //未审核5-15
            map.put("degreeCertNumUnReviewed", degreeCertNumUnreviewed);
            map.put("videoNumUnReviewed", videoNumUnreviewed);
            map.put("audioNumUnReviewed", audioNumUnreviewed);
            map.put("photoNumUnReviewed", photoNumUnreviewed);
            //新增
            map.put("syxxzlNumUnreviewed",syxxzlNumUnreviewed);
            map.put("drivingLicenceNumUnreviewed", drivingLicenceNumUnreviewed);

            int degreeCertNumReviewed = (new Random().nextInt(21)+ 50)*30;
            int videoNumReviewed = (new Random().nextInt(21)+ 50)*30;
            int audioNumReviewed = (new Random().nextInt(21)+ 50)*30;
            int photoNumReviewed = (new Random().nextInt(21)+ 50)*30;
            //新增
            int syxxzlNumReviewed = (new Random().nextInt(21)+ 50)*30;
            int drivingLicenceNumReviewed = (new Random().nextInt(21)+ 50)*30;


            //已审核50-70
            map.put("degreeCertNumReviewed", degreeCertNumReviewed);
            map.put("videoNumReviewed", videoNumReviewed);
            map.put("audioNumReviewed", audioNumReviewed);
            map.put("photoNumReviewed", photoNumReviewed);
            //新增
            map.put("syxxzlNumReviewed", syxxzlNumReviewed);
            map.put("drivingLicenceNumReviewed", drivingLicenceNumReviewed);



            int degreeCertNumUnPass = (new Random().nextInt(6)+ 10)*30;//10-20
            int videoNumUnPass = (new Random().nextInt(6)+ 10)*30;
            int audioNumUnPass = (new Random().nextInt(6)+ 10)*30;
            int photoNumUnPass = (new Random().nextInt(6)+ 10)*30;
            //新增
            int syxxzlNumUnPass = (new Random().nextInt(6)+ 10)*30;
            int drivingLicenceNumUnPass = (new Random().nextInt(6)+ 10)*30;



            //不通过//10-15
            map.put("degreeCertNumUnPass", degreeCertNumUnPass);
            map.put("videoNumUnPass", videoNumUnPass);
            map.put("audioNumUnPass", audioNumUnPass);
            map.put("photoNumUnPass", photoNumUnPass);//10-20
            map.put("syxxzlNumUnPass", syxxzlNumUnPass);
            map.put("drivingLicenceNumUnPass", drivingLicenceNumUnPass);



            int degreeCertCanceled = degreeCertNumTotal-degreeCertNumUnreviewed- degreeCertNumReviewed- degreeCertNumUnPass;
            int videoNumUnCanceled = videoNumTotal-videoNumUnreviewed-videoNumReviewed-videoNumUnPass;
            int audioNumUnCanceled = audioNumTotal-audioNumUnreviewed-audioNumReviewed-audioNumUnPass;
            int photoNumUnCanceled = photoNumTotal-photoNumUnreviewed-photoNumReviewed-photoNumUnPass;
            //新增
            int syxxzlNumUnCanceled = syxxzlNumTotal-syxxzlNumUnreviewed-syxxzlNumReviewed-syxxzlNumUnPass;
            int drivingLicencedNumCanceled = drivingLicenceNumTotal-drivingLicenceNumUnreviewed-drivingLicenceNumReviewed-drivingLicenceNumUnPass;


            //注销
            map.put("degreeCertNumCanceled", degreeCertCanceled);
            map.put("videoNumCanceled", videoNumUnCanceled);
            map.put("audioNumCanceled", audioNumUnCanceled);
            map.put("photoNumCanceled", photoNumUnCanceled);
            map.put("syxxzlNumUnCanceled",syxxzlNumUnCanceled);
            map.put("drivingLicencedNumCanceled",drivingLicencedNumCanceled);



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