# Usage

### Storage definitions
```java
@Component
public class UserAvatarChineseS3Storage implements S3Storage {
    private final S3Client s3Client;

    public TestS3Storage(S3Client s3Client) {
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
}
```

### Bean configuration
```java
@Import({S3ConfigurationBean.class})
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
public void uploadFile()
{
    return this.s3Storage.uploadFile(
        "China",
        "avatars",
        "TWFuIGlzIGRpc3Rpbmd1aXNoZWQsIG5vdCBvbmx5IGJ5IGhpcyByZWFzb24sIGJ1dCAuLi4=",
        "test.txt"
    );
}
```