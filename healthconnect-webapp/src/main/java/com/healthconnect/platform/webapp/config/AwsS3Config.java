package com.healthconnect.platform.webapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AwsS3Config {
	
	@Value("${healthconnect.aws.keyId}")
	private String awsKeyId;

	@Value("${healthconnect.aws.accessKey}")
	private String accessKey;

	@Value("${healthconnect.aws.region}")
	private String region;

	@Bean
	public AmazonS3 awsS3Client() {
		BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsKeyId, accessKey);
		return AmazonS3ClientBuilder.standard().withRegion(Regions.fromName(region))
				.withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();
	}

}
