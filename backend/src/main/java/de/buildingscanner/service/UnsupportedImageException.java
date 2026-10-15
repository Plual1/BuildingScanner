package de.buildingscanner.service;

public class UnsupportedImageException extends RuntimeException {

    public UnsupportedImageException() {
        super("Only PNG and JPEG images are accepted");
    }
}
