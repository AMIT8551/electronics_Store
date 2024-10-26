package com.electronics.store.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

	
	public String uploadImage(MultipartFile file,String path,String name,String UserId) throws IOException;
	
	public InputStream getResource(String path,String imageName) throws FileNotFoundException;
	
	public void deleteresource(String fullpath);
}
