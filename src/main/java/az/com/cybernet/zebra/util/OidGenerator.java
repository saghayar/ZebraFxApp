/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package az.com.cybernet.zebra.util;

/**
 *
 * @author SAMIR-PC
 */
import java.net.InetAddress;
import java.net.UnknownHostException;

public class OidGenerator {
    // use base 36 (i.e., 0123456789abcdefghijklmnopqrstuvwyz) for encoding

    private static final int ENCODING_BASE = 36;

    private static final int TIME_LENGTH = 8;

    private static final long IP_RESET = getPower(ENCODING_BASE, 2);

    private static final long COUNTER_RESET = getPower(ENCODING_BASE, 3);

    private static String ip = getIP();
    private static String hexTime = getHexTime();
    private static long counter = 0;

    /* calculate p'th power of n */
    private static long getPower(int n, int p) {
        long result = 1;
        for (int i = 0; i < p; i++) {
            result *= n;
        }
        return result;
    }

    /* returns zero padded,
	* IP_LENGTH length,
	* ENCODING_BASE long encoded
	* ip address */
    private static String getIP() {
        long ip = 0;
        try {
            byte[] b = InetAddress.getLocalHost().getAddress();
            ip = ((b[3] & 0xFF) << 0)
                    & 0xFFFFFFFFL;
            /* ip = ((((b[0] & 0xFF) << 24)
			+ ((b[1] & 0xFF) << 16)
			+ ((b[2] & 0xFF) << 8)
			+ ((b[3] & 0xFF) << 0)
			& 0xFFFFFFFFL));
             */
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return Long.toString(ip + IP_RESET, ENCODING_BASE).substring(1);
    }

    private static String getHexTime() {
        /* 2059'da 8 haneyi asacak ve
		* 1980'den sonra 8 haneden eksik olmayacak */
        String s = Long.toString(System.currentTimeMillis(), ENCODING_BASE);
        int l = s.length();
        if (l > TIME_LENGTH) {
            return s.substring(l - TIME_LENGTH);
        } else {
            return s;
        }
    }

    public static synchronized String generateOid() {
        String oid = ip + hexTime + Long.toString(counter + COUNTER_RESET, ENCODING_BASE);

        counter = (counter + 1) % COUNTER_RESET;

        if (counter == 0) {
            String tempTime = getHexTime();
            while (hexTime.equalsIgnoreCase(tempTime)) {
                tempTime = getHexTime();
            }
            hexTime = tempTime;
        }

        return oid;
    }

}
