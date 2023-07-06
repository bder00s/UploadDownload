package com.example.download_upload.controller;

import com.example.download_upload.Repository.FileRepository;
import com.example.download_upload.model.File;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
public class FileController {

//    @PostMapping("/single/uploadeDb")
//    public <FileUploadResponse> ResponseEntity<FileUploadResponse> singleFileUpload(@RequestParam("file") MultipartFile file) { }

    private FileRepository fileRepository;


    public FileController(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @PostMapping("/single/uploadDb")
    public ResponseEntity<String> singleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {

        File uploadfile = new File();
        uploadfile.setFilename(file.getOriginalFilename());
        uploadfile.setDocfile(file.getBytes());

        fileRepository.save(uploadfile);
        return ResponseEntity.ok("file geüpload: " + uploadfile.getFilename());
    }


    @GetMapping("/downloadFromDb/{fileId}")
    public ResponseEntity<byte[]> downloadSingleFile(@PathVariable Long fileId) {
        File file = fileRepository.findById(fileId).orElseThrow(() -> new RuntimeException("Id from file is not found"));
        byte[] uploadedFiles = file.getDocfile();

        if (uploadedFiles == null) {
            throw new RuntimeException("there is no file yet.");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "file" + file.getFilename() + ".pdf");
        headers.setContentLength(uploadedFiles.length);
        return new ResponseEntity<>(uploadedFiles, headers, HttpStatus.OK);

    }






}
