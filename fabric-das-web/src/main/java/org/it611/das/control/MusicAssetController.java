package org.it611.das.control;

import com.alibaba.fastjson.JSONObject;
import org.it611.das.service.MusicAssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class MusicAssetController {

    @Autowired
    private MusicAssetService musicAssetService;


    //Music资产首页Index页面
    @RequestMapping("/asset/music/index")
    public ModelAndView degreeCertificationAssetIndex(){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index_musicAssetList");
        return modelAndView;
    }

    //获取Music资产列表
    @RequestMapping("/asset/music/list")
    @ResponseBody
    public JSONObject musicAssetList(int currentPage, int numberOfPages, String title,String state){
        return musicAssetService.musicAssetList(currentPage, numberOfPages, title,state);
    }

    //获取Music详情(页面)
    @RequestMapping("/asset/music/detail/index")
    @ResponseBody
    public ModelAndView degreeCertificationCompareDetail(String id) throws Exception {
        JSONObject result = musicAssetService.musicAssetDetail(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("result", result);
        modelAndView.setViewName("compare_musicAssetDetail");
        return modelAndView;
    }

    //审核和更改状态
    @RequestMapping("/asset/music/checkAndChangeState")
    @ResponseBody
    public JSONObject checkAndChangeState(String id, String state) throws Exception {
        JSONObject resultData = musicAssetService.CheckMusicAssetAndChangeState(id, state);
        return resultData;
    }
    //音频链接地址播放
    @RequestMapping("/musicPalyLink")
    public ModelAndView videoPalyLink(String linkAddress){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("videoUrl", linkAddress);
        modelAndView.setViewName("play_videoAndAudio");
        return modelAndView;
    }

}
