package s3.s3;

import s3.s3.business.FileManager;
import s3.s3.dto.UploadFileResult;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.util.Base64;
import java.util.List;

public class S3Service {
    private final FileManager fileManager;

    public S3Service(
            FileManager fileManager
    ) {
        this.fileManager = fileManager;
    }

    public UploadFileResult uploadFile(String region, String storageName, byte[] fileContents, String fileName) {
        return this.fileManager.uploadFile(region, storageName, fileContents, fileName);
    }

    public UploadFileResult uploadFile(String region, String contentType, String base64EncodedFileContents, String fileName) {
        return this.uploadFile(
                region,
                contentType,
                Base64.getDecoder().decode(base64EncodedFileContents.getBytes()),
                fileName
        );
    }

    public List<S3Object> listFiles(String region, String storageName) {
        return this.fileManager.listFiles(region, storageName);
    }

    public void deleteFile(String region, String storageName, String fileName) {
        this.fileManager.deleteFile(region, storageName, fileName);
    }
}
