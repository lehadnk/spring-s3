package s3.s3.business;

import s3.s3.dto.UploadFileResult;
import s3.s3.dto.factory.UploadFileResultFactory;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.util.List;

public class FileManager {
    private final StorageContainer storageContainer;
    private final PathConcatenator pathConcatenator;

    public FileManager(
            StorageContainer storageContainer,
            PathConcatenator pathConcatenator
    ) {
        this.storageContainer = storageContainer;
        this.pathConcatenator = pathConcatenator;
    }

    public UploadFileResult uploadFile(String region, String storageName, byte[] fileContents, String fileName) {
        var storage = this.storageContainer.getStorage(region, storageName);
        var key = this.pathConcatenator.concatPaths(storage.getKeyPrefix(), fileName);

        var request = PutObjectRequest.builder()
                .bucket(storage.getBucketName())
                .key(key)
                .build();

        var body = RequestBody.fromBytes(fileContents);

        storage.getS3Client().putObject(request, body);
        return UploadFileResultFactory.success(this.pathConcatenator.concatPaths(storage.getEndpoint(), key));
    }

    public List<S3Object> listFiles(String region, String storageName)
    {
        var storage = this.storageContainer.getStorage(region, storageName);
        var request = ListObjectsV2Request.builder()
                .bucket(storage.getBucketName())
                .prefix(storage.getKeyPrefix())
                .build();

        var response = storage.getS3Client().listObjectsV2(request);

        return response.contents()
                .stream()
                .filter((object) -> object.key().startsWith(storage.getKeyPrefix()))
                .toList();
    }

    public void deleteFile(String region, String storageName, String fileName)
    {
        var storage = this.storageContainer.getStorage(region, storageName);
        var key = this.pathConcatenator.concatPaths(storage.getKeyPrefix(), fileName);

        var request = DeleteObjectRequest.builder()
                .bucket(storage.getBucketName())
                .key(key)
                .build();

        storage.getS3Client().deleteObject(request);
    }
}
