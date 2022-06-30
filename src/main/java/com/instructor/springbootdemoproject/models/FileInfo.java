package com.instructor.springbootdemoproject.models;


import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Slf4j
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "files")
@Entity
public class FileInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @NonNull
    String fileName;
    @NonNull
    String fileType;
    @NonNull
    long fileSize;
    @NonNull
    @Lob
    byte[] data;

}
