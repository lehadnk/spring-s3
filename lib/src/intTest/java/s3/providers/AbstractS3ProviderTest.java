package s3.providers;

import org.junit.jupiter.api.Test;
import s3.s3.S3Service;
import s3.s3.S3Storage;
import s3.s3.business.FileManager;
import s3.s3.business.PathConcatenator;
import s3.s3.business.StorageContainer;
import s3.storages.TestS3Storage;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public abstract class AbstractS3ProviderTest {
    protected String region;
    protected String accessKey;
    protected String secretKey;
    protected String bucketName;
    protected String publicEndpoint;
    protected String serviceEndpoint;

    private S3Client s3Client;
    private S3Service s3Service;
    private List<S3Storage> testS3Storages;

    protected abstract void setUp();

    @Test
    public void testLibraryCode() throws IOException {
        this.setUp();

        assertNotNull(this.accessKey);
        assertNotNull(this.secretKey);
        assertNotNull(this.region);
        assertNotNull(this.publicEndpoint);

        this.createS3Client();
        this.createTestS3Storage();
        this.createS3Service();

        var fileUploadResult = this.s3Service.uploadFile(
                "EU",
                "avatars",
                "TWFuIGlzIGRpc3Rpbmd1aXNoZWQsIG5vdCBvbmx5IGJ5IGhpcyByZWFzb24sIGJ1dCAuLi4=",
                "test.txt"
        );

        assertEquals(true, fileUploadResult.isSuccess);
        assertNotNull(fileUploadResult.fileUrl);

        var responseCode = this.getUrlStatusCode(fileUploadResult.fileUrl);
        assertEquals(200, responseCode);

        this.s3Service.deleteFile("EU", "avatars", "test.txt");

        responseCode = this.getUrlStatusCode(fileUploadResult.fileUrl);
        assertEquals('4', Integer.toString(responseCode).charAt(0));
    }

    private void createTestS3Storage() {
        this.testS3Storages = List.of(
                new TestS3Storage(
                        this.s3Client,
                        "intlTest/",
                        "avatars",
                        "EU",
                        this.bucketName,
                        this.publicEndpoint
                )
        );
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
                .endpointOverride(URI.create(this.serviceEndpoint))
                .build();
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

    private int getUrlStatusCode(String urlString) throws IOException {
        var url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        return connection.getResponseCode();
    }
}
