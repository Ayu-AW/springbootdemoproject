package com.instructor.springbootdemoproject.controllers;

import com.instructor.springbootdemoproject.data.FileInfoRepository;
import com.instructor.springbootdemoproject.models.FileInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller @Slf4j
public class FileController {
    //private final String UPLOAD_DIR = "~/IdeaProjects/springbootdemoproject/files";
    @Value("${app.upload.dir:${user.home}}")
    public String uploadDir;
    private final FileInfoRepository fileInfoRepository;
    @Autowired
    public FileController(FileInfoRepository fileInfoRepository) {
        this.fileInfoRepository = fileInfoRepository;
    }

    @GetMapping("/uploadfileform")
    public String uploadFileForm(){
        return "fileupload";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file")MultipartFile file, RedirectAttributes redirectAttributes){

        //check if file is empty
        if(file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/uploadfileform";
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());



        try{

            Path path = Paths.get(uploadDir + File.separator + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            fileInfoRepository.save(new FileInfo(fileName, uploadDir+ File.separator + fileName, file.getContentType(), String.valueOf(file.getSize())));

        }catch (IOException ex){
            log.warn("Upload file exception, file name: " + fileName);
            ex.printStackTrace();
        }
        redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + fileName + '!');
        return "redirect:/uploadfileform";
    }
}
