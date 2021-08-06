package com.vdurmont.emoji;

import java.io.UnsupportedEncodingException;

/**
 * Enum that represents the variant selectors
 */
public enum Variant {

	/**
	 * Text based variant selector
	 */
	TEXT("\uFE0E"),

	/**
	 * graphical based variant selector
	 */
	GRAPHICAL("\uFE0F");

	/**
   * The unicode representation of the Fitzpatrick modifier
   */
  public final String unicode;

	Variant(String unicode) {
		this.unicode = unicode;
	}

	public static Variant getVariantFromEmoji(String unicode) {
		for (Variant v : values()) {
			if (unicode.endsWith(v.unicode)) {
				return v;
			}
		}
		return null;
	}
	
	public static Variant getVariantFromString(String unicode) {
		if (unicode == null) return null;
		for (Variant v : values()) {
			if (unicode.startsWith(v.unicode)) {
				return v;
			}
		}
		return null;
	}

	public static Variant variantFromType(String type) {
		try {
			return Variant.valueOf(type.toUpperCase());
		} catch (IllegalArgumentException e) {
			return null;
		}
	}
	
	public static String stripVariant(byte[] bytes) throws UnsupportedEncodingException {
		String input = new String(bytes, "UTF-8");
	      
	    Variant v = Variant.getVariantFromEmoji(input);
	    
	    if (v == null) return input;
	    
	    return input.substring(0,input.length()-v.unicode.length());	    
	}
}
