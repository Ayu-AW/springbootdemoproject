package com.instructor.springbootdemoproject.data;

import com.instructor.springbootdemoproject.models.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileInfoRepository extends JpaRepository<FileInfo, Integer> {
}
