package org.it611.das.control;

import org.apache.log4j.Logger;
import org.it611.das.couchdb.CouchDB;
import org.it611.das.domain.Msg;
import org.it611.das.fabric.FabricClient;
import org.it611.das.fabric.LedgerRecord;
import org.it611.das.fastdfs.FastDFSClient;
import org.it611.das.fastdfs.FastDFSFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStream;

@Controller
public class HomeController {

	/**
	 * test
	 */
	private static Logger logger=Logger.getLogger(HomeController.class);

	@Autowired
	private CouchDB ccdb;

	@Autowired
	private FabricClient fabricClient;

    @RequestMapping("/login")
    public String login(Model model){
        return "login";
    }

	@RequestMapping("/")
	public String index(Model model){
		Msg msg =  new Msg("测试标题","测试内容","额外信息，只对管理员显示");
		model.addAttribute("msg", msg);
		return "home";
	}

	@RequestMapping("/t")
	@ResponseBody
	public String t(){
		return ccdb.basicKVQuery("aef01013");
	}


	@RequestMapping("/t2")
	@ResponseBody
	public String t2() {
		return null;
	}


	@RequestMapping("/t3")
    @ResponseBody
    public String t3() throws Exception {

		int r =(int)(Math.random()*10000);
        LedgerRecord testinfo=new LedgerRecord("test1key"+r,"test1value"+r);
		logger.info("test1key"+r+",test1value"+r);
        String result = fabricClient.instert(fabricClient.getDefaultChannel(),testinfo);
        return result;
    }

    @RequestMapping("/t4")
    @ResponseBody
    public String t4() throws Exception {
        return fabricClient.query(fabricClient.getDefaultChannel(), "aef0101225");
    }

    @RequestMapping("/upload_index")
	public String index() {
		return "upload";
	}

	@RequestMapping("/upload") //new annotation since 4.3
	public String singleFileUpload(@RequestParam("file") MultipartFile file,
								   RedirectAttributes redirectAttributes) {
		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
			return "redirect:uploadStatus";
		}
		try {
			// Get the file and save it somewhere
			String path=saveFile(file);
			redirectAttributes.addFlashAttribute("message",
					"You successfully uploaded '" + file.getOriginalFilename() + "'");
			redirectAttributes.addFlashAttribute("path",
					"file path url '" + path + "'");
		} catch (Exception e) {
			logger.error("upload file failed",e);
		}
		return "redirect:/uploadStatus";
	}

	@RequestMapping("/uploadStatus")
	public String uploadStatus() {
		return "uploadStatus";
	}

	/**
	 * @param multipartFile
	 * @return
	 * @throws IOException
	 */
	public String saveFile(MultipartFile multipartFile) throws IOException {
		String[] fileAbsolutePath={};
		String fileName=multipartFile.getOriginalFilename();
		String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
		byte[] file_buff = null;
		InputStream inputStream=multipartFile.getInputStream();
		if(inputStream!=null){
			int len1 = inputStream.available();
			file_buff = new byte[len1];
			inputStream.read(file_buff);
		}
		inputStream.close();
		FastDFSFile file = new FastDFSFile(fileName, file_buff, ext);
		try {
			fileAbsolutePath = FastDFSClient.upload(file);  //upload to fastdfs
		} catch (Exception e) {
			logger.error("upload file Exception!",e);
		}
		if (fileAbsolutePath==null) {
			logger.error("upload file failed,please upload again!");
		}
		String path=FastDFSClient.getTrackerUrl()+fileAbsolutePath[0]+ "/"+fileAbsolutePath[1];
		return path;
	}

}
