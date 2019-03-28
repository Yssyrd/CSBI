package com.nt.csbi.services;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author ����
 *
 * @date: 2017��4��19�� ����8:46:47
 */
public interface FileUploaderService {
	
	public String uploader(MultipartFile file,String path) throws Exception ;

	public String dataDoubleFormat(String data);

	public String dataIntFormat(String data);
	
	public boolean dataFormat(String[] str);
	
	public boolean dataCharge(String[] str);
}
