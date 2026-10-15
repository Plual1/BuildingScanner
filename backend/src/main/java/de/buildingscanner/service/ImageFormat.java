package de.buildingscanner.service;

/** Trusts the file's first bytes, never its name or the client's Content-Type. */
final class ImageFormat {

    private static final byte[] PNG = {(byte) 0x89, 'P', 'N', 'G', 0x0D, 0x0A, 0x1A, 0x0A};
    private static final byte[] JPEG = {(byte) 0xFF, (byte) 0xD8, (byte) 0xFF};

    private ImageFormat() {
    }

    static void requireSupported(byte[] bytes) {
        if (!startsWith(bytes, PNG) && !startsWith(bytes, JPEG)) {
            throw new UnsupportedImageException();
        }
    }

    private static boolean startsWith(byte[] bytes, byte[] prefix) {
        if (bytes.length < prefix.length) {
            return false;
        }
        for (int i = 0; i < prefix.length; i++) {
            if (bytes[i] != prefix[i]) {
                return false;
            }
        }
        return true;
    }
}
