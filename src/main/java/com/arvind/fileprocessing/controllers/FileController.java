package com.arvind.fileprocessing.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.arvind.fileprocessing.service.FileUtilService;

@RestController
public class FileController {

	@Autowired
	FileUtilService fileService;

	@PostMapping("/upload")
	public ResponseEntity<String> upload(@RequestParam("file") MultipartFile[] files) throws IllegalStateException {
		return fileService.uploadFiles(files);
	}

	@GetMapping("/download/{fileName}")
	public ResponseEntity<?> download(@PathVariable("fileName") String fileName) {
		return fileService.downloadFiles(fileName);

	}
	
	@DeleteMapping("/delete/{fileName}")
	public ResponseEntity<?> delete(@PathVariable("fileName") String fileName) {
		return fileService.deleteFiles(fileName);
	}
	
	@DeleteMapping("/deleteAll")
	public ResponseEntity<?> deleteAllFiles(){
		return fileService.deleteAllFiles();
	}
	
	@GetMapping("/fileRead/{fileName}")
	public ResponseEntity<?> readFile(@PathVariable("fileName") String fileName){
		return fileService.readFiles(fileName);
	}
	
	@GetMapping("/fileCreate/{fileName}")
	public ResponseEntity<?> createFile(@PathVariable("fileName") String fileName){
		return fileService.generateFile(fileName);
	}
	
	@GetMapping("/getAllFiles")
	public ResponseEntity<?> getAllFiles(){
		return fileService.getAllFiles();
	}
	
	@GetMapping("/compareSheets")
	public ResponseEntity<?> compareSheets(@RequestParam("file1") String file1,@RequestParam("file2") String file2){
		return fileService.sheetCompare(file1, file2);
	}

}
