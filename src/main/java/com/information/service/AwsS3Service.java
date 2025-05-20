package com.information.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AwsS3Service {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;

    @SneakyThrows
    public String upload(MultipartFile file) {
        if (file.isEmpty() || Objects.isNull(file.getOriginalFilename())) {
            throw new Exception("파일이 비어있음");
        }
        return checkAndUpload(file);
    }

    private String checkAndUpload(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

        String S3UploadFilename = UUID.randomUUID().toString().replace("-", "").substring(0, 16) + "." + ext;

        String url = "";
        try{
            InputStream inputStream = file.getInputStream();
            byte[] bytes = IOUtils.toByteArray(inputStream);

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("image/" + ext);
            metadata.setContentLength(bytes.length);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

            PutObjectRequest request = new PutObjectRequest(
                    bucketName, S3UploadFilename, byteArrayInputStream, metadata);
            amazonS3.putObject(request);

            byteArrayInputStream.close();

            url = amazonS3.getUrl(bucketName,S3UploadFilename).toString();

        }catch (Exception e){
            e.printStackTrace();
        }
        return url;
    }

    public void delete(String key) {
        amazonS3.deleteObject(bucketName, key);
    }
}
