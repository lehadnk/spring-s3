package s3.s3.business;

import s3.s3.S3Storage;
import s3.s3.exceptions.NoSuchStorageException;

import java.util.HashMap;
import java.util.List;

public class StorageContainer {
    private final HashMap<String, HashMap<String, S3Storage>> s3Storages = new HashMap<>();

    public StorageContainer(
            List<S3Storage> s3Storages
    ) {
        for (var storage : s3Storages) {
            if (!this.s3Storages.containsKey(storage.getStorageRegion())) {
                this.s3Storages.put(storage.getStorageRegion(), new HashMap<>());
            }

            this.s3Storages.get(storage.getStorageRegion()).put(storage.getStorageName(), storage);
        }
    }

    public S3Storage getStorage(String region, String storageName) {
        if (!this.s3Storages.containsKey(region)) {
            throw new NoSuchStorageException("No buckets for region " + region);
        }
        if (!this.s3Storages.get(region).containsKey(storageName)) {
            throw new NoSuchStorageException("No storage named " + storageName + " found in region " + region);
        }

        return this.s3Storages.get(region).get(storageName);
    }
}
