package s3.s3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class S3ServiceBeanConfiguration {
    @Bean
    public S3Service createS3Service(
            List<S3Storage> storages
    ) {
        return new S3Service(storages);
    }
}
