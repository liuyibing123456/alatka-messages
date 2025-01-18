package com.alatka.messages.core.holder;

import com.alatka.messages.core.util.BytesUtil;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class Bitmap {

    private final byte[] bytes;

    private final int offset;

    public Bitmap(byte[] bytes, int offset) {
        this.bytes = bytes;
        this.offset = offset;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public boolean exist(int domainNo) {
        int result = domainNo - this.offset - 1;
        return (this.bytes[result / 8] & 0x80 >> (result % 8)) == (0x80 >> (result % 8));
    }

    @Override
    public String toString() {
        Map<String, String> map = new LinkedHashMap<>(this.bytes.length);
        for (int i = 0; i < this.bytes.length; i++) {
            String key = "F" + (i + offset + 1) + "~F" + (i + 8 + offset);
            String value = BytesUtil.bytesToBinary(Arrays.copyOfRange(bytes, i, i + 1));
            map.put(key, value);
        }
        return map.toString();
    }

    public static void main(String[] args) {
        Bitmap bitmap = new Bitmap(BytesUtil.hexToBytes("2A"), 0);
        System.out.println(bitmap);
    }

}
