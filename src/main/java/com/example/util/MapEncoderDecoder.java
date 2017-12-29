package com.example.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.commons.codec.binary.Base64;

public class MapEncoderDecoder
{
	public static final String ENCTIME = "@enctime";
	private final SecureRandom _random;
	private final Cipher _encodingCipher;
	private final Cipher _decodingCipher;

	public MapEncoderDecoder(char[] passphrase) throws RuntimeException {
		try {
			byte[] salt = { -7, -55, -7, -55, -7, -55, -7, -55 };
			PBEParameterSpec pbeParam = new PBEParameterSpec(salt, 20);
			SecretKeyFactory keyFactory = SecretKeyFactory
					.getInstance("PBEWithMD5AndDES");
			SecretKey secretKey = keyFactory.generateSecret(new PBEKeySpec(
					passphrase));
			this._encodingCipher = Cipher
					.getInstance("PBEWithMD5AndDES/CBC/PKCS5Padding");
			this._encodingCipher.init(1, secretKey, pbeParam);
			this._decodingCipher = Cipher
					.getInstance("PBEWithMD5AndDES/CBC/PKCS5Padding");
			this._decodingCipher.init(2, secretKey, pbeParam);
			this._random = new SecureRandom();
		} catch (Exception e) {
			throw new IllegalStateException("private ctor failed", e);
		}
	}

	public String encryptMap(Map<String, String> map)
			throws IllegalArgumentException {
		if (map != null) {
			byte[] b = new byte[3];
			this._random.nextBytes(b);
			StringBuilder sb = new StringBuilder(80).append(ByteUtil.toHex(b))
					.append('\n').append("@enctime").append('=')
					.append(System.currentTimeMillis()).append('\n');

			for (Map.Entry<String, String> me : map.entrySet()) {
				String key = (String) me.getKey();
				String value = (String) me.getValue();
				if ((key.indexOf(10) > -1) || (key.indexOf(61) > -1)) {
					throw new IllegalArgumentException("key [" + key
							+ "] may not contain a '\n' or '=' character");
				}
				if (value.indexOf(10) > -1) {
					throw new IllegalArgumentException("value [" + value
							+ "] may not contain a '\n' character");
				}
				sb.append(key).append('=').append(value).append('\n');
			}

			return internalEncode(sb.toString());
		}
		return null;
	}

	public String encryptMapURLEncoded(Map<String, String> map) {
		try {
			return URLEncoder.encode(encryptMap(map), "UTF8");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("failed to encode url using map ["
					+ map + "]", e);
		}
	}

	public Map<String, String> decryptMapURLEncoded(String value) {
		try {
			return decryptMap(URLDecoder.decode(value, "UTF8"));
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("failed to decode url [" + value
					+ "]", e);
		}
	}

	public Map<String, String> decryptMap(String value) {
		Map m = new HashMap();
		if (value != null) {
			String stringWithMap = internalDecode(value);
			int i = 0;
			int j = 0;
			int k = 0;
			for (int n = stringWithMap.length(); (j < n) && (j > -1); ++i) {
				j = stringWithMap.indexOf(10, k);
				if (j > -1) {
					String s = stringWithMap.substring(k, j);

					if (i > 0) {
						int l = s.indexOf(61);
						if (l > -1) {
							m.put(s.substring(0, l), s.substring(l + 1));
						}
					}
					k = j + 1;
				}
			}
			return m;
		}
		return m;
	}

	public String encryptString(String value) {
		StringBuilder sb = null;
		if (value != null) {
			byte[] b = new byte[3];
			this._random.nextBytes(b);
			if (value.indexOf(10) > -1) {
				throw new IllegalArgumentException("value [" + value
						+ "] may not contain a '\n' character");
			}

			sb = new StringBuilder(32).append(ByteUtil.toHex(b)).append('\n')
					.append(value);
		}

		return ((sb != null) ? internalEncode(sb.toString()) : null);
	}

	public String decryptString(String value) {
		String rValue = null;
		if ((value != null) && (value.length() > 0)) {
			String decodeString = internalDecode(value);
			if (decodeString != null) {
				int i = decodeString.indexOf(10);
				if (i > -1) {
					rValue = decodeString.substring(i + 1);
				}
			}
		}

		return rValue;
	}

	private String internalEncode(String value) {
		try {
			if (value != null) {
				byte[] encodedBytes;
				synchronized (this._encodingCipher) {
					encodedBytes = this._encodingCipher.doFinal(value
							.getBytes("UTF8"));
				}
				return new String(Base64.encodeBase64(encodedBytes), "UTF8");
			}

			return null;
		} catch (Throwable e) {
			throw new IllegalArgumentException("internal encoding of string ["
					+ value + "] failed", e);
		}
	}

	private String internalDecode(String value) {
		if (value != null) {
			try {
				byte[] dec = Base64.decodeBase64(value.getBytes("UTF8"));
				byte[] decodedBytes;
				synchronized (this._decodingCipher) {
					decodedBytes = this._decodingCipher.doFinal(dec);
				}
				return new String(decodedBytes, "UTF8");
			} catch (Throwable e) {
				throw new IllegalArgumentException("internal decoding of ["
						+ value + "] failed", e);
			}
		}

		return null;
	}

	public String toHex(byte[] data) {
		return ByteUtil.toHex(data);
	}
}
