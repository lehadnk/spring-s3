package s3.s3;

import software.amazon.awssdk.services.s3.S3Client;

public interface S3Storage {
    S3Client getS3Client();
    String getKeyPrefix();
    String getStorageName();
    String getStorageRegion();
    String getBucketName();
    String getPublicEndpoint();
}
