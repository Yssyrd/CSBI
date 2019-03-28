package com.nt.csbi.services.imp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nt.csbi.dao.HistoryInfoDao;
import com.nt.csbi.entities.HistoryInfo;
import com.nt.csbi.services.FileUploaderService;

/**
 * @author 杨润东
 *
 * @date: 2017年4月19日 下午8:49:29
 */
@Service
public class FileUploaderServiceImp implements FileUploaderService {

	@Autowired
	private HistoryInfoDao historyInfoDao;
	
	@Override
	public String uploader(MultipartFile mtpFile,String path) throws Exception {
		
		String result = "0";
		
		Date date = new Date();
		//注意format的格式要与日期String的格式相匹配
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		
		
		List<HistoryInfo> historyInfolist = new ArrayList<HistoryInfo>();

		String encoding = "GBK";
//		
//		File logoSaveFile = new File(path);
//		if (!logoSaveFile.exists())
//	         logoSaveFile.mkdirs();
//		
//		path += File.separator + UUID.randomUUID().toString() + ".txt";
		
		File file = new File(path);
		
//		mtpFile.transferTo(file);
		
		try {
			
			InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
			BufferedReader bufferedReader = new BufferedReader(read);

			String lineTxt = null;
			lineTxt = bufferedReader.readLine();
			int i = 0;
			while ((lineTxt = bufferedReader.readLine()) != null) {
				
				HistoryInfo historyInfo = new HistoryInfo();
				i++;
				String[] sourceStrArray = lineTxt.split(";");
				if (sourceStrArray.length != 21) {
					
				} else {
					try {
						date = sdf.parse(sourceStrArray[0]);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					if(dataFormat(sourceStrArray)&&dataCharge(sourceStrArray)){
						historyInfo.setRecordTime(new Timestamp(date.getTime()));
						historyInfo.setAT(dataDoubleFormat(sourceStrArray[1]));
						historyInfo.setBT(dataDoubleFormat(sourceStrArray[2]));
						historyInfo.setCT(dataDoubleFormat(sourceStrArray[3]));
						historyInfo.setDT(dataDoubleFormat(sourceStrArray[4]));
						historyInfo.setET(dataDoubleFormat(sourceStrArray[5]));
						historyInfo.setAF(dataDoubleFormat(sourceStrArray[6]));
						historyInfo.setAG(dataDoubleFormat(sourceStrArray[7]));
						historyInfo.setO2(dataDoubleFormat(sourceStrArray[8]));
						historyInfo.setAngleX(dataIntFormat(sourceStrArray[9]));
						historyInfo.setAngleY(dataIntFormat(sourceStrArray[10]));
						historyInfo.setAngleZ(dataIntFormat(sourceStrArray[11]));
						historyInfo.setDIST(dataDoubleFormat(sourceStrArray[12]));
						historyInfo.setCONT(dataDoubleFormat(sourceStrArray[13]));
						historyInfo.setDISRH(dataDoubleFormat(sourceStrArray[14]));
						historyInfo.setNOISE(dataDoubleFormat(sourceStrArray[15]));
						historyInfo.setINNOIS(dataDoubleFormat(sourceStrArray[16]));
						historyInfo.setOUTNOI(dataDoubleFormat(sourceStrArray[17]));
						historyInfo.setFLOW(dataDoubleFormat(sourceStrArray[18]));
						historyInfo.setInspectedId(dataIntFormat(sourceStrArray[19]));
						historyInfo.setDetectionId(dataIntFormat(sourceStrArray[20]));
						historyInfo.setInputDate(new Timestamp(new Date().getTime()));
						historyInfolist.add(historyInfo);
					}
				}
				result = "0";
			}
			historyInfoDao.saveHistoryInfo(historyInfolist);
			System.out.println(historyInfolist.size());
			read.close();
			System.out.println(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String dataDoubleFormat(String data) {
		
		Double dataStr = Double.valueOf(data);
		
		DecimalFormat df = new DecimalFormat("######0.00");   
		data = df.format(dataStr); 
		
		return data;

	}

	@Override
	public String dataIntFormat(String data) {
		
		return String.valueOf(Integer.valueOf(data));

	}

	@Override
	public boolean dataFormat(String[] str) {
		
		for(int i = 1; i < str.length; i++){
			
			try {  
	            Double num=Double.valueOf(str[i]);//把字符串强制转换为数字   
	        } catch (Exception e) {  
	            return false;//如果抛出异常，返回False  
	            
	        }
		}
		return true;
	}

	@Override
	public boolean dataCharge(String[] str) {
		
		for(int i = 1; i < 6; i++){
			
			try {  
	            Double num=Double.valueOf(str[i]);//把字符串强制转换为数字  
	            System.out.println(str[i]);
	            if(num>80.0||num<10.0){
	            	return false;
	            }
	            
	        } catch (Exception e) {  
	            return false;//如果抛出异常，返回False  
	            
	        }
		}
		if(Double.valueOf(str[6])>100.0||Double.valueOf(str[6])<0.0){
			return false;
		}
		System.out.println(str[6]);
		return true;
	}
	
}
