package com.prelim.springBatch.springBatch;

import javax.batch.api.chunk.ItemReader;
import java.io.Serializable;

public class Reader implements ItemReader<String> {
    @Override
    public void open(Serializable serializable) throws Exception {

    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public Object readItem() throws Exception {
        return null;
    }

    @Override
    public Serializable checkpointInfo() throws Exception {
        return null;
    }
}
