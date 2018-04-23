package org.it611;

import org.apache.log4j.Logger;
import org.it611.das.fastdfs.FastDFSClient;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FastDFSTest {
    private static Logger logger=Logger.getLogger(CouchDBTest.class);


    /**
     * Test Upload
     */
    @Test
    public void TestUploadFile(){
        String result = FastDFSClient.uploadFile(new File("/root/桌面/azm.jpeg"),"azm.jpeg");
        System.out.println(result);
    }

    /**
     * Test download
     */
    @Test
    public void TestDownload() {
        InputStream is = FastDFSClient.downFile("group1","M00/00/00/wKgKgFrd3yCAPKKPAANlvPVzKlM49.jpeg");
        inputstreamtofile(is,new File("/root/桌面/azm2.jpeg"));



    }


    public static void inputstreamtofile(InputStream ins,File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
