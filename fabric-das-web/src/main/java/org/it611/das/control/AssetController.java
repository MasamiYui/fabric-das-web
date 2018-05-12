package org.it611.das.control;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.it611.das.fastdfs.FastDFSClient;
import org.it611.das.service.AssetService;
import org.it611.das.util.MD5Util;
import org.it611.das.util.ResponseUtil;
import org.it611.das.vo.StudentIdCardAssetVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
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
        String fileHash = MD5Util.md5HashCode(file.getInputStream());
        //System.out.println("fileHash:"+fileHash);
        String path=FastDFSClient.saveFile(file);//将文件上传到fastDFS，返回http url
        Map<String,String> dataMap = new HashMap<String,String>();
        dataMap.put("path", path);
        dataMap.put("filesHash", fileHash);
        return ResponseUtil.constructResponse(200,"ok",dataMap);
    }


    /**
     * 资产插入（学生证StudentIdCardAsset sica）
     */
    @RequestMapping("/asset/insert/sica")
    @ResponseBody
    public Map<String, Object> insert(StudentIdCardAssetVO sica) throws Exception {

        Map<String, Object> resultMap = assetService.InsertStudentIdCardAsset(sica);
        if((int)resultMap.get("code") == 200) {//if success
            Map dataMap = new HashMap<String, Object>();
            dataMap.put("txId", resultMap.get("txId"));
            dataMap.put("assetId", resultMap.get("assetId"));
            return ResponseUtil.constructResponse(200, "ok", dataMap);
        }
        return ResponseUtil.constructResponse(400, "insert student id asset failed.", null);
    }

    /**
     * 资产对照查询
     */
    @RequestMapping("/asset/sica/detail")
    @ResponseBody
    public JSONObject detail(String id) throws Exception {

        HashMap dataMap = assetService.selectStudentIdCardAssetById(id);
        if ((int)dataMap.get("code") == 200){
            return ResponseUtil.constructResponse(200, "ok", dataMap);
        }

        return ResponseUtil.constructResponse(400, "query failed.", dataMap);
    }

    //获取资产列表
    @RequestMapping("/asset/getAsset")
    public String getCompanyList(){
        return "assetBase_list";
    }

    //资产列表
    @RequestMapping("/asset/assetList")
    @ResponseBody
    public JSONObject selectCompanies(int currentPage,int numberOfPages, String title,String id,String txid){

        HashMap<String, Object> data = assetService.selectAssetList(currentPage,numberOfPages, title, id,txid);
        return ResponseUtil.constructResponse(200,"ok", data);
    }
}
