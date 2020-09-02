package Util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;


public class CryptationManager {
    
    public final String DEFAULT_ENCODING = "UTF8"; 
    Base64.Encoder enc;
    Base64.Decoder dec;

    
    public CryptationManager() {
		enc = Base64.getEncoder();
		dec = Base64.getDecoder();
    }

    public String encrypt(String text) {
        try {
            return  new String(enc.encode(text.getBytes(DEFAULT_ENCODING)));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public String decrypt(String text) {
        try {
            return new String(dec.decode(text), DEFAULT_ENCODING);
        } catch (IOException e) {
            return null;
        }
    }

}
