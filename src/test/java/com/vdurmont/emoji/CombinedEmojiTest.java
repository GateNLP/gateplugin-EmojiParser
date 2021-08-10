package com.vdurmont.emoji;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class CombinedEmojiTest {

	@Test
	public void manHealthWorker() {
		
		// ideally we should just do this to every emoji loaded in EmojiJsonTest but
		// we don't yet support the gender modifiers so we'll leave it here for now
		
		//1F468 200D 2695 FE0F                       ; fully-qualified     # 👨‍⚕️ man health worker
		assertTrue(EmojiManager.isEmoji("👨‍⚕️"));
		
		//1F468 200D 2695                            ; non-fully-qualified # 👨‍⚕ man health worker
		assertTrue(EmojiManager.isEmoji("👨‍⚕"));
		
		//1F468 1F3FB 200D 2695 FE0F                 ; fully-qualified     # 👨🏻‍⚕️ man health worker: light skin tone
		assertTrue(EmojiManager.isEmoji("👨🏻‍⚕️"));
		
		//1F468 1F3FB 200D 2695                      ; non-fully-qualified # 👨🏻‍⚕ man health worker: light skin tone
		assertTrue(EmojiManager.isEmoji("👨🏻‍⚕"));
		
		//1F468 1F3FC 200D 2695 FE0F                 ; fully-qualified     # 👨🏼‍⚕️ man health worker: medium-light skin tone
		assertTrue(EmojiManager.isEmoji("👨🏼‍⚕️"));

		//1F468 1F3FC 200D 2695                      ; non-fully-qualified # 👨🏼‍⚕ man health worker: medium-light skin tone
		assertTrue(EmojiManager.isEmoji("👨🏼‍⚕"));
		
		//1F468 1F3FD 200D 2695 FE0F                 ; fully-qualified     # 👨🏽‍⚕️ man health worker: medium skin tone
		assertTrue(EmojiManager.isEmoji("👨🏽‍⚕️"));
		
		//1F468 1F3FD 200D 2695                      ; non-fully-qualified # 👨🏽‍⚕ man health worker: medium skin tone
		assertTrue(EmojiManager.isEmoji("👨🏽‍⚕"));
		
		//1F468 1F3FE 200D 2695 FE0F                 ; fully-qualified     # 👨🏾‍⚕️ man health worker: medium-dark skin tone
		assertTrue(EmojiManager.isEmoji("👨🏾‍⚕️"));
		
		//1F468 1F3FE 200D 2695                      ; non-fully-qualified # 👨🏾‍⚕ man health worker: medium-dark skin tone
		assertTrue(EmojiManager.isEmoji("👨🏾‍⚕"));
		
		//1F468 1F3FF 200D 2695 FE0F                 ; fully-qualified     # 👨🏿‍⚕️ man health worker: dark skin tone
		assertTrue(EmojiManager.isEmoji("👨🏿‍⚕️"));
		
		//1F468 1F3FF 200D 2695                      ; non-fully-qualified # 👨🏿‍⚕ man health worker: dark skin tone
		assertTrue(EmojiManager.isEmoji("👨🏿‍⚕"));

	}
}
