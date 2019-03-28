package com.nt.csbi.handlers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ����
 *
 * @date: 2017��4��21�� ����2:11:19
 */
@Controller
public class RedirectPageController {
	
	@RequestMapping("/historyFigure")
	public String HistoryInfoFigurePage(){
		
		return "historyFigure";
	}
	@RequestMapping("/Search")
	public String SearchDiv(){
		
		return "searchDiv";
	}
	@RequestMapping("/hitoryInfo")
	public String HistoryInfoPage(){
		
		return "historyFigure";
	}
	@RequestMapping("/uploadData")
	public String uploadDataPage(){
		
		return "uploader";
	}
	@RequestMapping("/originalDataPreview")
	public String originalDataPreview(){
		
		return "originalData";
	}
	@RequestMapping("/realTimeFigure")
	public String realTimeFigure(){
		
		return "realTimeFigure";
	}
	
}
