package s3.s3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import s3.s3.business.FileManager;
import s3.s3.business.PathConcatenator;
import s3.s3.business.StorageContainer;

import java.util.List;

@Configuration
public class S3ServiceBeanConfiguration {
    @Bean
    public S3Service createS3Service(
            FileManager fileManager
    ) {
        return new S3Service(
                fileManager
        );
    }

    @Bean
    protected FileManager createFileManager(
            StorageContainer storageContainer,
            PathConcatenator pathConcatenator
    ) {
        return new FileManager(
                storageContainer,
                pathConcatenator
        );
    }

    @Bean
    protected StorageContainer createStorageContainer(
            List<S3Storage> storages
    ) {
        return new StorageContainer(storages);
    }

    @Bean
    protected PathConcatenator createPathConcatenator()
    {
        return new PathConcatenator();
    }
}
