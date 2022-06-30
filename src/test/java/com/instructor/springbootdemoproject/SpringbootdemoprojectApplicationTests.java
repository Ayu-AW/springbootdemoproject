package com.instructor.springbootdemoproject;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@DataJpaTest
class SpringbootdemoprojectApplicationTests {

    @Test
    @Disabled("Coming soon...")
    void contextLoads() {
        fail("no tests yet.");
    }

}
