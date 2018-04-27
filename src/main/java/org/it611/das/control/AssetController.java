package org.it611.das.control;

import org.it611.das.Service.AssetService;
import org.it611.das.util.State;
import org.it611.das.vo.StudentIdCardAssetVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AssetController {

    @Autowired
    private AssetService assetService;

    /**
     * 资产插入（学生证StudentIdCardAsset sica）
     */
    @RequestMapping("/asset/insert/sica")
    @ResponseBody//记得删除
    public String insert(StudentIdCardAssetVO sica) {

        if(assetService.InsertStudentIdCardAsset(sica) == State.SUCCESS) {
            return "success";
        }
        return "false";
    }

}
