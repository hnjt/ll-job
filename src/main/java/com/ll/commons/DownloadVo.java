package com.ll.commons;


public class DownloadVo {

    private String fileName;

    private byte[] data;

    /**
     * 获得fileName
     *
     * @return fileName fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 设定fileName
     *
     * @param fileName fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 获得data
     *
     * @return data data
     */
    public byte[] getData() {
        return data;
    }

    /**
     * 设定data
     *
     * @param data data
     */
    public void setData(byte[] data) {
        this.data = data;
    }
}
