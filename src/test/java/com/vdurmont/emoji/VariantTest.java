package com.vdurmont.emoji;

import static org.junit.Assert.assertEquals;

import java.util.regex.Pattern;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class VariantTest {

	@Test
	public void testVariantSelectors() {
		// 1F468 200D 2695 FE0F                       ; fully-qualified     # 👨‍⚕️ man health worker
		assertEquals(Variant.GRAPHICAL, Variant.getVariantFromEmoji("👨‍⚕️"));
		
		// 1F468 200D 2695                            ; non-fully-qualified # 👨‍⚕ man health worker
		assertEquals(null, Variant.getVariantFromEmoji("👨‍⚕"));
		
		// this one isn't in the test document but should be the text form
		// 1F468 200D 2695 FE0F
		assertEquals(Variant.TEXT, Variant.getVariantFromEmoji("👨‍⚕︎"));
		
		assertEquals(null, Variant.getVariantFromString("Testing"));
	}
}
