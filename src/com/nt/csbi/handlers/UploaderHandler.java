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
 * @author ����
 *
 * @date: 2017��4��19�� ����9:47:38
 */
@Controller
public class UploaderHandler {

	@Autowired
	private FileUploaderService fileService;
	
	
	@RequestMapping("/zyUploadsssss")
	@ResponseBody
	public String zyUpload(HttpServletRequest request) throws Exception{
		String result="";
		
		//����һ��ͨ�õĶಿ�ֽ�����  
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
        //�ж� request �Ƿ����ļ��ϴ�,���ಿ������  
        if(multipartResolver.isMultipart(request)){  
            //ת���ɶಿ��request    
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// ȡ��request�е������ļ���
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				// ȡ���ϴ��ļ�
				MultipartFile file = multiRequest.getFile(iter.next());
				// ���ݷ�װ���� MultipartFile reqeust
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
				
				// ȡ�õ�ǰ�ϴ��ļ����� ������
				// ������Ҫ����ļ��Ĵ���Ŷ
				// logger.debug("ͼƬ�ϴ���{}", JsonUtil.toString(map));
				
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
