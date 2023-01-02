package com.arvind.fileprocessing.service.Impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.arvind.fileprocessing.service.FileUtilService;
import com.arvind.fileprocessing.util.SheetCompare;
import com.arvind.fileprocessing.util.SheetGenerate;
import com.arvind.fileprocessing.util.SheetReader;

@Service
public class FileUtilServiceImpl implements FileUtilService {

	@Value("${uploadDir}")
	private String UPLOAD_DIR;

	@Override
	public ResponseEntity<String> uploadFiles(MultipartFile[] files) {
		String message = "";
		try {
			List<String> fileNames = new ArrayList<>();

			Arrays.asList(files).stream().forEach(file -> {
				try {
					file.transferTo(new File(UPLOAD_DIR + file.getOriginalFilename()));
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
				fileNames.add(file.getOriginalFilename());
			});

			message = "Uploaded the files successfully: " + fileNames;
			return new ResponseEntity<>(message, HttpStatus.OK);
		} catch (Exception e) {
			message = "Fail to upload files!";
			return new ResponseEntity<>(message, HttpStatus.EXPECTATION_FAILED);
		}
	}

	@Override
	public ResponseEntity<byte[]> downloadFiles(String filename) {
		byte[] fileData = null;
		try {
			fileData = Files.readAllBytes(new File(UPLOAD_DIR + filename).toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);

		return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> refreshFiles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<String> deleteFiles(String filename) {
		String message = null;
		try {
			boolean isdeleted = Files.deleteIfExists(Paths.get(UPLOAD_DIR + filename));
			message = "Deleted files successfully: " + filename;
			System.err.println(isdeleted + "testing ");
			return new ResponseEntity<>(message, HttpStatus.OK);
		} catch (IOException e) {
			message = "Something went wrong: ";
			return new ResponseEntity<>(message, HttpStatus.EXPECTATION_FAILED);
		}
	}

	@Override
	public ResponseEntity<String> deleteAllFiles() {
		String message = null;
		try {
			FileUtils.cleanDirectory(new File(UPLOAD_DIR));
			message = "Deleted files successfully: ";
			return new ResponseEntity<>(message, HttpStatus.OK);
		} catch (IOException e) {
			message = "something went wrong: ";
			return new ResponseEntity<>(message, HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<String> generateFile(String filename) {

		SheetGenerate sheetObj = new SheetGenerate();
		sheetObj.sheetCreater(UPLOAD_DIR + filename);
		String message = "sheet created: " + filename;
		return new ResponseEntity<>(message, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> readFiles(String filename) {
		SheetReader read = new SheetReader();
		read.readExcelSheet(UPLOAD_DIR + filename);
		String message = "file read successful: ";
		return new ResponseEntity<>(message, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> downloadAllFiles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<List<String>> getAllFiles() {
		List<String> results = new ArrayList<String>();

		File[] files = new File(UPLOAD_DIR).listFiles();
		// If this pathname does not denote a directory, then listFiles() returns null.

		for (File file : files) {
			if (file.isFile()) {
				results.add(file.getName());
			}
		}
		return new ResponseEntity<>(results, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> sheetCompare(String path1, String path2) {
		SheetCompare compareObj = new SheetCompare();
		compareObj.sheetComparision(UPLOAD_DIR+path1, UPLOAD_DIR+path2);
		String message = "files compared successfully: ";
		return new ResponseEntity<>(message, HttpStatus.OK);
	}

}
