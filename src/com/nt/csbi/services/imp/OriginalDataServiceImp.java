package com.nt.csbi.services.imp;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.pdf.BaseFont;
import com.nt.csbi.dao.OriginalDataDao;
import com.nt.csbi.entities.HistoryInfo;
import com.nt.csbi.entities.OriginalData;
import com.nt.csbi.entities.RealTimeInfo;
import com.nt.csbi.services.OriginalDataService;

import jxl.HeaderFooter;
import jxl.SheetSettings;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * @author 杨润东
 *
 * @date: 2017年4月28日 下午2:16:44
 */
@Service
public class OriginalDataServiceImp implements OriginalDataService {

	@Autowired
	private OriginalDataDao originalDataDao;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void dataHandler(String dataId, HttpServletResponse response,HttpServletRequest request,String pdfName) throws Exception {
		
		OriginalData data = originalDataDao.getOriginalTable(dataId);
		
		if(pdfName.trim().equals("")){
			String str = String.valueOf(new Timestamp(new Date().getTime()));
			pdfName = str.substring(0,4)+str.substring(5,7)+str.substring(8,10)+str.substring(11,13)
			+str.substring(14,16)+str.substring(17,19)+ ".pdf";
		}else{
			pdfName = pdfName + ".pdf";
		}
		
	//	String outputFile = "原始记录表.pdf";
		OutputStream os = new FileOutputStream(request.getRealPath("/")+"img/"+pdfName);
		ITextRenderer renderer = new ITextRenderer();
		ITextFontResolver fontResolver = renderer.getFontResolver();
		fontResolver.addFont("C:/Windows/fonts/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		StringBuffer html = new StringBuffer();
		// DOCTYPE 必需写否则类似于 这样的字符解析会出现错误
		html.append("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd '> ");
		html.append("<html xmlns='http://www.w3.org/1999/xhtml '>")
			.append("<head>")
		    .append("<meta http-equiv='Content-Type' content='text/html; charset=utf-8' /> ")
		    //css
		    .append("<style type='text/css'> *{font-family: SimSun;} a{color:#000000;} .titleTable{width: 100%}.titleTable tr{text-align:right;font-size: medium;}.underLine{border-bottom:1px solid #000;text-align:center;} .oneTable{width: 100%;border-collapse:collapse;border:none;}.oneTable tr{text-align:center;}.oneTable tr td{border:1px solid #000;}.oneTable tr td{border:solid #000 1px;font-size: medium;}</style></head>");
		    //title
		
		String[] options = data.getShowOptions().split("#@");
		boolean firFlag=true,secFlag = true,thrFlag = false,allFlag=true,t32Flag = false;
		
		String[] title = data.getTitleInfo().split("#@");
		String[] nineFianl = data.getAutograph().split("#@");
		html.append("<body><div style='width: 90%;margin-left:4%;'><div style='text-align: center;'><h3 style='font-size: 25px;'><b>婴儿培养箱校准记录</b></h3>");
		if(nineFianl.length>2){
			if(!nineFianl[2].trim().equals("")){
				html.append("<div style='border-bottom: 1px solid #000;display: block;width: 25%;float: right;'><input style='width: 100%;border:none;'>")
				.append(nineFianl[2])
				.append("</input></div>");
			}
		}
		html.append("</div><div><table class = 'titleTable top' ><tr><td width='4.5%' style='text-align:right;'>委托单位</td><td class='underLine' colspan = '5' >")
		    .append((title[0]!=null&&!title[0].trim().equals(""))?title[0].trim():"/")
			.append("</td></tr><tr><td class='nameTd' width='4.5%' style='text-align:right;'>器具名称</td><td class='underLine' colspan = '2' width='40%'>")
			.append((title[1]!=null&&!title[1].trim().equals(""))?title[1].trim():"/")
			.append("</td><td class='nameTd'  width='4.5%' style='text-align:right;'>制造厂</td><td class='underLine' colspan = '2' width='40%'>")
			.append((title[2]!=null&&!title[2].trim().equals(""))?title[2].trim():"/")
			.append("</td></tr><tr><td></td></tr></table><table class = 'titleTable' ><tr><td class='nameTd' width='4.5%'>型号规格</td><td class='underLine' width='25%'>")
			.append((title[3]!=null&&!title[3].trim().equals(""))?title[3].trim():"/")
			.append("</td><td class='nameTd' width='4.5%'>出厂编号</td><td class='underLine'>")
			.append((title[4]!=null&&!title[4].trim().equals(""))?title[4].trim():"/")
			.append("</td><td class='nameTd' width='4.5%'>校准日期</td><td class='underLine' width='25%'>")
			.append((title[5]!=null&&!title[5].trim().equals(""))?title[5].trim():"/")
			.append("</td></tr><tr><td class='nameTd'>地&nbsp;&nbsp;点</td><td class='underLine'>")
			.append((title[6]!=null&&!title[6].trim().equals(""))?title[6].trim():"/")
			.append("</td><td class='nameTd'>温&nbsp;&nbsp;度</td><td class='underLine'>")
			.append((title[7]!=null&&!title[7].trim().equals(""))?title[7].trim():"/")
			.append("℃</td><td class='nameTd'>湿&nbsp;&nbsp;度</td><td class='underLine'>")
			.append((title[8]!=null&&!title[8].trim().equals(""))?title[8].trim():"/")
			.append("%RH</td></tr><tr><td>校准依据</td><td class='underLine' colspan = '5' >")
			.append((title[9]!=null&&!title[9].trim().equals(""))?title[9].trim():"/")
			.append("</td></tr></table></div>");
			//one
		
		if(options[0].trim().equals("1")){
			firFlag = false;
			String[] one = data.getTableOne().split("#@");
			html.append("<div><br></br><a>外观、报警功能及电气安全检查 </a><table class = 'oneTable' ><tr ><td colspan = '2' height='20px' width='50%'>检查项目</td><td colspan = '2' width='50%' >测&nbsp;量&nbsp;值</td></tr><tr><td colspan = '2' height='25px'  >&nbsp;外&nbsp;&nbsp;观&nbsp;</td><td colspan = '2' >")
				.append((one[0].trim().equals("0"))?"符合":(one[0].trim().equals("1")?"不符合":""))
				.append("</td></tr><tr><td colspan = '2' height='20px'  >电源中断报警</td><td colspan = '2' >")
				.append((one[1].trim().equals("0"))?"符合":(one[1].trim().equals("1")?"不符合":(one[1].trim().equals("2")?"功能缺失":"")))
				.append("</td></tr><tr><td colspan = '2' height='20px'  >风机报警</td><td colspan = '2' >")
				.append((one[2].trim().equals("0"))?"符合":(one[2].trim().equals("1")?"不符合":(one[2].trim().equals("2")?"功能缺失":"")))
				.append("</td></tr><tr><td colspan = '2' height='20px'  >过热切断装置报警</td><td colspan = '2' >")
				.append((one[3].trim().equals("0"))?"符合":(one[3].trim().equals("1")?"不符合":(one[3].trim().equals("2")?"功能缺失":"")))
				.append("</td></tr><tr ><td rowspan = '2' >患者漏电流/μA</td><td height='25px'  >&nbsp;直&nbsp;&nbsp;流&nbsp;</td><td colspan = '2' >")
				.append((one[4]!=null&&!one[4].trim().equals(""))?one[4].trim():"/")
				.append("</td></tr><tr><td height='20px' >&nbsp;交&nbsp;&nbsp;流&nbsp;</td><td colspan = '2' >")
				.append((one[5]!=null&&!one[5].trim().equals(""))?one[5].trim():"/")
				.append("</td></tr><tr ><td colspan = '2' height='20px' width='50%'  >机壳漏电流/μA</td><td colspan = '2' >")
				.append((one[6]!=null&&!one[6].trim().equals(""))?one[6].trim():"/")
				.append("</td></tr><tr ><td colspan = '2' height='20px' width='50%'  >接地电阻/Ω</td><td colspan = '2'  >")
				.append((one[7]!=null&&!one[7].trim().equals(""))?one[7].trim():"/")
				.append("</td></tr></table></div>");
		}
			
			//two
		
		String[] two = data.getTableTwo().split("#@");
		String height = "20";
		String height32 = "23";
		if(firFlag){
			height = "27";
			height32 = "30";
			html.append("<br></br>");
		}
		html.append("<div><br></br><a style='display: inline-block;'>温度校准记录 </a><a style='display: inline-block;margin-left: 60%;'>单位：℃</a>");
		
		if(options[1].trim().equals("1")){
			
			html.append("<table class = 'oneTable' ><tr><td rowspan='3' width='30px' >次数</td><td colspan='3' height='"+height+"px' >控制温度</td><td colspan='3' >32</td><td colspan='3' >控制温度</td><td colspan='3' >36</td></tr>")
				.append("<tr><td rowspan='2' width='50px' >显示<br></br>温度</td><td colspan='5' height='"+height+"px' >温度</td><td rowspan='2'  width='7.69%'  >显示<br></br>温度</td><td colspan='5' >温度</td></tr>")
				.append("<tr><td height='"+height+"px'  width='20px'  >A</td><td  width='20px' >B</td><td  width='20px' >C</td><td  width='20px' >D</td><td  width='7.69%' >E</td><td  width='7.69%' >A</td><td  width='7.69%' >B</td><td  width='7.69%' >C</td><td  width='7.69%' >D</td><td  width='30px' >&nbsp;E&nbsp;</td></tr>");
				for(int i = 0;i<15;i++)	
				{
					html.append("<tr><td height='"+height+"px'>")
						.append(i+1)
						.append("</td><td width='9%'>")
						.append((two[i*12]!=null&&!two[i*12].trim().equals(""))?two[i*12].trim():"/")
						.append("</td><td width='9%'>")
						.append((two[i*12+1]!=null&&!two[i*12+1].trim().equals(""))?two[i*12+1].trim():"/")
						.append("</td><td width='9%'>")
						.append((two[i*12+2]!=null&&!two[i*12+2].trim().equals(""))?two[i*12+2].trim():"/")
						.append("</td><td width='9%'>")
						.append((two[i*12+3]!=null&&!two[i*12+3].trim().equals(""))?two[i*12+3].trim():"/")
						.append("</td><td width='9%'>")
						.append((two[i*12+4]!=null&&!two[i*12+4].trim().equals(""))?two[i*12+4].trim():"/")
						.append("</td><td width='9%'>")
						.append((two[i*12+5]!=null&&!two[i*12+5].trim().equals(""))?two[i*12+5].trim():"/")
						.append("</td><td width='9%'>")
						.append((two[i*12+6]!=null&&!two[i*12+6].trim().equals(""))?two[i*12+6].trim():"/")
						.append("</td><td width='9%'>")
						.append((two[i*12+7]!=null&&!two[i*12+7].trim().equals(""))?two[i*12+7].trim():"/")
						.append("</td><td width='9%'>")
						.append((two[i*12+8]!=null&&!two[i*12+8].trim().equals(""))?two[i*12+8].trim():"/")
						.append("</td><td width='9%'>")
						.append((two[i*12+9]!=null&&!two[i*12+9].trim().equals(""))?two[i*12+9].trim():"/")
						.append("</td><td width='9%'>")
						.append((two[i*12+10]!=null&&!two[i*12+10].trim().equals(""))?two[i*12+10].trim():"/")
						.append("</td><td width='9%'>")
						.append((two[i*12+11]!=null&&!two[i*12+11].trim().equals(""))?two[i*12+11].trim():"/")
						.append("</td></tr>");
				}
				html.append("<tr><td height='"+height+"px' >修正值</td><td>/</td><td>")
				.append((two[180]!=null&&!two[180].trim().equals(""))?two[180].trim():"/")
				.append("</td><td>")
				.append((two[181]!=null&&!two[181].trim().equals(""))?two[181].trim():"/")
				.append("</td><td>")
				.append((two[182]!=null&&!two[182].trim().equals(""))?two[182].trim():"/")
				.append("</td><td>")
				.append((two[183]!=null&&!two[183].trim().equals(""))?two[183].trim():"/")
				.append("</td><td>")
				.append((two[184]!=null&&!two[184].trim().equals(""))?two[184].trim():"/")
				.append("</td><td>/</td><td>")
				.append((two[185]!=null&&!two[185].trim().equals(""))?two[185].trim():"/")
				.append("</td><td>")
				.append((two[186]!=null&&!two[186].trim().equals(""))?two[186].trim():"/")
				.append("</td><td>")
				.append((two[187]!=null&&!two[187].trim().equals(""))?two[187].trim():"/")
				.append("</td><td>")
				.append((two[188]!=null&&!two[188].trim().equals(""))?two[188].trim():"/")
				.append("</td><td>")
				.append((two[189]!=null&&!two[189].trim().equals(""))?two[189].trim():"/")
				.append("</td></tr><tr><td height='"+height+"px' >平均值</td><td>")
				.append((two[190]!=null&&!two[190].trim().equals(""))?two[190].trim():"/")
				.append("</td><td>")
				.append((two[191]!=null&&!two[191].trim().equals(""))?two[191].trim():"/")
				.append("</td><td>")
				.append((two[192]!=null&&!two[192].trim().equals(""))?two[192].trim():"/")
				.append("</td><td>")
				.append((two[193]!=null&&!two[193].trim().equals(""))?two[193].trim():"/")
				.append("</td><td>")
				.append((two[194]!=null&&!two[194].trim().equals(""))?two[194].trim():"/")
				.append("</td><td>")
				.append((two[195]!=null&&!two[195].trim().equals(""))?two[195].trim():"/")
				.append("</td><td>")
				.append((two[196]!=null&&!two[196].trim().equals(""))?two[196].trim():"/")
				.append("</td><td>")
				.append((two[197]!=null&&!two[197].trim().equals(""))?two[197].trim():"/")
				.append("</td><td>")
				.append((two[198]!=null&&!two[198].trim().equals(""))?two[198].trim():"/")
				.append("</td><td>")
				.append((two[199]!=null&&!two[199].trim().equals(""))?two[199].trim():"/")
				.append("</td><td>")
				.append((two[200]!=null&&!two[200].trim().equals(""))?two[200].trim():"/")
				.append("</td><td>")
				.append((two[201]!=null&&!two[201].trim().equals(""))?two[201].trim():"/")
				.append("</td></tr>")
				.append("<tr><td colspan='2' rowspan='2' >温度偏差</td><td colspan='2' height='"+height+"px' >32</td><td colspan='3' >")
				.append((two[202]!=null&&!two[202].trim().equals(""))?two[202].trim():"/")
				.append("</td><td colspan='2' rowspan='2' >温度波动度</td><td colspan='2' height='"+height+"px' >32</td><td colspan='2' >")
				.append((two[203]!=null&&!two[203].trim().equals(""))?two[203].trim():"/")
				.append("</td></tr><tr><td colspan='2' height='"+height+"px' >36</td><td colspan='3' >")
				.append((two[204]!=null&&!two[204].trim().equals(""))?two[204].trim():"/")
				.append("</td><td colspan='2' >36</td><td colspan='2' >")
				.append((two[205]!=null&&!two[205].trim().equals(""))?two[205].trim():"/")
				.append("</td></tr><tr><td colspan='2' rowspan='2' >温度均匀度</td><td colspan='2' height='"+height+"px' >32</td><td colspan='3' >")
				.append((two[206]!=null&&!two[206].trim().equals(""))?two[206].trim():"/")
				.append("</td><td colspan='4' rowspan='2' >平均培养箱温度与<br></br>控制温度之差 </td><td colspan='2' rowspan='2' height='"+height+"px' >")
				.append((two[207]!=null&&!two[207].trim().equals(""))?two[208].trim():"/")
				.append("</td></tr><tr><td colspan='2' height='"+height+"px' >36</td><td colspan='3' >")
				.append((two[208]!=null&&!two[208].trim().equals(""))?two[207].trim():"/")
				.append("</td></tr><tr><td colspan='4' rowspan='2' height='"+ String.valueOf(Integer.valueOf(height)*2) +"px' >调整控制温度后，<br></br>测得培养箱温度最大值</td><td colspan='3' rowspan='2' >")
				.append((two[209]!=null&&!two[209].trim().equals(""))?two[209].trim():"/")
				.append("</td><td colspan='3' rowspan='2' >温度超调量</td><td colspan='3' rowspan='2' >")
				.append((two[210]!=null&&!two[210].trim().equals(""))?two[210].trim():"/")
				.append("</td></tr></table>");
				if(firFlag){
					html.append("<br></br><br></br><br></br>");
				}
				
		}
		if(options[1].trim().equals("0")){
			t32Flag = true;
			html.append("<br></br><table class='oneTable 32And36Two'><tr><td rowspan='3' width='60px' class='bordertd'><strong>次数</strong></td><td colspan='3' height='30px' class='bordertd'><strong>控制温度</strong></td><td colspan='3' class='bordertd'><strong>32</strong></td></tr><tr><td rowspan='2' width='60px' class='bordertd'><strong>显示温度</strong></td><td colspan='5' height='30px' class='bordertd'><strong>温度</strong></td></tr><tr><td height='30px' class='bordertd'><strong>A</strong></td><td class='bordertd'><strong>B</strong></td><td class='bordertd'><strong>C</strong></td><td class='bordertd'><strong>D</strong></td><td class='bordertd'><strong>E</strong></td></tr>");
			for(int i = 0;i<15;i++){
				html.append("<tr><td height='"+height32+"'>")
				.append(i+1)
				.append("</td><td width='16%'>")
				.append((two[i*12]!=null&&!two[i*12].trim().equals(""))?two[i*12].trim():"/")
				.append("</td><td width='16%'>")
				.append((two[i*12+1]!=null&&!two[i*12+1].trim().equals(""))?two[i*12+1].trim():"/")
				.append("</td><td width='16%'>")
				.append((two[i*12+2]!=null&&!two[i*12+2].trim().equals(""))?two[i*12+2].trim():"/")
				.append("</td><td width='16%'>")
				.append((two[i*12+3]!=null&&!two[i*12+3].trim().equals(""))?two[i*12+3].trim():"/")
				.append("</td><td width='16%'>")
				.append((two[i*12+4]!=null&&!two[i*12+4].trim().equals(""))?two[i*12+4].trim():"/")
				.append("</td><td width='16%'>")
				.append((two[i*12+5]!=null&&!two[i*12+5].trim().equals(""))?two[i*12+5].trim():"/")
				.append("</td></tr>");
			}
			html.append("<tr><td height='"+height32+"' >修正值</td><td>/</td><td>")
			.append((two[180]!=null&&!two[180].trim().equals(""))?two[180].trim():"/")
			.append("</td><td>")
			.append((two[181]!=null&&!two[181].trim().equals(""))?two[181].trim():"/")
			.append("</td><td>")
			.append((two[182]!=null&&!two[182].trim().equals(""))?two[182].trim():"/")
			.append("</td><td>")
			.append((two[183]!=null&&!two[183].trim().equals(""))?two[183].trim():"/")
			.append("</td><td>")
			.append((two[184]!=null&&!two[184].trim().equals(""))?two[184].trim():"/")
			.append("</td></tr>");
			html.append("<tr><td height='"+height32+"' >平均值</td><td>")
			.append((two[190]!=null&&!two[190].trim().equals(""))?two[190].trim():"/")
			.append("</td><td>")
			.append((two[191]!=null&&!two[191].trim().equals(""))?two[191].trim():"/")
			.append("</td><td>")
			.append((two[192]!=null&&!two[192].trim().equals(""))?two[192].trim():"/")
			.append("</td><td>")
			.append((two[193]!=null&&!two[193].trim().equals(""))?two[193].trim():"/")
			.append("</td><td>")
			.append((two[194]!=null&&!two[194].trim().equals(""))?two[194].trim():"/")
			.append("</td><td>")
			.append((two[195]!=null&&!two[195].trim().equals(""))?two[195].trim():"/")
			.append("</td></tr>");
			html.append("<tr><td height='"+height32+"' >温度偏差</td><td>")
			.append((two[202]!=null&&!two[202].trim().equals(""))?two[202].trim():"/")
			.append( "</td><td>温度波动度</td><td>")
			.append((two[202]!=null&&!two[203].trim().equals(""))?two[203].trim():"/")
			.append( "</td><td colspan='2'>温度均匀度</td><td>")
			.append((two[206]!=null&&!two[206].trim().equals(""))?two[206].trim():"/")
			.append("</td></tr></table><br></br><br></br>");
			if(firFlag){
				html.append("<br></br><br></br><br></br><br></br><br></br><br></br>");
			}
		}
		html.append("</div>");
		System.out.println("html:"+html);
		if(options[2].trim().equals("1")){
			//three
			allFlag = false;
			String[] three = data.getTableThree().split("#@");
			html.append("<div><h4 style='display: inline-block;'>床垫倾斜时温度均匀度 </h4><h4 style='display: inline-block;margin-left: 50%;'>单位：℃</h4><table class = 'oneTable' ><tr><td rowspan='2' height='40px'>次数 </td><td colspan='2' height='20px'>控制温度</td><td colspan='3' >32</td></tr><tr><td >A</td><td >B</td><td >C</td><td >D</td><td >E</td></tr>");
			for(int i = 0;i<15;i++)	
			{
				html.append("<tr><td>")
					.append(i+1)
					.append("</td><td>")
					.append((three[i*6+1]!=null&&!three[i*6+1].trim().equals(""))?three[i*6+1].trim():"/")
					.append("</td><td>")
					.append((three[i*6+2]!=null&&!three[i*6+2].trim().equals(""))?three[i*6+2].trim():"/")
					.append("</td><td>")
					.append((three[i*6+3]!=null&&!three[i*6+3].trim().equals(""))?three[i*6+3].trim():"/")
					.append("</td><td>")
					.append((three[i*6+4]!=null&&!three[i*6+4].trim().equals(""))?three[i*6+4].trim():"/")
					.append("</td><td>")
					.append((three[i*6+5]!=null&&!three[i*6+5].trim().equals(""))?three[i*6+5].trim():"/")
					.append("</td></tr>");
			}
			html.append("<tr><td height='20px' width='15%'>修正值</td><td  width='15%'>")
			.append((three[90]!=null&&!three[90].trim().equals(""))?three[90].trim():"/")
			.append("</td><td  width='15%'>")
			.append((three[91]!=null&&!three[91].trim().equals(""))?three[91].trim():"/")
			.append("</td><td  width='15%'>")
			.append((three[92]!=null&&!three[92].trim().equals(""))?three[92].trim():"/")
			.append("</td><td  width='15%'>")
			.append((three[93]!=null&&!three[93].trim().equals(""))?three[93].trim():"/")
			.append("</td><td  width='15%'>")
			.append((three[94]!=null&&!three[94].trim().equals(""))?three[94].trim():"/")
			.append("</td></tr>")
			.append("<tr><td height='20px'>平均值</td><td >")
			.append((three[95]!=null&&!three[95].trim().equals(""))?three[95].trim():"/")
			.append("</td><td >")
			.append((three[96]!=null&&!three[96].trim().equals(""))?three[96].trim():"/")
			.append("</td><td >")
			.append((three[97]!=null&&!three[97].trim().equals(""))?three[97].trim():"/")
			.append("</td><td >")
			.append((three[98]!=null&&!three[98].trim().equals(""))?three[98].trim():"/")
			.append("</td><td >")
			.append((three[99]!=null&&!three[99].trim().equals(""))?three[99].trim():"/")
			.append("</td></tr><tr><td height='20px' colspan='2'>床垫倾斜时温度均匀度</td><td >")
			.append((three[100]!=null&&!three[100].trim().equals(""))?three[100].trim():"/")
			.append("</td><td >")
			.append((three[101]!=null&&!three[101].trim().equals(""))?three[101].trim():"/")
			.append("</td><td >")
			.append((three[102]!=null&&!three[102].trim().equals(""))?three[102].trim():"/")
			.append("</td><td >")
			.append((three[103]!=null&&!three[103].trim().equals(""))?three[103].trim():"/")
			.append("</td></tr></table></div>");
		}else{
			secFlag = false;
		}
			
			
		if(options[3].trim().equals("1")){
			//four
			allFlag = false;
			String[] four = data.getTableFour().split("#@");
			html.append("<div><br></br><a>湿度相对偏差 </a><table class = 'oneTable' ><tr><td width='20%' height='20px' >次数</td><td width='20%' >1</td><td width='20%' >2</td><td width='20%' >3</td><td >平均值</td></tr><tr><td height='20px' >显示湿度/%RH</td><td>")
			.append((four[0]!=null&&!four[0].trim().equals(""))?four[0].trim():"/")
			.append("</td><td>")
			.append((four[1]!=null&&!four[1].trim().equals(""))?four[1].trim():"/")
			.append("</td><td>")
			.append((four[2]!=null&&!four[2].trim().equals(""))?four[2].trim():"/")
			.append("</td><td>")
			.append((four[3]!=null&&!four[3].trim().equals(""))?four[3].trim():"/")
			.append("</td></tr><tr><td  height='20px' >实测湿度/%RH</td><td>")
			.append((four[4]!=null&&!four[4].trim().equals(""))?four[4].trim():"/")
			.append("</td><td>")
			.append((four[5]!=null&&!four[5].trim().equals(""))?four[5].trim():"/")
			.append("</td><td>")
			.append((four[6]!=null&&!four[6].trim().equals(""))?four[6].trim():"/")
			.append("</td><td>")
			.append((four[7]!=null&&!four[7].trim().equals(""))?four[7].trim():"/")
			.append("</td></tr><tr><td  height='20px' >相对湿度偏差/%RH</td><td colspan='4' >")
			.append((four[8]!=null&&!four[8].trim().equals(""))?four[8].trim():"/")
			.append("</td></tr></table></div>");
		}else{
			secFlag = false;
		}
			
		if(options[4].trim().equals("1")){	
			//five
			allFlag = false;
			String[] five = data.getTableFive().split("#@");
			html.append("<div><br></br><a>婴儿舱内氧分析器示值误差 </a><table class = 'oneTable' ><tr><td width='20%' height='20px' >次数</td><td width='20%' >1</td><td width='20%' >2</td><td width='20%' >3</td><td >平均值</td></tr><tr><td height='20px' >显示氧体积分数/%</td><td>")
			.append((five[0]!=null&&!five[0].trim().equals(""))?five[0].trim():"/")
			.append("</td><td>")
			.append((five[1]!=null&&!five[1].trim().equals(""))?five[1].trim():"/")
			.append("</td><td>")
			.append((five[2]!=null&&!five[2].trim().equals(""))?five[2].trim():"/")
			.append("</td><td>")
			.append((five[3]!=null&&!five[3].trim().equals(""))?five[3].trim():"/")
			.append("</td></tr><tr><td  height='20px' >氧标准气体的体积分数/%</td><td  colspan='4' >")
			.append((five[4]!=null&&!five[4].trim().equals(""))?five[4].trim():"/")
			.append("</td></tr><tr><td  height='20px' >氧分析器示值误差/%</td><td  colspan='4' >")
			.append((five[5]!=null&&!five[5].trim().equals(""))?five[5].trim():"/")
			.append("</td></tr></table></div>");
		}else{
			secFlag = false;
		}
		
		if(options[5].trim().equals("1")){
			//six
			allFlag = false;
			String[] six = data.getTableSix().split("#@");
			html.append("<div><br></br><a>婴儿舱的噪声</a><table class = 'oneTable' ><tr><td width='20%' height='20px' >婴儿舱内的噪声/dB</td><td width='10%' >1</td><td width='10%' >")
			.append((six[0]!=null&&!six[0].trim().equals(""))?six[0].trim():"/")
			.append("</td><td width='10%' >2</td><td width='10%'>")
			.append((six[1]!=null&&!six[1].trim().equals(""))?six[1].trim():"/")
			.append("</td><td width='10%' >3</td><td width='10%'>")
			.append((six[2]!=null&&!six[2].trim().equals(""))?six[2].trim():"/")
			.append("</td><td width='10%' >平均值</td><td width='10%'>")
			.append((six[3]!=null&&!six[3].trim().equals(""))?six[3].trim():"/")
			.append("</td></tr></table></div>");
		}else{
			secFlag = false;
			thrFlag = true;
		}
		
		if(options[6].trim().equals("1")){
			//seven
			allFlag = false;
			String[] seven = data.getTableSeven().split("#@");
			html.append("<div><br></br><a>报警器报警噪声 </a><table class = 'oneTable' ><tr><td width='20%' height='20px' >婴儿舱内的噪声/dB</td><td width='10%' >1</td><td width='10%' >")
			.append((seven[0]!=null&&!seven[0].trim().equals(""))?seven[0].trim():"/")
			.append("</td><td width='10%' >2</td><td width='10%' >")
			.append((seven[1]!=null&&!seven[1].trim().equals(""))?seven[1].trim():"/")
			.append("</td><td width='10%' >3</td><td width='10%' >")
			.append((seven[2]!=null&&!seven[2].trim().equals(""))?seven[2].trim():"/")
			.append("</td><td >平均值</td><td width='10%' >")
			.append((seven[3]!=null&&!seven[3].trim().equals(""))?seven[3].trim():"/")
			.append("</td></tr><tr><td width='20%' height='20px' >箱外噪声/dB</td><td width='10%' >1</td><td width='10%' >")
			.append((seven[4]!=null&&!seven[4].trim().equals(""))?seven[4].trim():"/")
			.append("</td><td width='10%' >2</td><td width='10%' >")
			.append((seven[5]!=null&&!seven[5].trim().equals(""))?seven[5].trim():"/")
			.append("</td><td width='10%' >3</td><td width='10%' >")
			.append((seven[6]!=null&&!seven[6].trim().equals(""))?seven[6].trim():"/")
			.append("</td><td >平均值</td><td width='10%' >")
			.append((seven[7]!=null&&!seven[7].trim().equals(""))?seven[7].trim():"/")
			.append("</td></tr></table></div>");
		}else{
			secFlag = false;
		}
		
		if(options[7].trim().equals("1")){
			//eight
			allFlag = false;
			String eight = data.getTableEight();
			
			html.append("<div><br></br><a>备注 </a><textarea cols='5' style='width: 100%;border:1px solid #000;min-height: "+ (thrFlag?"35":"55")+"px;overflow:hidden;'>")
			.append((eight!=null)?eight:" ")
			.append("</textarea></div>");
		}else{
			html.append("<br></br>");
			secFlag = false;
		}
		
		if(options[8].trim().equals("1")){
			if(secFlag){
				html.append("<br></br><br></br><br></br><br></br>");
			}
			allFlag = false;
			String[] nine = data.getTitleInfo().split("#@");
			html.append("<br></br><div><a>标准信息</a><br></br><table  class = 'oneTable'><tr><td width='4.5%'>标准名称</td><td class='underLine' colspan = '3' >")
			.append((nine[10]!=null&&!nine[10].trim().equals(""))?nine[10].trim():"/")
			.append("</td><td class='nameTd' width='5%'>标准制造厂</td><td class='underLine'>")
			.append((nine[11]!=null&&!nine[11].trim().equals(""))?nine[11].trim():"/")
			.append("</td></tr><tr><td class='nameTd' width='4.5%' colspan = '3'>标准型号</td><td class='underLine' >")
			.append((nine[12]!=null&&!nine[12].trim().equals(""))?nine[12].trim():"/")
			.append("</td><td class='nameTd' width='4.5%'>标准编号</td><td class='underLine' >")
			.append((nine[13]!=null&&!nine[13].trim().equals(""))?nine[13].trim():"/")
			.append("</td></tr></table>");
			
			html.append("<table class = 'oneTable' border='1' style='table-layout: fixed;WORD-BREAK: break-all; WORD-WRAP: break-word'><tr><td class='nameTd' width='130px'>等级(不确定度)</td><td class='underLine' colspan = '5'>")
			.append((nine[14]!=null&&!nine[14].trim().equals(""))?nine[14].trim():"/")
			.append("</td></tr></table>");

			html.append("<table class = 'oneTable' style='table-layout: fixed;WORD-BREAK: break-all; WORD-WRAP: break-word'><tr><td class='nameTd' width='130px'>检定/校准证书号</td><td class='underLine' colspan = '4'>")
			.append((nine[15]!=null&&!nine[15].trim().equals(""))?nine[15].trim():"/")
			.append("</td></tr></table>");
			
			html.append("<table class = 'oneTable' style='table-layout: fixed;WORD-BREAK: break-all; WORD-WRAP: break-word'><tr><td class='nameTd' width='70px'>有效期至</td><td class='underLine' colspan = '5'>")
			.append((nine[16]!=null&&!nine[16].trim().equals(""))?nine[16].trim():"/")
			.append("</td></tr></table></div>");
		}
		String finalStr = (thrFlag?"style='margin-top:5px'":"style='margin-top:20px'");
		if(allFlag){
			System.out.println("html"+html);
			if(t32Flag&&firFlag){
				html = new StringBuffer(html.substring(0, html.length()-24) + "</div>");
			}else{
				html = new StringBuffer(html.substring(0, html.length()-9));
			}
			System.out.println("html2"+html);
			finalStr = "";
		}
		
		html.append("<div "+finalStr+"><table class='titleTable'><tr><td width='15%' ></td><td width='10%' height = '20px'>校准员</td><td width='20%' class='underLine'>")
		.append((nineFianl[0]!=null)?nineFianl[0].trim():" ")
		.append("</td><td width='10%' ></td><td width='10%'>核验员</td><td width='20%' class='underLine'>")
		.append((nineFianl[1]!=null)?nineFianl[1].trim():" ")
		.append("</td><td></td></tr></table></div>");
		
		html.append("</div></body></html>");
		renderer.setDocumentFromString(html.toString());
		// 解决图片的相对路径问题
		// renderer.getSharedContext().setBaseURL("file:/F:/teste/html/");
		renderer.layout();
		renderer.createPDF(os);
		os.close();
		
		try {
			response.setHeader("Content-disposition",
					"attachment; filename="
							+ new String(pdfName.getBytes("gb2312"),
									"iso8859-1"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		
		FileInputStream in = new  FileInputStream(request.getRealPath("/")+"img/"+pdfName);
		
		OutputStream out = response.getOutputStream();
		
		byte buffer[] = new byte[1024];
		int len = 0;
		while((len = in.read(buffer))>0){
			out.write(buffer,0,len);
		}
		in.close();
		out.close();
		
	}

	@Override
	public String originalCreate(String tableName,HttpServletRequest request){
		
		OriginalData originalData = new OriginalData();
		
		if(tableName==null||tableName.equals("")){
			tableName = String.valueOf(new Timestamp(new Date().getTime())).substring(0,19);
		}
		originalData.setOriginalDataName(tableName);
		originalData.setLogin(new Timestamp(new Date().getTime()));
		
		originalData = originalDataDao.originalTableCreate(originalData);
		
		request.getSession().setAttribute("newOriginalData",  originalData);
		
		return originalData.getOriginalDataId().toString();
	}

	@Override
	public void getOriginalTable(String dataId, HttpServletRequest request) {
		
		OriginalData originalData = new OriginalData();
		
		originalData = originalDataDao.getOriginalTable(dataId);
		
		request.getSession().setAttribute("newOriginalData",  originalData);
		
//		request.getSession().setAttribute("OriginalDataName", originalData.getOriginalDataName());
//		request.getSession().setAttribute("OriginalDataId", originalData.getOriginalDataId());
		
	}

	@Override
	public String getOriginalTableList(String originalBeginDate, String originalEndDate, String originalName) {

		
		if((originalBeginDate==null||originalBeginDate.equals(""))&&(originalEndDate.equals("")||originalEndDate==null)
				&&(originalName.equals("")||originalName==null)){
			
			if(originalBeginDate==null||originalBeginDate.equals("")){
				originalBeginDate =String.valueOf(new Timestamp(new Date().getTime())).substring(0,10); 
			}
			
			if(originalEndDate.equals("")||originalEndDate==null){
				originalEndDate =String.valueOf(new Timestamp(new Date().getTime())).substring(0,10); 
			}
			
		}
		
		List<OriginalData> originalDataList = originalDataDao.getOriginalTableList(originalBeginDate, originalEndDate, 
				originalName);
		
		StringBuffer stringBuffer = new StringBuffer();
		for(OriginalData data :originalDataList){
			stringBuffer.append("<tr><td style='display:none'>");
			stringBuffer.append(data.getOriginalDataId());
			stringBuffer.append("</td><td>");
			stringBuffer.append(data.getOriginalDataName());
			stringBuffer.append("</td><td>");
			stringBuffer.append(data.getLogin().toString().substring(0, 19));
			stringBuffer.append("</td><td>");
			stringBuffer.append("<a class='getODId'>选择</a></td></tr>");
			stringBuffer.append("</td></tr>");
		}
		
		return stringBuffer.toString();
	}

	@Override
	public String updateOraiginalTableTitle(String originalDataId, String tableTitle, 
								HttpServletRequest request) {
		
		OriginalData originalData = originalDataDao.updateOraiginalTableTitle(originalDataId, 
				tableTitle.substring(0, tableTitle.length()-2));
		
		request.getSession().setAttribute("newOriginalData",  originalData);
		System.out.println(originalData.getOriginalDataId().toString());
		return originalData.getOriginalDataId().toString();
	}

	@Override
	public String updateOraiginalTableOne(String originalDataId, String tableOne, HttpServletRequest request) {
	
		OriginalData originalData = originalDataDao.updateOraiginalTableOne(originalDataId, 
				tableOne.substring(0, tableOne.length()-2));
		
		request.getSession().setAttribute("newOriginalData",  originalData);
		
		return originalData.getOriginalDataId().toString();
	}

	@Override
	public String updateOraiginalTableTwo(String originalDataId, String tableTwo, HttpServletRequest request) {
		
		OriginalData originalData = originalDataDao.updateOraiginalTwo(originalDataId, 
				tableTwo.substring(0, tableTwo.length()-2));
		
		request.getSession().setAttribute("newOriginalData",  originalData);
		
		return originalData.getOriginalDataId().toString();
	}

	@Override
	public String updateOraiginalTableThree(String originalDataId, String tableThree, HttpServletRequest request) {

		OriginalData originalData = originalDataDao.updateOraiginalTableThree(originalDataId, 
				tableThree.substring(0, tableThree.length()-2));
		
		request.getSession().setAttribute("newOriginalData",  originalData);
		
		return originalData.getOriginalDataId().toString();
	}

	@Override
	public String updateOraiginalTableFour(String originalDataId, String tableFour, HttpServletRequest request) {

		OriginalData originalData = originalDataDao.updateOraiginalTableFour(originalDataId, 
				tableFour.substring(0, tableFour.length()-2));
		
		request.getSession().setAttribute("newOriginalData",  originalData);
		
		return originalData.getOriginalDataId().toString();
	}

	@Override
	public String updateOraiginalTableFive(String originalDataId, String tableFive, HttpServletRequest request) {

		OriginalData originalData = originalDataDao.updateOraiginalTableFive(originalDataId, 
				tableFive.substring(0, tableFive.length()-2));
		
		request.getSession().setAttribute("newOriginalData",  originalData);
		
		return originalData.getOriginalDataId().toString();
	}

	@Override
	public String updateOraiginalTableSix(String originalDataId, String tableSix, HttpServletRequest request) {

		OriginalData originalData = originalDataDao.updateOraiginalTableSix(originalDataId, 
				tableSix.substring(0, tableSix.length()-2));
		
		request.getSession().setAttribute("newOriginalData",  originalData);
		
		return originalData.getOriginalDataId().toString();
	}

	@Override
	public String updateOraiginalTableSeven(String originalDataId, String tableSeven, HttpServletRequest request) {

		OriginalData originalData = originalDataDao.updateOraiginalTableSeven(originalDataId, 
				tableSeven.substring(0, tableSeven.length()-2));
		
		request.getSession().setAttribute("newOriginalData",  originalData);
		
		return originalData.getOriginalDataId().toString();
	}

	@Override
	public String updateOraiginalTableEight(String originalDataId, String tableEight, HttpServletRequest request) {

		OriginalData originalData = originalDataDao.updateOraiginalTableEight(originalDataId, 
				tableEight.substring(0, tableEight.length()));
		
		request.getSession().setAttribute("newOriginalData",  originalData);
		
		return originalData.getOriginalDataId().toString();
	}

	@Override
	public String updateOraiginalTableNine(String originalDataId, String tableNine, HttpServletRequest request) {

		OriginalData originalData = originalDataDao.updateOraiginalTableNine(originalDataId, 
				tableNine.substring(0, tableNine.length()));
		
		request.getSession().setAttribute("newOriginalData",  originalData);
		
		return originalData.getOriginalDataId().toString();
	}
	
	
	@Override
	public HistoryInfo getSpecifyOraiginalData(String detectionId, String recordTime) {
		
		
		return originalDataDao.getSpecifyOraiginalData(detectionId, recordTime);
		
	}

	@Override
	public RealTimeInfo getSpecifyOraiginalDataFromRealtime(String detectionId, String recordTime) {
		
		
		return originalDataDao.getSpecifyOraiginalDataFromRealtime(detectionId, recordTime);
	}

	@Override
	public String updateOraiginalUIOptions(String originalDataId, String options, HttpServletRequest request) {
		
		OriginalData originalData = originalDataDao.updateOraiginalUIOptions(originalDataId, options);
		
		request.getSession().setAttribute("newOriginalData",  originalData);
		
		return originalData.getOriginalDataId().toString();
	}

	@Override
	public List<Object[]> matchTableHeadInfo(String tableHeadInfo,String modelId) {
		
		List<Object[]> lists = originalDataDao.matchTableHeadInfo(tableHeadInfo,modelId);
		List<String> strBool = new ArrayList<>();
		List<Object[]> result = new ArrayList<>(); 
		
		if(!tableHeadInfo.trim().equals("")){
			for(Object[] tmp : lists){
				String [] strArr = tmp[0].toString().split("#@");
				if(strArr!=null){
					if(!strArr[0].trim().equals("")){
						String strTmp =  String.valueOf(strArr[0]);
						if(!strBool.contains(strTmp)&&strArr[0].indexOf(tableHeadInfo)>-1){
							strBool.add(strTmp);
							result.add(tmp);
						}
					}
				}
			}
			lists = new ArrayList<>();
			lists.addAll(result);
		}
		if(!modelId.trim().equals("")){
			System.out.println("modelId:" + modelId);
			result = new ArrayList<>(); 
			for(Object[] tmp : lists){
				String [] strArr = tmp[0].toString().split("#@");
				if(strArr!=null){
					if(!strArr[4].trim().equals("")){
						String strTmp = String.valueOf(strArr[4]);
						if(!strBool.contains(strTmp)&&strArr[4].indexOf(modelId.trim())>-1){
							strBool.add(strTmp);
							result.add(tmp);
						}
					}
				}
			}
		}
		return result;
	}

	@Override
	public void html2Excel(String dataId, HttpServletResponse response,String excelName) throws Exception{
		
		  WritableWorkbook book=null;
		  response.reset();
		  OutputStream os =null;
		  OriginalData data = originalDataDao.getOriginalTable(dataId);
//		  WritableCellFormat headerFormat = new WritableCellFormat( HEADER_FONT_STYLE);
		  System.out.println("excelName:"+excelName);
		  WritableFont font1 = new WritableFont(WritableFont.ARIAL,15,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.GRAY_80); 
		  WritableCellFormat cellFormat1 = new WritableCellFormat(font1);  
		  cellFormat1.setAlignment(Alignment.CENTRE);
		  cellFormat1.setVerticalAlignment(VerticalAlignment.CENTRE);  
		  
		  WritableFont shoukongF = new WritableFont(WritableFont.ARIAL,9,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.GRAY_80); 
		  WritableCellFormat shoukongW = new WritableCellFormat(shoukongF);  
		  shoukongW.setAlignment(Alignment.RIGHT);
		  shoukongW.setVerticalAlignment(VerticalAlignment.CENTRE);
		  
		  
		  WritableFont font2 = new WritableFont(WritableFont.ARIAL,9,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.GRAY_80); 
		  WritableCellFormat cellFormat2 = new WritableCellFormat(font2);  
		  cellFormat2.setAlignment(Alignment.CENTRE);
		  cellFormat2.setVerticalAlignment(VerticalAlignment.CENTRE); 
		  
		  WritableCellFormat subTitle = new WritableCellFormat(font2);  
		  subTitle.setAlignment(Alignment.CENTRE);
		  subTitle.setVerticalAlignment(VerticalAlignment.CENTRE); 
		  subTitle.setBorder(Border.ALL, BorderLineStyle.THIN);
		  subTitle.setWrap(true);
		  
		  WritableFont font3 = new WritableFont(WritableFont.ARIAL,9,WritableFont.NO_BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.GRAY_80); 
		  WritableCellFormat cellFormat3 = new WritableCellFormat(font3);  
		  cellFormat3.setAlignment(Alignment.CENTRE);
		  cellFormat3.setVerticalAlignment(VerticalAlignment.CENTRE); 
		  cellFormat3.setBorder(Border.ALL, BorderLineStyle.THIN);
		  
		  WritableCellFormat title = new WritableCellFormat(font3);  
		  title.setAlignment(Alignment.CENTRE);
		  title.setVerticalAlignment(VerticalAlignment.CENTRE); 
		  title.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
		  
		  
		  WritableFont font4 = new WritableFont(WritableFont.ARIAL,12,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.GRAY_80); 
		  WritableCellFormat cellFormat4 = new WritableCellFormat(font4);  
		  cellFormat4.setVerticalAlignment(VerticalAlignment.CENTRE);
		  cellFormat4.setWrap(true);
		  
		  if(excelName.trim().equals("")){
				String str = String.valueOf(new Timestamp(new Date().getTime()));
				excelName = str.substring(0,4)+str.substring(5,7)+str.substring(8,10)+str.substring(11,13)
				+str.substring(14,16)+str.substring(17,19);
			}
		   // 设置弹出对话框
		  response.setContentType("application/DOWLOAD");
		   // 设置工作表的标题
		  excelName = excelName + ".xls";
		  response.setHeader("Content-Disposition",
		     "attachment; filename=" + new String(excelName.getBytes("gb2312"),"iso8859-1"));//设置生成的文件名字
		  os = response.getOutputStream();
		 
		  // 初始化工作表
		  book = Workbook.createWorkbook(os);
		 
		  WritableSheet sheet = book.createSheet("婴儿培养箱", 0);
		   // 生成名为"商品信息"的工作表，参数0表示这是第一页
		  
		  
		  sheet.getSettings().getHeader().getRight().append(" ");
		  
		  for(int i = 0;i<13;i++){
			  sheet.setColumnView(i, 6);
		  }
		  for(int i = 0;i<230;i++){
			  sheet.setRowView(i, 300);
		  }
		  
		  
		  int lineNum = 0;
//		  Label label = new Label(0, lineNum, "普洱市质量技术监督综合检测中心",cellFormat1);  
//		  sheet.addCell(label);  
//		  sheet.mergeCells(0, lineNum, 12, lineNum+1); 
//		  lineNum = lineNum + 2;
		  Label label = new Label(0,lineNum, "婴儿培养箱校准记录",cellFormat1);  
		  sheet.addCell(label);  
		  sheet.mergeCells(0, lineNum, 12, lineNum+1	); 
		  lineNum = lineNum + 2;
		  
		  String [] list = data.getAutograph().split("#@");
		  if(list.length>2){
			  if(!list[2].trim().equals("")){
				  sheet.addCell(new Label(8, lineNum, "证书编号："+list[2],title));
				  sheet.mergeCells(8, lineNum, 12, lineNum);
				  sheet.mergeCells(0, lineNum, 7, lineNum++);
			  }
		  }
		  
		  //表头数据填写 lineNum = 3
		  list = data.getTitleInfo().split("#@");
		  label = new Label(0, lineNum, "委托单位：",subTitle);  
		  sheet.addCell(label);
		  sheet.mergeCells(0, lineNum, 2, lineNum); 
		  sheet.addCell(new Label(3, lineNum, list[0],cellFormat3));
		  sheet.mergeCells(3, lineNum, 12, lineNum++); 
		  sheet.addCell(new Label(0, lineNum, "型号规格：",subTitle));
		  sheet.mergeCells(0, lineNum, 2, lineNum); 
		  sheet.addCell(new Label(3, lineNum, list[3],cellFormat3));
		  sheet.mergeCells(3, lineNum, 6, lineNum);  
		  sheet.addCell(new Label(7, lineNum, "校准日期：",subTitle));
		  sheet.mergeCells(7, lineNum, 8, lineNum); 
		  sheet.addCell(new Label(9, lineNum, list[5],cellFormat3));
		  sheet.mergeCells(9, lineNum, 12, lineNum++); 
		  sheet.addCell(new Label(0, lineNum, "制造厂：",subTitle));
		  sheet.mergeCells(0, lineNum, 2, lineNum); 
		  sheet.addCell(new Label(3, lineNum, list[2],cellFormat3));
		  sheet.mergeCells(3, lineNum, 6, lineNum);
		  sheet.addCell(new Label(7, lineNum, "检定环境：",subTitle));
		  sheet.mergeCells(7, lineNum, 8, lineNum);
		  sheet.addCell(new Label(9, lineNum, list[7]+"℃",cellFormat3));
		  sheet.mergeCells(9, lineNum, 10, lineNum);
		  sheet.addCell(new Label(11, lineNum, list[8]+" %RH",cellFormat3));
		  sheet.mergeCells(11, lineNum, 12, lineNum++);
		  sheet.addCell(new Label(0, lineNum, "出厂编号：",subTitle));
		  sheet.mergeCells(0, lineNum, 2, lineNum);
		  sheet.addCell(new Label(3, lineNum, list[4],cellFormat3));
		  sheet.mergeCells(3, lineNum, 6, lineNum);
		  sheet.addCell(new Label(7, lineNum, "校准依据：",subTitle));
		  sheet.mergeCells(7, lineNum, 8, lineNum); 
		  sheet.addCell(new Label(9, lineNum, list[9],cellFormat3));
		  sheet.mergeCells(9, lineNum, 12, lineNum++);
		  
		  sheet.addCell(new Label(0, lineNum, "标准器名称：",subTitle));
		  sheet.mergeCells(0, lineNum, 2, lineNum);
		  sheet.addCell(new Label(3, lineNum, list[10],cellFormat3));
		  sheet.mergeCells(3, lineNum, 6, lineNum);
		  sheet.addCell(new Label(7, lineNum, "校准器证书号：",subTitle));
		  sheet.mergeCells(7, lineNum, 8, lineNum); 
		  sheet.addCell(new Label(9, lineNum, list[13],cellFormat3));
		  sheet.mergeCells(9, lineNum, 12, lineNum++);
		  
		  sheet.addCell(new Label(0, lineNum, "标准编号：",subTitle));
		  sheet.mergeCells(0, lineNum, 2, lineNum);
		  sheet.addCell(new Label(3, lineNum, list[15],cellFormat3));
		  sheet.mergeCells(3, lineNum, 6, lineNum);
		  sheet.addCell(new Label(7, lineNum, "有效期至：",subTitle));
		  sheet.mergeCells(7, lineNum, 8, lineNum); 
		  sheet.addCell(new Label(9, lineNum, list[16],cellFormat3));
		  sheet.mergeCells(9, lineNum, 12, lineNum++);
		  
		  
		  sheet.mergeCells(0, lineNum, 12, lineNum++);
		  
		  //外观、报警功能及电器安全检查 lineNum = 9
		  list = data.getTableOne().split("#@");
		  sheet.addCell(new Label(0, lineNum, "  外观、报警功能及电器安全检查",cellFormat4));
		  sheet.mergeCells(0, lineNum, 12, lineNum+1);
		  lineNum = lineNum + 2;
		  sheet.addCell(new Label(0, lineNum, "检查项目",subTitle));
		  sheet.mergeCells(0, lineNum, 6, lineNum); 
		  sheet.addCell(new Label(7, lineNum, "测量值",subTitle));
		  sheet.mergeCells(7, lineNum, 12, lineNum++); 
		  sheet.addCell(new Label(0, lineNum, "外观",subTitle));
		  sheet.mergeCells(0, lineNum, 6, lineNum); 
		  sheet.addCell(new Label(7, lineNum, (list[0].trim().equals("0"))?"符合":(list[0].trim().equals("1")?"不符合":""),cellFormat3));
		  sheet.mergeCells(7, lineNum, 12, lineNum++); 
		  sheet.addCell(new Label(0, lineNum, "患者电流/μA",subTitle));
		  sheet.mergeCells(0, lineNum, 3, lineNum+1); 
		  sheet.addCell(new Label(4, lineNum, "直流",subTitle));
		  sheet.mergeCells(4, lineNum, 6, lineNum); 
		  sheet.addCell(new Label(7, lineNum, list[4],cellFormat3));
		  sheet.mergeCells(7, lineNum, 12, lineNum++); 
		  sheet.addCell(new Label(4, lineNum, "交流",subTitle));
		  sheet.mergeCells(4, lineNum, 6, lineNum); 
		  sheet.addCell(new Label(7, lineNum, list[5],cellFormat3));
		  sheet.mergeCells(7, lineNum, 12, lineNum++); 
		  sheet.addCell(new Label(0, lineNum, "机壳漏电流/μA",subTitle));
		  sheet.mergeCells(0, lineNum, 6, lineNum); 
		  sheet.addCell(new Label(7, lineNum, list[6],cellFormat3));
		  sheet.mergeCells(7, lineNum, 12, lineNum++); 
		  sheet.addCell(new Label(0, lineNum, "接地电阻/Ω",subTitle));
		  sheet.mergeCells(0, lineNum, 6, lineNum); 
		  sheet.addCell(new Label(7, lineNum, list[7],cellFormat3));
		  sheet.mergeCells(7, lineNum, 12, lineNum++); 
		  sheet.mergeCells(0, lineNum, 12, lineNum++);
		  
		  //温度校准记录 lineNum = 18
		  sheet.addCell(new Label(0, lineNum, "  温度校准记录",cellFormat4));
		  sheet.mergeCells(0, lineNum, 10, lineNum+1); 
		  sheet.addCell(new Label(11, lineNum, "单位：℃",cellFormat2));
		  sheet.mergeCells(11, lineNum, 12, lineNum+1); 
		  lineNum = lineNum + 2;
		  sheet.addCell(new Label(0, lineNum, "次数",subTitle));
		  sheet.mergeCells(0, lineNum, 0, lineNum+2); 
		  sheet.addCell(new Label(1, lineNum, "控制温度",subTitle));
		  sheet.mergeCells(1, lineNum, 3, lineNum); 
		  sheet.addCell(new Label(4, lineNum, "32",subTitle));
		  sheet.mergeCells(4, lineNum, 6, lineNum);
		  sheet.addCell(new Label(7, lineNum, "控制温度",subTitle));
		  sheet.mergeCells(7, lineNum, 9, lineNum);
		  sheet.addCell(new Label(10, lineNum, "36",subTitle));
		  sheet.mergeCells(10, lineNum, 12, lineNum++);
		  sheet.addCell(new Label(1, lineNum, "显示\n温度",subTitle));
		  sheet.mergeCells(1, lineNum, 1, lineNum+1);
		  sheet.addCell(new Label(2, lineNum, "温度",subTitle));
		  sheet.mergeCells(2, lineNum, 6, lineNum);
		  sheet.addCell(new Label(7, lineNum, "显示\n温度",subTitle));
		  sheet.mergeCells(7, lineNum, 7, lineNum+1);
		  sheet.addCell(new Label(8, lineNum, "温度",subTitle));
		  sheet.mergeCells(8, lineNum, 12, lineNum++);
		  sheet.addCell(new Label(2, lineNum, "A",subTitle));
		  sheet.mergeCells(2, lineNum, 2, lineNum);
		  sheet.addCell(new Label(3, lineNum, "B",subTitle));
		  sheet.mergeCells(3, lineNum, 3, lineNum);
		  sheet.addCell(new Label(4, lineNum, "C",subTitle));
		  sheet.mergeCells(4, lineNum, 4, lineNum);
		  sheet.addCell(new Label(5, lineNum, "D",subTitle));
		  sheet.mergeCells(5, lineNum, 5, lineNum);
		  sheet.addCell(new Label(6, lineNum, "E",subTitle));
		  sheet.mergeCells(6, lineNum, 6, lineNum);
		  sheet.addCell(new Label(8, lineNum, "A",subTitle));
		  sheet.mergeCells(8, lineNum, 8, lineNum);
		  sheet.addCell(new Label(9, lineNum, "B",subTitle));
		  sheet.mergeCells(9, lineNum, 9, lineNum);
		  sheet.addCell(new Label(10, lineNum, "C",subTitle));
		  sheet.mergeCells(10, lineNum, 10, lineNum);
		  sheet.addCell(new Label(11, lineNum, "D",subTitle));
		  sheet.mergeCells(11, lineNum, 11, lineNum);
		  sheet.addCell(new Label(12, lineNum, "E",subTitle));
		  sheet.mergeCells(12, lineNum, 12, lineNum++);
		  
		  list = data.getTableTwo().split("#@");
		  for(int i=0;i<15;i++){
		    sheet.addCell(new Label(0, lineNum, String.valueOf(i+1),subTitle));
		    sheet.addCell(new Label(1, lineNum, list[i*12 + 0],cellFormat3));
		    sheet.addCell(new Label(2, lineNum, list[i*12 + 1],cellFormat3));
		    sheet.addCell(new Label(3, lineNum, list[i*12 + 2],cellFormat3));
		    sheet.addCell(new Label(4, lineNum, list[i*12 + 3],cellFormat3));
		    sheet.addCell(new Label(5, lineNum, list[i*12 + 4],cellFormat3));
		    sheet.addCell(new Label(6, lineNum, list[i*12 + 5],cellFormat3));
		    sheet.addCell(new Label(7, lineNum, list[i*12 + 6],cellFormat3));
		    sheet.addCell(new Label(8, lineNum, list[i*12 + 7],cellFormat3));
		    sheet.addCell(new Label(9, lineNum, list[i*12 + 8],cellFormat3));
		    sheet.addCell(new Label(10, lineNum, list[i*12 + 9],cellFormat3));
		    sheet.addCell(new Label(11, lineNum, list[i*12 + 10],cellFormat3));
		    sheet.addCell(new Label(12, lineNum++, list[i*12 + 11],cellFormat3));
		  }
		  sheet.addCell(new Label(0, lineNum, "修正值",subTitle));
	      sheet.addCell(new Label(1, lineNum, "/",cellFormat3));
	      sheet.addCell(new Label(2, lineNum, list[180],cellFormat3));
	      sheet.addCell(new Label(3, lineNum, list[181],cellFormat3));
	      sheet.addCell(new Label(4, lineNum, list[182],cellFormat3));
	      sheet.addCell(new Label(5, lineNum, list[183],cellFormat3));
	      sheet.addCell(new Label(6, lineNum, list[184],cellFormat3));
	      sheet.addCell(new Label(7, lineNum, "/",cellFormat3));
	      sheet.addCell(new Label(8, lineNum, list[185],cellFormat3));
	      sheet.addCell(new Label(9, lineNum, list[186],cellFormat3));
	      sheet.addCell(new Label(10, lineNum, list[187],cellFormat3));
	      sheet.addCell(new Label(11, lineNum, list[188],cellFormat3));
	      sheet.addCell(new Label(12, lineNum++, list[189],cellFormat3));
	      
	      sheet.addCell(new Label(0, lineNum, "平均值",subTitle));
	      sheet.addCell(new Label(1, lineNum, list[190],cellFormat3));
	      sheet.addCell(new Label(2, lineNum, list[191],cellFormat3));
	      sheet.addCell(new Label(3, lineNum, list[192],cellFormat3));
	      sheet.addCell(new Label(4, lineNum, list[193],cellFormat3));
	      sheet.addCell(new Label(5, lineNum, list[194],cellFormat3));
	      sheet.addCell(new Label(6, lineNum, list[195],cellFormat3));
	      sheet.addCell(new Label(7, lineNum, list[196],cellFormat3));
	      sheet.addCell(new Label(8, lineNum, list[197],cellFormat3));
	      sheet.addCell(new Label(9, lineNum, list[198],cellFormat3));
	      sheet.addCell(new Label(10, lineNum, list[199],cellFormat3));
	      sheet.addCell(new Label(11, lineNum, list[200],cellFormat3));
	      sheet.addCell(new Label(12, lineNum++, list[201],cellFormat3));
	      
	      sheet.addCell(new Label(0, lineNum, "温度偏差",subTitle));
	      sheet.mergeCells(0, lineNum, 1, lineNum+1);
	      sheet.addCell(new Label(2, lineNum, "32",subTitle));
	      sheet.mergeCells(2, lineNum, 3, lineNum);
	      sheet.addCell(new Label(4, lineNum, list[202],cellFormat3));
	      sheet.mergeCells(4, lineNum, 6, lineNum);
	      sheet.addCell(new Label(2, lineNum+1, "36",subTitle));
	      sheet.mergeCells(2, lineNum+1, 3, lineNum+1);
	      sheet.addCell(new Label(4, lineNum+1, list[204],cellFormat3));
	      sheet.mergeCells(4, lineNum+1, 6, lineNum+1);
	      sheet.addCell(new Label(7, lineNum, "温度波动度",subTitle));
	      sheet.mergeCells(7, lineNum, 8, lineNum+1);
	      sheet.addCell(new Label(9, lineNum, "32",subTitle));
	      sheet.mergeCells(9, lineNum, 10, lineNum);
	      sheet.addCell(new Label(11, lineNum, list[203],cellFormat3));
	      sheet.mergeCells(11, lineNum, 12, lineNum++);
	      sheet.addCell(new Label(9, lineNum, "36",subTitle));
	      sheet.mergeCells(9, lineNum, 10, lineNum);
	      sheet.addCell(new Label(11, lineNum, list[205],cellFormat3));
	      sheet.mergeCells(11, lineNum, 12, lineNum++);
	      
	      sheet.addCell(new Label(0, lineNum, "温度均匀度",subTitle));
	      sheet.mergeCells(0, lineNum, 1, lineNum+1);
	      sheet.addCell(new Label(2, lineNum, "32",subTitle));
	      sheet.mergeCells(2, lineNum, 3, lineNum);
	      sheet.addCell(new Label(4, lineNum, list[206],cellFormat3));
	      sheet.mergeCells(4, lineNum, 6, lineNum);
	      sheet.addCell(new Label(2, lineNum+1, "36",subTitle));
	      sheet.mergeCells(2, lineNum+1, 3, lineNum+1);
	      sheet.addCell(new Label(4, lineNum+1, list[207],cellFormat3));
	      sheet.mergeCells(4, lineNum+1, 6, lineNum+1);
	      sheet.addCell(new Label(7, lineNum, "平均培养箱温度与控制温度之差",subTitle));
	      sheet.mergeCells(7, lineNum, 10, lineNum+1);
	      sheet.addCell(new Label(11, lineNum, list[208],cellFormat3));
	      sheet.mergeCells(11, lineNum++, 12, lineNum++);
	      
	      sheet.addCell(new Label(0, lineNum, "调整控制温度后,测得培养箱温度最大值",subTitle));
	      sheet.mergeCells(0, lineNum, 2, lineNum+1);
	      sheet.addCell(new Label(3, lineNum, list[209],cellFormat3));
	      sheet.mergeCells(3, lineNum, 6, lineNum+1);
	      sheet.addCell(new Label(7, lineNum, "温度超调量",subTitle));
	      sheet.mergeCells(7, lineNum, 9, lineNum+1);
	      sheet.addCell(new Label(10, lineNum, list[210],cellFormat3));
	      sheet.mergeCells(10, lineNum++,12, lineNum++);
	      
	      //温度校准记录 lineNum = 47
		  list = data.getTableThree().split("#@");
		  sheet.addCell(new Label(0, lineNum, "  床垫倾斜时温度均匀度",cellFormat4));
		  sheet.mergeCells(0, lineNum, 10, lineNum+1); 
		  sheet.addCell(new Label(11, lineNum, "单位：℃",cellFormat2));
		  sheet.mergeCells(11, lineNum++, 12, lineNum++); 
	      
		  sheet.addCell(new Label(0, lineNum, "次数",subTitle));
	      sheet.mergeCells(0, lineNum, 2, lineNum+1);
	      sheet.addCell(new Label(3, lineNum, "控制温度",subTitle));
	      sheet.mergeCells(3, lineNum, 6, lineNum);
	      sheet.addCell(new Label(7, lineNum, "32",subTitle));
	      sheet.mergeCells(7, lineNum, 12, lineNum++);
	      sheet.addCell(new Label(3, lineNum, "A",subTitle));
	      sheet.mergeCells(3, lineNum, 4, lineNum);
	      sheet.addCell(new Label(5, lineNum, "B",subTitle));
	      sheet.mergeCells(5, lineNum, 6, lineNum);
	      sheet.addCell(new Label(7, lineNum, "C",subTitle));
	      sheet.mergeCells(7, lineNum, 8, lineNum);
	      sheet.addCell(new Label(9, lineNum, "D",subTitle));
	      sheet.mergeCells(9, lineNum, 10, lineNum);
	      sheet.addCell(new Label(11, lineNum, "E",subTitle));
	      sheet.mergeCells(11, lineNum, 12, lineNum++);
	      
	      for(int i=0;i<15;i++){
		    sheet.addCell(new Label(0, lineNum, String.valueOf(i+1),subTitle));
		    sheet.mergeCells(0, lineNum, 2, lineNum);
		    sheet.addCell(new Label(3, lineNum, list[i*6 + 1],cellFormat3));
		    sheet.mergeCells(3, lineNum, 4, lineNum);
		    sheet.addCell(new Label(5, lineNum, list[i*6 + 2],cellFormat3));
		    sheet.mergeCells(5, lineNum, 6, lineNum);
		    sheet.addCell(new Label(7, lineNum, list[i*6 + 3],cellFormat3));
		    sheet.mergeCells(7, lineNum, 8, lineNum);
		    sheet.addCell(new Label(9, lineNum, list[i*6 + 4],cellFormat3));
		    sheet.mergeCells(9, lineNum, 10, lineNum);
		    sheet.addCell(new Label(11, lineNum, list[i*6 + 5],cellFormat3));
		    sheet.mergeCells(11, lineNum, 12, lineNum++);
		  }
	      sheet.addCell(new Label(0, lineNum, "修正值",subTitle));
	      sheet.mergeCells(0, lineNum, 2, lineNum);
	      sheet.addCell(new Label(3, lineNum, list[90],cellFormat3));
	      sheet.mergeCells(3, lineNum, 4, lineNum);
	      sheet.addCell(new Label(5, lineNum, list[91],cellFormat3));
	      sheet.mergeCells(5, lineNum, 6, lineNum);
	      sheet.addCell(new Label(7, lineNum, list[92],cellFormat3));
	      sheet.mergeCells(7, lineNum, 8, lineNum);
	      sheet.addCell(new Label(9, lineNum, list[93],cellFormat3));
	      sheet.mergeCells(9, lineNum, 10, lineNum);
	      sheet.addCell(new Label(11, lineNum, list[94],cellFormat3));
	      sheet.mergeCells(11, lineNum, 12, lineNum++);
	      
	      sheet.addCell(new Label(0, lineNum, "平均值",subTitle));
	      sheet.mergeCells(0, lineNum, 2, lineNum);
	      sheet.addCell(new Label(3, lineNum, list[95],cellFormat3));
	      sheet.mergeCells(3, lineNum, 4, lineNum);
	      sheet.addCell(new Label(5, lineNum, list[96],cellFormat3));
	      sheet.mergeCells(5, lineNum, 6, lineNum);
	      sheet.addCell(new Label(7, lineNum, list[97],cellFormat3));
	      sheet.mergeCells(7, lineNum, 8, lineNum);
	      sheet.addCell(new Label(9, lineNum, list[98],cellFormat3));
	      sheet.mergeCells(9, lineNum, 10, lineNum);
	      sheet.addCell(new Label(11, lineNum, list[99],cellFormat3));
	      sheet.mergeCells(11, lineNum, 12, lineNum++);
	      
	      sheet.addCell(new Label(0, lineNum, "床垫倾斜时温度均匀度",subTitle));
	      sheet.mergeCells(0, lineNum, 4, lineNum);
	      sheet.addCell(new Label(5, lineNum, list[100],cellFormat3));
	      sheet.mergeCells(5, lineNum, 6, lineNum);
	      sheet.addCell(new Label(7, lineNum, list[101],cellFormat3));
	      sheet.mergeCells(7, lineNum, 8, lineNum);
	      sheet.addCell(new Label(9, lineNum, list[102],cellFormat3));
	      sheet.mergeCells(9, lineNum, 10, lineNum);
	      sheet.addCell(new Label(11, lineNum, list[103],cellFormat3));
	      sheet.mergeCells(11, lineNum, 12, lineNum++);
	      
	      //湿度相对偏差 lineNum = 70
		  list = data.getTableFour().split("#@");
		  sheet.addCell(new Label(0, lineNum, "  湿度相对偏差",cellFormat4));
		  sheet.mergeCells(0, lineNum++, 12, lineNum++); 
	      
		  sheet.addCell(new Label(0, lineNum, "次数",subTitle));
	      sheet.mergeCells(0, lineNum, 4, lineNum);
	      sheet.addCell(new Label(5, lineNum, "1",subTitle));
	      sheet.mergeCells(5, lineNum, 6, lineNum); 
	      sheet.addCell(new Label(7, lineNum, "2",subTitle));
	      sheet.mergeCells(7, lineNum, 8, lineNum); 
	      sheet.addCell(new Label(9, lineNum, "3",subTitle));
	      sheet.mergeCells(9, lineNum, 10, lineNum); 
	      sheet.addCell(new Label(11, lineNum, "平均值",subTitle));
	      sheet.mergeCells(11, lineNum, 12, lineNum++); 
	      sheet.addCell(new Label(0, lineNum, "显示湿度/%RH",subTitle));
	      sheet.mergeCells(0, lineNum, 4, lineNum);
	      sheet.addCell(new Label(5, lineNum, list[0],cellFormat3));
	      sheet.mergeCells(5, lineNum, 6, lineNum); 
	      sheet.addCell(new Label(7, lineNum, list[1],cellFormat3));
	      sheet.mergeCells(7, lineNum, 8, lineNum); 
	      sheet.addCell(new Label(9, lineNum, list[2],cellFormat3));
	      sheet.mergeCells(9, lineNum, 10, lineNum); 
	      sheet.addCell(new Label(11, lineNum, list[3],cellFormat3));
	      sheet.mergeCells(11, lineNum, 12, lineNum++); 
	      sheet.addCell(new Label(0, lineNum, "实测湿度/%RH",subTitle));
	      sheet.mergeCells(0, lineNum, 4, lineNum);
	      sheet.addCell(new Label(5, lineNum, list[4],cellFormat3));
	      sheet.mergeCells(5, lineNum, 6, lineNum); 
	      sheet.addCell(new Label(7, lineNum, list[5],cellFormat3));
	      sheet.mergeCells(7, lineNum, 8, lineNum); 
	      sheet.addCell(new Label(9, lineNum, list[6],cellFormat3));
	      sheet.mergeCells(9, lineNum, 10, lineNum); 
	      sheet.addCell(new Label(11, lineNum, list[7],cellFormat3));
	      sheet.mergeCells(11, lineNum, 12, lineNum++); 
	      sheet.addCell(new Label(0, lineNum, "相对湿度偏差/%RH",subTitle));
	      sheet.mergeCells(0, lineNum, 4, lineNum);
	      sheet.addCell(new Label(5, lineNum, list[8],cellFormat3));
	      sheet.mergeCells(5, lineNum, 12, lineNum++); 
	      
	      //婴儿舱内氧分析器示值误差 row = 77
		  list = data.getTableFive().split("#@");
		  sheet.addCell(new Label(0, lineNum, "  婴儿舱内氧分析器示值误差",cellFormat4));
		  sheet.mergeCells(0, lineNum++, 12, lineNum++); 
	      
		  sheet.addCell(new Label(0, lineNum, "次数",subTitle));
	      sheet.mergeCells(0, lineNum, 4, lineNum);
	      sheet.addCell(new Label(5, lineNum, "1",subTitle));
	      sheet.mergeCells(5, lineNum, 6, lineNum); 
	      sheet.addCell(new Label(7, lineNum, "2",subTitle));
	      sheet.mergeCells(7, lineNum, 8, lineNum); 
	      sheet.addCell(new Label(9, lineNum, "3",subTitle));
	      sheet.mergeCells(9, lineNum, 10, lineNum); 
	      sheet.addCell(new Label(11, lineNum, "平均值",subTitle));
	      sheet.mergeCells(11, lineNum, 12, lineNum++); 
	      sheet.addCell(new Label(0, lineNum, "显示氧体积分数/%",subTitle));
	      sheet.mergeCells(0, lineNum, 4, lineNum);
	      sheet.addCell(new Label(5, lineNum, list[0],cellFormat3));
	      sheet.mergeCells(5, lineNum, 6, lineNum); 
	      sheet.addCell(new Label(7, lineNum, list[1],cellFormat3));
	      sheet.mergeCells(7, lineNum, 8, lineNum); 
	      sheet.addCell(new Label(9, lineNum, list[2],cellFormat3));
	      sheet.mergeCells(9, lineNum, 10, lineNum); 
	      sheet.addCell(new Label(11, lineNum, list[3],cellFormat3));
	      sheet.mergeCells(11, lineNum, 12, lineNum++); 
	      sheet.addCell(new Label(0, lineNum, "氧标准气体的体积分数/%",subTitle));
	      sheet.mergeCells(0, lineNum, 4, lineNum);
	      sheet.addCell(new Label(5, lineNum, list[4],cellFormat3));
	      sheet.mergeCells(5, lineNum, 12, lineNum++); 
	      sheet.addCell(new Label(0, lineNum, "氧分析器示值误差/%",subTitle));
	      sheet.mergeCells(0, lineNum, 4, lineNum);
	      sheet.addCell(new Label(5, lineNum, list[5],cellFormat3));
	      sheet.mergeCells(5, lineNum, 12, lineNum++); 
	      
	      // 婴儿舱内的误差 row = 84
		  list = data.getTableSix().split("#@");
		  sheet.addCell(new Label(0, lineNum, "  婴儿舱内的误差",cellFormat4));
		  sheet.mergeCells(0, lineNum++, 12, lineNum++); 
		  sheet.addCell(new Label(0, lineNum, "婴儿舱内的噪声/dB",subTitle));
	      sheet.mergeCells(0, lineNum, 4, lineNum);
	      sheet.addCell(new Label(5, lineNum, "1",subTitle));
	      sheet.addCell(new Label(6, lineNum, list[0],cellFormat3));
	      sheet.addCell(new Label(7, lineNum, "2",subTitle));
	      sheet.addCell(new Label(8, lineNum, list[1],cellFormat3));
	      sheet.addCell(new Label(9, lineNum, "3",subTitle));
	      sheet.addCell(new Label(10, lineNum, list[2],cellFormat3));
	      sheet.addCell(new Label(11, lineNum, "平均值",subTitle));
	      sheet.addCell(new Label(12, lineNum++, list[3],cellFormat3));
	      
	      
	      //报警器报警噪声 row = 88
		  list = data.getTableSeven().split("#@");
		  sheet.addCell(new Label(0, lineNum, "  报警器报警噪声",cellFormat4));
		  sheet.mergeCells(0, lineNum++, 12, lineNum++); 
		  
		  sheet.addCell(new Label(0, lineNum, "婴儿舱内的噪声/dB",subTitle));
	      sheet.mergeCells(0, lineNum, 4, lineNum);
	      sheet.addCell(new Label(5, lineNum, "1",subTitle));
	      sheet.addCell(new Label(6, lineNum, list[0],cellFormat3));
	      sheet.addCell(new Label(7, lineNum, "2",subTitle));
	      sheet.addCell(new Label(8, lineNum, list[1],cellFormat3));
	      sheet.addCell(new Label(9, lineNum, "3",subTitle));
	      sheet.addCell(new Label(10, lineNum, list[2],cellFormat3));
	      sheet.addCell(new Label(11, lineNum, "平均值",subTitle));
	      sheet.addCell(new Label(12, lineNum++, list[3],cellFormat3));
	      
	      sheet.addCell(new Label(0, lineNum, "箱外噪声/dB",subTitle));
	      sheet.mergeCells(0, lineNum, 4, lineNum);
	      sheet.addCell(new Label(5, lineNum, "1",subTitle));
	      sheet.addCell(new Label(6, lineNum, list[4],cellFormat3));
	      sheet.addCell(new Label(7, lineNum, "2",subTitle));
	      sheet.addCell(new Label(8, lineNum, list[5],cellFormat3));
	      sheet.addCell(new Label(9, lineNum, "3",subTitle));
	      sheet.addCell(new Label(10, lineNum, list[6],cellFormat3));
	      sheet.addCell(new Label(11, lineNum, "平均值",subTitle));
	      sheet.addCell(new Label(12, lineNum++, list[7],cellFormat3));
	      
	      
	      //报警器报警噪声 row = 93
		  list = data.getTableEight().split("#@");
		  sheet.addCell(new Label(0, lineNum, "  备注",cellFormat4));
		  sheet.mergeCells(0, lineNum, 12, lineNum++);
		  
		  sheet.addCell(new Label(0, lineNum, list[0],cellFormat3));
		  sheet.mergeCells(0, lineNum, 12, lineNum+2);
		  lineNum = lineNum +3;
		  
		  //标准信息 row = 99
		  list = data.getTitleInfo().split("#@");
		  
		  sheet.addCell(new Label(0, lineNum, "  等级（不确定度）",cellFormat4));
		  sheet.mergeCells(0, lineNum, 12, lineNum++);
		  
		  sheet.addCell(new Label(0, lineNum, "等级（不确定度）：",subTitle));
		  sheet.mergeCells(0, lineNum, 2, lineNum); 
		  sheet.addCell(new Label(3, lineNum, list[14],cellFormat3));
		  sheet.mergeCells(3, lineNum, 12, lineNum++); 

		  //签名 row = 107
		  list = data.getAutograph().split("#@");
		  sheet.mergeCells(0, lineNum, 1, lineNum);
		  sheet.addCell(new Label(2, lineNum, "校准员:",cellFormat2));
		  sheet.addCell(new Label(3, lineNum, list[0],title));
		  sheet.mergeCells(3, lineNum, 5, lineNum);
		  sheet.mergeCells(6, lineNum, 6, lineNum);
		  sheet.addCell(new Label(7, lineNum, "核验员:",cellFormat2));
		  sheet.addCell(new Label(8, lineNum, list[1],title));
		  sheet.mergeCells(8, lineNum, 10, lineNum);
		  sheet.mergeCells(11, lineNum, 12, lineNum);

		  book.write();
		  book.close();
		 }
}
