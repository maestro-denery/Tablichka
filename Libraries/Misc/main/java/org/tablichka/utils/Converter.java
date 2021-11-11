package org.tablichka.utils;

import java.nio.ByteBuffer;
import java.util.UUID;

public class Converter {
    public static byte[] uuidToBytes(UUID uuid) {
        return ByteBuffer.allocate(16).putLong(uuid.getMostSignificantBits()).putLong(uuid.getLeastSignificantBits()).array();
    }

    public static UUID uuidFromBytes(final byte[] bytes) throws IllegalArgumentException {
        if (bytes.length < 2) { throw new IllegalArgumentException("Byte array too small."); }
        final ByteBuffer bb = ByteBuffer.wrap(bytes);
        return new UUID(bb.getLong(), bb.getLong());
    }
}
