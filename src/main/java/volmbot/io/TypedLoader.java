package volmbot.io;

import art.arcane.quill.collections.KMap;
import art.arcane.quill.math.M;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.locks.ReentrantLock;

public class TypedLoader<T extends DataType> {
    private final Class<T> type;
    private final KMap<Long, T> cache;
    private final KMap<Long, Long> lastUse;
    private final ReentrantLock lock;

    public TypedLoader(Class<T> type)
    {
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
    }

    public T get(long key)
    {
        lock.lock();
        lastUse.compute(key, (k,v) -> M.ms());
        T t = cache.get(key);

        if(t == null)
        {
            t = cache.computeIfAbsent(key, (v) -> {
                try {
                    T x = type.getConstructor().newInstance();
                    x.id(key);
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
