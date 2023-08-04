package s3.providers;

public class YandexCloudTest extends AbstractS3ProviderTest {
    protected void setUp()
    {
        this.accessKey = System.getenv("YANDEX_ACCESS_KEY_ID");
        this.secretKey = System.getenv("YANDEX_SECRET_KEY_ID");
        this.region = System.getenv("YANDEX_REGION");
        this.publicEndpoint = System.getenv("YANDEX_PUBLIC_ENDPOINT");
        this.serviceEndpoint = System.getenv("YANDEX_SERVICE_ENDPOINT");
        this.bucketName = System.getenv("YANDEX_BUCKET_NAME");
    }
}
