package s3.s3;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;

public class S3Service {
    private final HashMap<String, HashMap<String, S3Storage>> s3Storages = new HashMap<>();

    public S3Service(
            List<S3Storage> s3Storages
    ) {
        for (var storage : s3Storages) {
            if (!this.s3Storages.containsKey(storage.getStorageRegion())) {
                this.s3Storages.put(storage.getStorageRegion(), new HashMap<>());
            }

            this.s3Storages.get(storage.getStorageRegion()).put(storage.getStorageName(), storage);
        }
    }

    public void uploadFile(String region, String contentType, String base64EncodedFileContents, String fileName) {
        this.uploadFile(
                region,
                contentType,
                Base64.getDecoder().decode(base64EncodedFileContents.getBytes()),
                fileName
        );
    }

    public void uploadFile(String region, String storageName, byte[] fileContents, String fileName) {
        var request = PutObjectRequest.builder()
                .bucket("default")
                .key(fileName)
                .build();

        var body = RequestBody.fromBytes(fileContents);

        this.s3Storages.get(region).get(storageName).getS3Client().putObject(request, body);
    }
}
