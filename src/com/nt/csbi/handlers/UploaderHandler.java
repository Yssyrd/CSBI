package com.nt.csbi.handlers;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.nt.csbi.services.FileUploaderService;


/**
 * @author 杨润东
 *
 * @date: 2017年4月19日 上午9:47:38
 */
@Controller
public class UploaderHandler {

	@Autowired
	private FileUploaderService fileService;
	
	
	@RequestMapping("/zyUploadsssss")
	@ResponseBody
	public String zyUpload(HttpServletRequest request) throws Exception{
		String result="";
		
		//创建一个通用的多部分解析器  
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
        //判断 request 是否有文件上传,即多部分请求  
        if(multipartResolver.isMultipart(request)){  
            //转换成多部分request    
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				// 取得上传文件
				MultipartFile file = multiRequest.getFile(iter.next());
				// 数据封装操作 MultipartFile reqeust
				String logoPathDir = "/uploaderFile/";
				String logoPathDir2 = "/uploaderFileCache/";
				String logoRealPathDir = request.getSession().getServletContext()
		                .getRealPath(logoPathDir);
				File logoSaveFile = new File(logoRealPathDir);
				if (!logoSaveFile.exists())
			         logoSaveFile.mkdirs();
				
				String myFileName = file.getOriginalFilename();
				
				String fileName = logoRealPathDir + File.separator + myFileName;
				
				System.out.println(fileName);
				
				if(!fileName.substring(fileName.length()-3,fileName.length()).equals("txt")&&!fileName.substring(fileName.length()-3,fileName.length()).equals("TXT")){
					return  "2";
				}
				
				
				File fileTXT = new File(fileName);
				
				if(fileTXT.exists()){
					return "1";
				}
				
				// 取得当前上传文件的文 件名称
				// 这里需要你对文件的处理哦
				// logger.debug("图片上传：{}", JsonUtil.toString(map));
				
				file.transferTo(fileTXT);
				
				String str = fileService.uploader(file,fileName);
				
				System.out.println(str);
				if (str.equals("0")) {
					
					result = "0";
				}
				if(str.equals("1")){
					
					result = "3";
				}
			}
			
		}
        return result.trim();
        
	}
	
	@RequestMapping(value="/test", method=RequestMethod.POST)
	public String Test(@RequestParam("id") String id){
		
		
		return "success";
	}
	
}
