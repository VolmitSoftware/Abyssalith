package volmbot.io;

import art.arcane.quill.collections.KMap;
import art.arcane.quill.math.M;
import volmbot.Main;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.concurrent.locks.ReentrantLock;

public class TypedLoader<T extends DataType> {
    private final Class<T> type;
    private final KMap<Long, T> cache;
    private final KMap<Long, Long> lastUse;
    private final ReentrantLock lock;
    private final StorageAccess storage;

    public TypedLoader(Class<T> type, StorageAccess storage)
    {
        this.storage = storage;
        this.type = type;
        lock = new ReentrantLock();
        cache = new KMap<>();
        lastUse = new KMap<>();
    }

    public void cleanup(long olderThan)
    {
        lock.lock();

        for(Long i : lastUse.k())
        {
            if(olderThan <= 0 || M.ms() - lastUse.get(i) > olderThan)
            {
                unload(i);
            }
        }

        lock.unlock();
    }

    private synchronized void unload(Long i) {
        lastUse.remove(i);
        T m = cache.get(i);
        storage.set(type.getSimpleName().toLowerCase(Locale.ROOT), i, storage.toString(m));
        Main.info("Saved " + type.getSimpleName() + "[" + i + "]");
    }

    public T get(long key)
    {
        lock.lock();
        lastUse.compute(key, (k,v) -> M.ms());
        T t = cache.get(key);

        if(t == null)
        {
            t = cache.computeIfAbsent(key, (v) -> {
                if(storage.exists(type.getSimpleName().toLowerCase(Locale.ROOT), key))
                {
                    Main.info("Loaded " + type.getSimpleName() + "[" + key + "]");
                    return storage.fromString(storage.get(type.getSimpleName().toLowerCase(Locale.ROOT), key), type);
                }

                try {
                    T x = type.getConstructor().newInstance();
                    x.id(key);
                    Main.info("Created " + type.getSimpleName() + "[" + key + "]");
                    return x;
                } catch (Throwable e) {
                    e.printStackTrace();
                }

                return null;
            });
        }

        lock.unlock();
        return t;
    }
}
