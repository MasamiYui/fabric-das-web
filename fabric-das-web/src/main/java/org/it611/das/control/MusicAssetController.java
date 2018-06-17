package org.it611.das.control;

import com.alibaba.fastjson.JSONObject;
import org.it611.das.service.MusicAssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class MusicAssetController {

    @Autowired
    private MusicAssetService musicAssetService;


    //Music资产首页Index页面
    @RequestMapping(value = "/asset/music/index", method = RequestMethod.GET)
    public ModelAndView musicAssetIndex(){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index_audioAssetList");
        return modelAndView;
    }

    //获取Music资产列表
    @RequestMapping(value = "/asset/musics")  /*, method = RequestMethod.GET*/
    @ResponseBody
    public JSONObject musicAssetList(int currentPage, int numberOfPages, String title,String state){

        return musicAssetService.musicAssetList(currentPage, numberOfPages, title,state);
    }

    //获取Music详情(页面)
    @RequestMapping(value = "/asset/music/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView musicDetail(@PathVariable String id) throws Exception {

        JSONObject result = musicAssetService.musicAssetDetail(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("result", result);
        modelAndView.setViewName("compare_musicAssetDetail");
        return modelAndView;
    }

    //审核和更改状态
    @RequestMapping(value = "/asset/music", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject checkAndChangeState(String id, String state) throws Exception {

        JSONObject resultData = musicAssetService.CheckMusicAssetAndChangeState(id, state);
        return resultData;
    }

    //音频链接地址播放
    @RequestMapping("/musicPalyLink")
    public ModelAndView musicPalyLink(String linkAddress){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("videoUrl", linkAddress);
        modelAndView.setViewName("play_videoAndAudio");
        return modelAndView;
    }

}
