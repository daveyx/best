package com.example.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ByteUtil
{
//    private static final String HEXDIGITS = "0123456789ABCDEF";

    private ByteUtil() {
    }

    public static byte[] createSHA512(final String s)
    {
        return ByteUtil.createDigest(s, "SHA-512");
    }

    public static byte[] createMD5(final String s)
    {
        return ByteUtil.createDigest(s, "MD5");
    }

    public static boolean isSameByteHash(byte[] ba1, byte[] ba2) {
        if (ba1.length != ba2.length) {
            return false;
        }
        boolean same = true;
        for (int i = 0; i < ba1.length; ++i) {
            same &= ba1[i] == ba2[i];
        }
        return same;
    }

    public static String toHex(byte[] data) {
        StringBuilder buf = new StringBuilder(data.length * 2);
        for (int i = 0; i != data.length; ++i) {
            int v = data[i] & 255;
            buf.append("0123456789ABCDEF".charAt(v >> 4)).append("0123456789ABCDEF".charAt(v & 15));
        }
        return buf.toString();
    }

    public static byte[] createDigest(String s, String digest) {
        try {
            MessageDigest md = MessageDigest.getInstance(digest);
            md.update(s.getBytes());
            return md.digest();
        }
        catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("JDK misses [" + digest + "]?", e);
        }
    }

}
