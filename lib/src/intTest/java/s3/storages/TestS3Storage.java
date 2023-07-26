package s3.storages;

import s3.s3.S3Storage;
import software.amazon.awssdk.services.s3.S3Client;

public class TestS3Storage implements S3Storage {
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
        return "EU";
    }
}
