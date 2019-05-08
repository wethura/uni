package edu.uni;

import edu.uni.labManagement.mapper.DeviceMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UniApplicationTests {

    @Autowired
    private DeviceMapper deviceMapper;

    @Test
    public void contextLoads() throws IOException {

    }
}
