package com.team5.sparta_clone_5.s3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/file")
public class S3Controller {

    @Autowired
    private S3Service s3Service;

    @PostMapping(value = "/upload")
    public ResponseEntity<String> uploadFile(@RequestPart List<MultipartFile> image,
                                             @RequestPart String content) {
        System.out.println(content);
        return new ResponseEntity<>(s3Service.uploadFile(image), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{fileName}")
    public void deleteFile(@PathVariable String fileName) {

    }
}

