package com.andevindo.andevindonetworking;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CountingOutputStream extends FilterOutputStream {
    private final ProgressListener progressListener;
    private long transferred;
    private long fileLength;

    public CountingOutputStream(final OutputStream out, long fileLength,
                                final ProgressListener listener) {
        super(out);
        this.fileLength = fileLength;
        this.progressListener = listener;
        this.transferred = 0;
    }

    public void write(byte[] buffer, int offset, int length) throws IOException {
        out.write(buffer, offset, length);
        if (progressListener != null) {
            this.transferred += length;
            int progress = (int) ((transferred * 100.0f) / fileLength);
            this.progressListener.transferred(this.transferred, progress);
        }
    }

    public void write(int oneByte) throws IOException {
        out.write(oneByte);
        if (progressListener != null) {
            this.transferred++;
            int progress = (int) ((transferred * 100.0f) / fileLength);
            this.progressListener.transferred(this.transferred, progress);
        }
    }
}
