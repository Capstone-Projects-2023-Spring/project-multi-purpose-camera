package com.example.layout_version;

import android.net.Uri;

import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.upstream.TransferListener;

import java.io.IOException;




public class ByteArrayDataSource implements DataSource {
    private byte[] byteArray;
    private int currentPosition;

    public ByteArrayDataSource(byte[] byteArray) {
        this.byteArray = byteArray;
        this.currentPosition = 0;
    }

    @Override
    public void addTransferListener(TransferListener transferListener) {

    }

    @Override
    public long open(DataSpec dataSpec) throws IOException {
        currentPosition = (int) dataSpec.position;
        return byteArray.length - currentPosition;
    }

    @Override
    public int read(byte[] buffer, int offset, int readLength) throws IOException {
        if (currentPosition >= byteArray.length) {
            return -1; // End of data
        }

        int bytesToRead = Math.min(readLength, byteArray.length - currentPosition);
        System.arraycopy(byteArray, currentPosition, buffer, offset, bytesToRead);
        currentPosition += bytesToRead;
        return bytesToRead;
    }

    @Override
    public void close() throws IOException {
        // No need to close anything in this case
    }

    @Override
    public Uri getUri() {
        return null;
    }
}