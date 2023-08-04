package s3.providers;



public class AwsTest extends AbstractS3ProviderTest {
    protected void setUp()
    {
        this.accessKey = System.getenv("AWS_ACCESS_KEY_ID");
        this.secretKey = System.getenv("AWS_SECRET_KEY_ID");
        this.region = System.getenv("AWS_REGION");
        this.publicEndpoint = System.getenv("AWS_PUBLIC_ENDPOINT");
        this.serviceEndpoint = System.getenv("AWS_SERVICE_ENDPOINT");
        this.bucketName = System.getenv("AWS_BUCKET_NAME");
    }
}
