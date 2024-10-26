package com.electronics.store.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.electronics.store.exceptions.ImageFormatNotSupportException;
import com.electronics.store.exceptions.ImageNotFoundException;
import com.electronics.store.services.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(MultipartFile file, String path, String name, String userId) throws IOException {

		// abc.jpg
		String originalFilename = file.getOriginalFilename();

		// name=Amit
		// UserId=123456789

		// "Amit"+"_"+"6789"
		String filename = null;

		if (userId.length() > 4) {
			filename = name.replace(" ", "_") + "_" + userId.substring(userId.length() - 4);
		} else {
			filename = name.replace(" ", "_") + "_" + String.format("%04d", Integer.parseInt(userId));
		}

		// .jpg from abc.jpg
		String extension = originalFilename.substring(originalFilename.indexOf("."));

		// Amit_6789.jpg

		String filenameWithExtension = filename + extension;

		// path with filename and extension also

		String pathWithFilename = path + filenameWithExtension;

		if (extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg")
				|| extension.equalsIgnoreCase(".jpeg")) {

			File folder = new File(path);

			if (!folder.exists()) {
				folder.mkdirs();
				;
			}

			/*
			 * The copy() method in the Files class is used to copy data from an input
			 * source Files.copy() reads data from the InputStream (file's content) and
			 * writes it to the destination specified by the Path
			 * 
			 * file is a MultipartFile object, which represents the uploaded file in
			 * Spring.The method getInputStream() returns an InputStream for the file
			 * content InputStream will contain the raw data of that image.
			 * 
			 * he Paths.get() method converts the given string into a Path object.
			 */

			Files.copy(file.getInputStream(), Paths.get(pathWithFilename));

			return filenameWithExtension;

		} else {
			throw new ImageFormatNotSupportException(
					"This Image Format Not Supported : " + extension + "-->" + " Required Format .png,.jpg,.jpeg");
		}

	}

	@Override
	public InputStream getResource(String path, String imageName) throws FileNotFoundException {

		String fullpath = path + File.separator + imageName;

		InputStream inputStream = new FileInputStream(fullpath);

		return inputStream;
	}

	@Override
	public void deleteresource(String fullpath) {

		File abc = new File(fullpath);

		if (!abc.exists()) {

			throw new ImageNotFoundException("Image not Found in class path ");
		}

		Path path = Paths.get(fullpath);

		try {
			Files.delete(path);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
