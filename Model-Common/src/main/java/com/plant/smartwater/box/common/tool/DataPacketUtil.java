package com.plant.smartwater.box.common.tool;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class DataPacketUtil {

    public static byte uniteBytes(byte src0, byte src1) {
        byte _b0 = Byte.decode("0x" + new String(new byte[]{src0}))
                .byteValue();
        _b0 = (byte) (_b0 << 4);
        byte _b1 = Byte.decode("0x" + new String(new byte[]{src1}))
                .byteValue();
        byte ret = (byte) (_b0 ^ _b1);
        return ret;
    }

    /**
     * 将指定字符串src，以每两个字符分割转换为16进制形式 如："2B44EFD9" --> byte[]{0x2B, 0x44, 0xEF,
     * 0xD9}
     *
     * @param src String
     * @return byte[]
     */
    public static byte HexString2Byte(String src) {
        byte ret;
        byte[] tmp;
        try {
            tmp = src.getBytes("gbk");
            ret = uniteBytes(tmp[0], tmp[1]);
            return ret;
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0x00;
        }

    }

    /**
     * 将指定字符串src，以每两个字符分割转换为16进制形式 如："2B44EFD9" --> byte[]{0x2B, 0x44, 0xEF,
     * 0xD9}
     *
     * @param src String
     * @return byte[]
     */
    public static byte[] HexString2Bytes(String src) {
        String[] params = src.split("\\s+");
        byte[] datas = new byte[params.length];
        for (int i = 0; i < params.length; i++) {
            datas[i] = HexString2Byte(params[i]);
        }
        return datas;
    }


    /**
     * 二进制转化十六进制
     *
     * @param bytes
     * @return
     */
    public static String bytesToHexFun3(byte[] bytes) {
        StringBuilder buf = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) { // 使用String的format方法进行转换
            buf.append(String.format("%02x", new Integer(b & 0xff)));
        }

        return buf.toString();
    }

    public static String byteToHexStr(byte[] src, boolean format) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv + " ");
        }

        return stringBuilder.toString();
    }

    public static String byteToHexStr(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv + " ");
        }

        return stringBuilder.toString();
    }

    public static String byteToHexStr(Byte[] src, boolean format) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv + " ");
        }

        return stringBuilder.toString();
    }

    public static String byteToHexStr(byte[] src, int length, boolean format) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || length <= 0) {
            return null;
        }
        for (int i = 0; i < length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv + " ");
        }

        return stringBuilder.toString();
    }

    public static String byteToHexStr(byte src) {
        StringBuilder stringBuilder = new StringBuilder("");
        int v = src & 0xFF;
        String hv = Integer.toHexString(v);
        if (hv.length() < 2) {
            stringBuilder.append(0);
        }
        stringBuilder.append(hv);
        return stringBuilder.toString();
    }

    public static String byteToHexStr(byte[] src, int start, int end) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = start; i < end; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }

        return stringBuilder.toString();
    }


    /**
     * 将int数值转换为byte数组
     *
     * @param value  要转换的int值
     * @param length 字节数
     * @return byte数组
     */
    public static byte[] intToBytes(int value, int length) {
        byte[] byte_src = new byte[length];
        for (int i = length - 1; i >= 0; i--) {
            byte_src[i] = (byte) (value >> (8 * (length - i - 1)));
        }
        return byte_src;
    }

    /**
     * byte数组中取int数值
     *
     * @param ary    byte数组
     * @param offset 从数组的第offset位开始
     * @param length 字节个数
     * @return int数值
     */
    public static int bytesToInt(byte[] ary, int offset, int length) {
        int param = 8 * (length - 1);
        long value = ((ary[offset]) & 0xFF) << param;
        for (int index = 1; index < length; index++) {
            param = 8 * (length - index - 1);
            value = value | ((ary[offset + index] & 0xFF) << param);
        }
        return (int) value;
    }


    /**
     * 将long数值转换为byte数组
     *
     * @param value  要转换的int值
     * @param length 字节个数
     * @return byte数组
     */
    public static byte[] longToBytes(long value, int length) {
        byte[] byte_src = new byte[length];
        for (int i = length - 1; i >= 0; i--) {
            byte_src[i] = (byte) (value >> (8 * (length - i - 1)));
        }
        return byte_src;
    }

    /**
     * byte数组中取long数值
     *
     * @param ary    byte数组
     * @param offset 从数组的第offset位开始
     * @param length
     * @return long数值
     */
    public static long bytesToLong(byte[] ary, int offset, int length) {
        long param = 8 * (length - 1);
        long value = ((ary[offset]) & 0xFF) << param;
        for (int index = 1; index < length; index++) {
            param = 8 * (length - index - 1);
            value = value | ((ary[offset + index] & 0xFF) << param);
        }
        return value;
    }

    public static byte[] listToBytes(List<Byte> list) {
        byte[] datas = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            datas[i] = list.get(i);
        }
        return datas;
    }

}
