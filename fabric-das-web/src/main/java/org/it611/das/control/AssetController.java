package org.it611.das.control;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.it611.das.fastdfs.FastDFSClient;
import org.it611.das.fastdfs.FastDFSFile;
import org.it611.das.service.AssetService;
import org.it611.das.util.ResponseUtil;
import org.it611.das.util.State;
import org.it611.das.vo.StudentIdCardAssetVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AssetController {

    private static Logger logger=Logger.getLogger(AssetController.class);

    @Autowired
    private AssetService assetService;

    /**
     * 返回资产插入页面
     */
    @RequestMapping("/asset/insert")
    public ModelAndView insertIndex(){

        ModelAndView view = new ModelAndView();
        view.setViewName("insert_index");
        return view;
    }


    /**
     * 资产原文件上传
     */
    @RequestMapping("/asset/file_upload")
    @ResponseBody
    public JSONObject singleFileUpload(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            logger.info("empty file.");
            return ResponseUtil.constructResponse(400,"empty file.",null);
        }
        String path=FastDFSClient.saveFile(file);//将文件上传到fastDFS，返回http url
        Map<String,String> dataMap = new HashMap<String,String>();
        dataMap.put("path", path);
        return ResponseUtil.constructResponse(200,"ok",dataMap);
    }


    /**
     * 资产插入（学生证StudentIdCardAsset sica）
     */
    @RequestMapping("/asset/insert/sica")
    @ResponseBody//记得删除
    public String insert(StudentIdCardAssetVO sica) throws Exception {

        System.out.println("=======>"+sica.toString());
        if(assetService.InsertStudentIdCardAsset(sica) == State.SUCCESS) {
            return "success";
        }
        return "false";
    }
}
