package uk.ac.gate.emoji;

import com.vdurmont.emoji.EmojiParser;
import com.vdurmont.emoji.EmojiParser.EmojiTransformer;
import com.vdurmont.emoji.EmojiParser.FitzpatrickAction;
import com.vdurmont.emoji.EmojiParser.UnicodeCandidate;

import gate.Factory;
import gate.FeatureMap;
import gate.creole.AbstractLanguageAnalyser;
import gate.creole.ExecutionException;
import gate.creole.metadata.CreoleResource;
import gate.util.InvalidOffsetException;

@CreoleResource
public class EmojiParserPR extends AbstractLanguageAnalyser {

	private static final long serialVersionUID = -4590956044578890382L;

	@Override
	public void execute() throws ExecutionException {
		
		for (UnicodeCandidate emoji : EmojiParser.getUnicodeCandidates(document.getContent().toString())) {
			try {
				FeatureMap features = Factory.newFeatureMap();
				
				features.put("description", emoji.getEmoji().getDescription());
				features.put("aliases", emoji.getEmoji().getAliases());
				features.put("tags", emoji.getEmoji().getTags());
				
				if (emoji.hasFitzpatrick()) {
					features.put("fitzpatrickType", emoji.getFitzpatrickType());
					features.put("fitzpatrickUnicode", emoji.getFitzpatrickUnicode());
				}
				
				document.getAnnotations().add((long)emoji.getEmojiStartIndex(),(long)emoji.getEmojiEndIndex(),"Emoji", features);
			} catch (InvalidOffsetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
