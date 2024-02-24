package com.onetuks.dayonetest.service;

import com.onetuks.dayonetest.IntegrationTest;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

class S3ServiceTest extends IntegrationTest {

    @Autowired
    private S3Service s3Service;

    @Test
    void s3PutAndGetTest() throws Exception {
        // Given
        String bucket = "test-bucket";
        String key = "sampleObject.xt";
        File sampleFile =new ClassPathResource("static/sample.txt").getFile();

        // When
        s3Service.putFile(bucket, key, sampleFile);

        // Then
        File resultFile = s3Service.getFile(bucket, key);

        List<String> sampleFileLines = FileUtils.readLines(sampleFile);
        List<String> resultFileLines = FileUtils.readLines(resultFile);

//        System.out.println("==========================");
//        System.out.println(Arrays.toString(sampleFileLines.toArray()));
//        System.out.println(Arrays.toString(resultFileLines.toArray()));
//        System.out.println("==========================");

        Assertions.assertIterableEquals(sampleFileLines, resultFileLines);
    }

}
