package uk.ac.gate.emoji;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.vdurmont.emoji.EmojiParser;

import gate.AnnotationSet;
import gate.FeatureMap;
import gate.Utils;
import gate.creole.AbstractLanguageAnalyser;
import gate.creole.ExecutionException;
import gate.creole.metadata.CreoleParameter;
import gate.creole.metadata.CreoleResource;
import gate.creole.metadata.Optional;
import gate.creole.metadata.RunTime;

@CreoleResource(name = "Emoji Tokeniser", comment = "Creates (more) correct Token annotations over emoji, handling things like flags, Fitzpatrick types, etc.")
public class EmojiTokeniser extends AbstractLanguageAnalyser {

  private String annotationSetName;

  public String getAnnotationSetName() {
    return annotationSetName;
  }

  @Optional
  @RunTime
  @CreoleParameter(comment = "Annotation set in which to work")
  public void setAnnotationSetName(String annotationSetName) {
    this.annotationSetName = annotationSetName;
  }

  private static final Set<String> TYPES_TO_REPLACE = new HashSet<>(Arrays.asList("Token", "SpaceToken", "DEFAULT_TOKEN"));

  public void execute() throws ExecutionException {
    AnnotationSet inputAS = document.getAnnotations(annotationSetName);
    AnnotationSet removableAnnots = inputAS.get(TYPES_TO_REPLACE);
    
    EmojiParser.parseFromUnicode(document.getContent().toString(), (EmojiParser.UnicodeCandidate candidate) -> {
      // remove any existing Token, SpaceToken or DEFAULT_TOKEN covered by this emoji
      long start = candidate.getEmojiStartIndex();
      long end = candidate.getFitzpatrickEndIndex();
      inputAS.removeAll(removableAnnots.getContained(start, end));
      FeatureMap features = Utils.featureMap(
            "string", candidate.getEmoji().getUnicode() + candidate.getFitzpatrickUnicode(),
            "emoji_base", candidate.getEmoji().getUnicode(),
            "length", String.valueOf(end - start),
            "kind", "symbol",
            "category", "UH",
            "emoji_description", candidate.getEmoji().getDescription());
      if(candidate.hasFitzpatrick()) {
        features.put("fitzpatrick", candidate.getFitzpatrickType());
      } else if(candidate.getEmoji().supportsFitzpatrick()) {
        // add an explicit "none" for emoji that could have had a fitzpatrick modifier but
        // don't in this case.  This is different from the case of an emoji that can't be
        // FP-modified in the first place.
        features.put("fitzpatrick", "none");
      }
      if(candidate.getEmoji().getDescription().contains("+ regional indic")) {
        // this is a flag, the country code is always the first alias
        features.put("emoji_kind", "flag");
        features.put("flag_country", candidate.getEmoji().getAliases().get(0));
      }
      Utils.addAnn(inputAS, start, end, "Token", features);

      // transformer has to return something but we only care about the side effects
      return "";
    });
  }
}