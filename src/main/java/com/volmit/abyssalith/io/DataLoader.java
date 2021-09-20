package com.volmit.abyssalith.io;

import art.arcane.quill.collections.KMap;
import art.arcane.quill.execution.Looper;
import com.volmit.abyssalith.data.Message;
import com.volmit.abyssalith.data.User;

/**
 * Just like irisdata!
 */
public class DataLoader
{
    private StorageAccess storage;
    private KMap<Class<? extends DataType>, TypedLoader<?>> loaders = new KMap<>();
    private Looper cleaner = new Looper() {
        @Override
        protected long loop() {
            cleanup(10000);
            return 1000;
        }
    };

    public DataLoader(StorageAccess storage)
    {
        this.storage = storage;
        cleaner.start();
        registerLoader(User.class);
        registerLoader(Message.class);
    }

    public User getUser(long id)
    {
        return getLoader(User.class).get(id);
    }
    public Message getMessage(long id)
    {
        return getLoader(Message.class).get(id);
    }

    private <T extends DataType> void registerLoader(Class<T> c)
    {
        loaders.put(c, new TypedLoader<>(c, storage));
    }

    public void cleanup(long olderThan)
    {
        for(TypedLoader<?> i : loaders.values())
        {
            i.cleanup(olderThan);
        }
    }

    public void close()
    {
        cleanup(-1);
        cleaner.interrupt();
    }

    public <T extends DataType> TypedLoader<T> getLoader(Class<T> t)
    {
        return (TypedLoader<T>) loaders.get(t);
    }
}
