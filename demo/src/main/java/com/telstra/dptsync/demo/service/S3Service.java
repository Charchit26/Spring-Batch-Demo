package com.telstra.dptsync.demo.service;

public interface S3Service {
    byte[] downloadFile(String keyName);
}