package volmbot.io.storage;

import volmbot.io.StorageAccess;

public class RedisStorageAccess implements StorageAccess {

    @Override
    public boolean exists(String typeName, Long key) {
        return false;
    }

    @Override
    public void delete(String typeName, Long key) {

    }

    @Override
    public void set(String typeName, Long key, String data) {

    }

    @Override
    public String get(String typeName, Long key) {
        return null;
    }
}
