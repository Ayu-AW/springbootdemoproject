package com.instructor.springbootdemoproject.controllers;

import com.instructor.springbootdemoproject.models.FileInfo;
import com.instructor.springbootdemoproject.services.FileInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
public class FileController {
    //private final String UPLOAD_DIR = "~/IdeaProjects/springbootdemoproject/files";
//    @Value("${app.upload.dir:${user.home}}")
//    public String uploadDir;
    private final FileInfoService fileInfoService;

    @Autowired
    public FileController(FileInfoService fileInfoService) {
        this.fileInfoService = fileInfoService;
    }

    // https://codebun.com/spring-boot-upload-and-download-file-example-using-thymeleaf/
    @GetMapping("/uploadfileform")
    public String uploadFileForm(Model model) {
        List<FileInfo> fileInfos = fileInfoService.getAllFiles();
        fileInfos = fileInfos.stream().map(f ->
        {
            return new FileInfo(f.getId(), f.getFileName(), f.getFileType(), (f.getFileSize() / 1024L), f.getData());
        }).collect(Collectors.toList());
        model.addAttribute("files", fileInfos);
        return "fileupload";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

        //check if file is empty
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/uploadfileform";
        }

        FileInfo fileInfo = null;
        try {
            fileInfo = fileInfoService.store(file);
        } catch (IOException ex) {
            log.warn("Upload file exception");
            ex.printStackTrace();
        }
        redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + fileInfo.getFileName() + '!');
        return "redirect:/uploadfileform";
    }


    @GetMapping("/downloadfile")
    public void downloadFile(@Param("id") int id, Model model, HttpServletResponse response) throws IOException {
        FileInfo fileInfo = fileInfoService.getFile(id);


        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename = " + fileInfo.getFileName();
        response.setHeader(headerKey, headerValue);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(fileInfo.getData());
        outputStream.close();

    }

    @GetMapping("/image")
    public void showImage(@Param("id") int id, HttpServletResponse response, FileInfo fileInfo)
            throws ServletException, IOException {

        fileInfo = fileInfoService.getFile(id);
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif, image/pdf");
        response.getOutputStream().write(fileInfo.getData());
        response.getOutputStream().close();
    }

}
