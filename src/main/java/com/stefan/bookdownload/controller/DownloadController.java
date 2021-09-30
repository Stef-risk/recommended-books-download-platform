package com.stefan.bookdownload.controller;

import com.stefan.bookdownload.utils.MediaTypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Controller
public class DownloadController {
    private static final String DIRECTORY="D:/PDF";
    private static final String DEFAULT_FILENAME="java.pdf";

    @Autowired
    private ServletContext servletContext;

    @RequestMapping("download")
    public ResponseEntity<InputStreamResource> downloadFlie(
            @RequestParam(defaultValue = DEFAULT_FILENAME)String filename
    ) throws FileNotFoundException {
        MediaType mediaType= MediaTypeUtils.getMediaTypeForFileName(servletContext,filename);
        System.out.println("filename:"+filename);
        System.out.println("media type:"+mediaType);

        File file=new File(DIRECTORY+"/"+filename);
        InputStreamResource source=new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename="+file.getName())
                .contentType(mediaType)
                .contentLength(file.length())
                .body(source);
    }
}
