package pl.faustyna.gallery.translation.logging;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ResponseOutputStream extends ServletOutputStream {
    protected ServletOutputStream stream;
    protected ByteArrayOutputStream cache;

    public ResponseOutputStream(final ServletOutputStream stream, final ByteArrayOutputStream cache) {
        super();
        this.stream = stream;
        this.cache = cache;
    }

    @Override
    public void write(final int b) throws IOException {
        if (stream == null)
            throw new IOException("ServletOutputStream stream: null, unable to write");
        else if (cache == null)
            throw new IOException("ByteArrayOutputStream cache: null, unable to write");
        stream.write(b);
        cache.write(b);
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setWriteListener(final WriteListener writeListener) {

    }
}