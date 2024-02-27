package s3.storages;

import s3.s3.S3Storage;
import software.amazon.awssdk.services.s3.S3Client;

public class TestS3Storage implements S3Storage {
    private final S3Client s3Client;
    private final String keyPrefix;
    private final String storageName;
    private final String storageRegion;
    private final String bucketName;
    private final String endpoint;

    public TestS3Storage(
            S3Client s3Client,
            String keyPrefix,
            String storageName,
            String storageRegion,
            String bucketName,
            String endpoint
    ) {
        this.s3Client = s3Client;
        this.keyPrefix = keyPrefix;
        this.storageName = storageName;
        this.storageRegion = storageRegion;
        this.bucketName = bucketName;
        this.endpoint = endpoint;
    }

    @Override
    public S3Client getS3Client() {
        return this.s3Client;
    }

    @Override
    public String getKeyPrefix() {
        return this.keyPrefix;
    }

    @Override
    public String getStorageName() {
        return this.storageName;
    }

    @Override
    public String getStorageRegion() {
        return this.storageRegion;
    }

    @Override
    public String getBucketName() {
        return this.bucketName;
    }

    @Override
    public String getPublicEndpoint() {
        return this.endpoint;
    }
}
