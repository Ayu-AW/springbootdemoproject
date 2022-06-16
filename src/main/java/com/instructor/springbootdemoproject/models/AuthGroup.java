package com.instructor.springbootdemoproject.models;

/*
    Step two: create AuthGroup and link email as shared id with Student class.

 */

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
@Table(name = "auth_group")
@Entity
public class AuthGroup {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @NonNull
    String aEmail;
    @NonNull
    String aAuthGroup;


}
