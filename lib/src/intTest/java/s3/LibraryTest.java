package s3;


import org.junit.Test;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;
import s3.s3.S3Service;
import s3.s3.S3Storage;
import s3.s3.business.FileManager;
import s3.s3.business.PathConcatenator;
import s3.s3.business.StorageContainer;
import s3.storages.TestS3Storage;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;

import java.net.URI;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LibraryTest {
    private String accessKey;
    private String secretKey;
    private String region;
    private String endpoint;
    private S3Client s3Client;
    private List<S3Storage> testS3Storages;
    private S3Service s3Service;

    @Test
    public void test()
    {
        this.startLocalstack();
        this.createS3Client();
        this.createS3Bucket("default");
        this.createTestS3Storage();
        this.createS3Service();

        assertNotNull(this.accessKey);
        assertNotNull(this.secretKey);
        assertNotNull(this.region);
        assertNotNull(this.endpoint);

        var uploadFileResult = this.s3Service.uploadFile(
                "EU",
                "avatars",
                "TWFuIGlzIGRpc3Rpbmd1aXNoZWQsIG5vdCBvbmx5IGJ5IGhpcyByZWFzb24sIGJ1dCAuLi4=",
                "test.txt"
        );

        assertEquals(true, uploadFileResult.isSuccess);
        assertEquals("https://s3.test.com/avatars/test.txt", uploadFileResult.fileUrl);

        var files = this.s3Service.listFiles("EU", "avatars");
        assertEquals(1, files.size());

        this.s3Service.deleteFile("EU", "avatars", "test.txt");
        assertEquals(0, this.s3Service.listFiles("EU", "avatars").size());
    }

    private void createTestS3Storage() {
        this.testS3Storages = List.of(new TestS3Storage(this.s3Client));
    }

    private void startLocalstack() {
        var localstack = new LocalStackContainer(
                DockerImageName.parse("localstack/localstack:0.11.2")
        ).withServices(LocalStackContainer.Service.S3);
        localstack.start();

        this.accessKey = localstack.getAccessKey();
        this.secretKey = localstack.getSecretKey();
        this.region = localstack.getRegion();
        this.endpoint = localstack.getEndpointOverride(LocalStackContainer.Service.S3).toString();
    }

    private void createS3Client()
    {
        this.s3Client = S3Client.builder()
                .region(Region.of(this.region))
                .credentialsProvider(() -> AwsBasicCredentials.create(this.accessKey, this.secretKey))
                .serviceConfiguration(
                        software.amazon.awssdk.services.s3.S3Configuration.builder()
                                .chunkedEncodingEnabled(false)
                                .build()
                )
                .endpointOverride(URI.create(this.endpoint))
                .build();
    }

    private void createS3Bucket(String name)
    {
        var request = CreateBucketRequest.builder()
                .bucket(name)
                .build();

        this.s3Client.createBucket(request);
    }

    private void createS3Service()
    {
        this.s3Service = new S3Service(
                new FileManager(
                        new StorageContainer(this.testS3Storages),
                        new PathConcatenator()
                )
        );
    }
}
