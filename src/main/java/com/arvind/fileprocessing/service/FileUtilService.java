package com.arvind.fileprocessing.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileUtilService {
	
	public ResponseEntity<String> uploadFiles(MultipartFile[] files);
	public ResponseEntity<?> downloadAllFiles();
	public ResponseEntity<?> downloadFiles(String filename);
	public ResponseEntity<?> refreshFiles();
	public ResponseEntity<String> deleteFiles(String filename);
	public ResponseEntity<String> deleteAllFiles();
	public ResponseEntity<String> generateFile(String filename);
	public ResponseEntity<?> readFiles(String name);
	public ResponseEntity<?> getAllFiles();
	public ResponseEntity<?> sheetCompare(String path1,String path2);

}
