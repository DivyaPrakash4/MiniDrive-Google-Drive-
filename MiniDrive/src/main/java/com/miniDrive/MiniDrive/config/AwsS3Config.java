package com.miniDrive.MiniDrive.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AwsS3Config {

    @Value("${aws.access-key:}")
    private String accessKey;

    @Value("${aws.secret-key:}")
    private String secretKey;

    @Value("${aws.region:ap-south-1}")
    private String region;

    @Bean
    public S3Client s3Client() {
        AwsCredentialsProvider credentialsProvider;

        // If explicitly provided via application.properties / env (e.g. for local testing)
        if (accessKey != null && !accessKey.isBlank() && !accessKey.contains("YOUR_AWS_ACCESS_KEY")
                && secretKey != null && !secretKey.isBlank() && !secretKey.contains("YOUR_AWS_SECRET_KEY")) {
            credentialsProvider = StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(accessKey.trim(), secretKey.trim())
            );
        } else {
            // AWS IAM Role (Instance Profile on EC2) or AWS CLI credentials / AWS environment variables
            credentialsProvider = DefaultCredentialsProvider.create();
        }

        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(credentialsProvider)
                .build();
    }
}
