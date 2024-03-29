# Usage

### Storage definitions
```java
public class UserAvatarChineseS3Storage implements S3Storage {
    private final S3Client s3Client;

    public UserAvatarChineseS3Storage(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public S3Client getS3Client() {
        return this.s3Client;
    }

    @Override
    public String getKeyPrefix() {
        return "avatars/";
    }

    @Override
    public String getStorageName() {
        return "avatars";
    }

    @Override
    public String getStorageRegion() {
        return "China";
    }

    @Override
    public String getBucketName() {
        return "user-content";
    }

    @Override
    public String getEndpoint() {
        return "https://s3.test.com";
    }
}
```

### Bean configuration
```java
@Import({SpringS3BeanConfiguration.class})
public class S3ConfigurationBean {
    @Bean
    public UserAvatarChineseS3Storage userAvatarChineseS3Storage()
    {
        return new UserAvatarChineseS3Storage(this.createChineseS3Client());
    }
    
    private S3Client createChineseS3Client()
    {
        return S3Client.builder()
                .region(Region.of("cn-north-1"))
                .credentialsProvider(() -> AwsBasicCredentials.create("AccessKeyId", "AccessKeySecret"))
                .serviceConfiguration(
                        software.amazon.awssdk.services.s3.S3Configuration.builder()
                                .chunkedEncodingEnabled(false)
                                .build()
                )
                .endpointOverride(URI.create(this.endpoint))
                .build();
    }    
}
```

### Usage
```java
public class FileUploader {
    private final S3Service s3Service;
    
    public FileUploader(S3Service s3Service)
    {
        this.s3Service = s3Service;
    }
    
    public void uploadFile()
    {
        return this.s3Service.uploadFile(
                "China",
                "avatars",
                "TWFuIGlzIGRpc3Rpbmd1aXNoZWQsIG5vdCBvbmx5IGJ5IGhpcyByZWFzb24sIGJ1dCAuLi4=",
                "test.txt"
        );
    }
}
```

# Publishing to remote repository
`./gradlew clean sonatypeCentralUpload`