package com.vdurmont.emoji;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class JoinedEmojiTest {

	@Test
	public void manShrugging() {
		//1F937 200D 2642 FE0F                       ; fully-qualified     # 🤷‍♂️ man shrugging
		assertTrue(EmojiManager.isEmoji("🤷‍♂️"));
		
		//1F937 200D 2642                            ; non-fully-qualified # 🤷‍♂ man shrugging
		assertTrue(EmojiManager.isEmoji("🤷‍♂"));
		
		//1F937 1F3FB 200D 2642 FE0F                 ; fully-qualified     # 🤷🏻‍♂️ man shrugging: light skin tone
		//1F937 1F3FB 200D 2642                      ; non-fully-qualified # 🤷🏻‍♂ man shrugging: light skin tone
		//1F937 1F3FC 200D 2642 FE0F                 ; fully-qualified     # 🤷🏼‍♂️ man shrugging: medium-light skin tone
		//1F937 1F3FC 200D 2642                      ; non-fully-qualified # 🤷🏼‍♂ man shrugging: medium-light skin tone
		//1F937 1F3FD 200D 2642 FE0F                 ; fully-qualified     # 🤷🏽‍♂️ man shrugging: medium skin tone
		//1F937 1F3FD 200D 2642                      ; non-fully-qualified # 🤷🏽‍♂ man shrugging: medium skin tone
		//1F937 1F3FE 200D 2642 FE0F                 ; fully-qualified     # 🤷🏾‍♂️ man shrugging: medium-dark skin tone
		//1F937 1F3FE 200D 2642                      ; non-fully-qualified # 🤷🏾‍♂ man shrugging: medium-dark skin tone
		//1F937 1F3FF 200D 2642 FE0F                 ; fully-qualified     # 🤷🏿‍♂️ man shrugging: dark skin tone
		//1F937 1F3FF 200D 2642                      ; non-fully-qualified # 🤷🏿‍♂ man shrugging: dark skin tone

	}
}
