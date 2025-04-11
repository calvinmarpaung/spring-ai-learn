package com.calvin.demo;

import org.springframework.util.MimeType;

public class Media {

    private final MimeType mimeType;
    private final byte[] data;

    public Media(MimeType mimeType, byte[] data) {
        this.mimeType = mimeType;
        this.data = data;
    }

    public MimeType getMimeType() {
        return mimeType;
    }

    public byte[] getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Media{" +
                "mimeType=" + mimeType +
                ", data.length=" + (data != null ? data.length : 0) +
                '}';
    }
}

