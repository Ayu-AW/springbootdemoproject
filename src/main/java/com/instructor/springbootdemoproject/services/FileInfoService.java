package com.instructor.springbootdemoproject.services;

import com.instructor.springbootdemoproject.data.FileInfoRepository;
import com.instructor.springbootdemoproject.models.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class FileInfoService {

    private FileInfoRepository fileInfoRepository;

    @Autowired
    public FileInfoService(FileInfoRepository fileInfoRepository) {
        this.fileInfoRepository = fileInfoRepository;
    }

    public FileInfo store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileInfo fileInfo = new FileInfo(fileName, file.getContentType(),file.getSize(),file.getBytes());
        return fileInfoRepository.save(fileInfo);
    }

    public FileInfo getFile(int id) throws NoSuchElementException {
        return fileInfoRepository.findById(id).orElseThrow();

    }

    public List<FileInfo> getAllFiles(){
        return fileInfoRepository.findAll();
    }
}
