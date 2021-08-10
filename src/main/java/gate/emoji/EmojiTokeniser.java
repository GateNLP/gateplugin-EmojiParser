package gate.emoji;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

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

	private static final long serialVersionUID = -6790156059647665288L;

	private Logger log = LoggerFactory.getLogger(EmojiTokeniser.class);

	private String annotationSetName, annotationType;

	private Boolean fixTokens, createAnnotations;

	public String getAnnotationType() {
		return annotationType;
	}

	@RunTime
	@CreoleParameter(comment = "Type of annotation to create", defaultValue = "Emoji")
	public void setAnnotationType(String annotationType) {
		this.annotationType = annotationType;
	}

	public Boolean getFixTokens() {
		return fixTokens;
	}

	@RunTime
	@CreoleParameter(comment = "If true then Token annotations will be 'fixed' to match the emoji", defaultValue = "true")
	public void setFixTokens(Boolean fixTokens) {
		this.fixTokens = fixTokens;
	}

	public Boolean getCreateAnnotations() {
		return createAnnotations;
	}

	@RunTime
	@CreoleParameter(comment = "If true then we create annotations that span each Emoji found", defaultValue = "true")
	public void setCreateAnnotations(Boolean createAnnotations) {
		this.createAnnotations = createAnnotations;
	}

	public String getAnnotationSetName() {
		return annotationSetName;
	}

	@Optional
	@RunTime
	@CreoleParameter(comment = "Annotation set in which to work")
	public void setAnnotationSetName(String annotationSetName) {
		this.annotationSetName = annotationSetName;
	}

	private static final Set<String> TYPES_TO_REPLACE = new HashSet<>(
			Arrays.asList("Token", "SpaceToken", "DEFAULT_TOKEN"));

	public void execute() throws ExecutionException {

		if (!fixTokens && !createAnnotations) {
			Utils.logOnce(log, Level.WARN,
					"Emoji Tokeniser disabled as we are neither fixing tokens or creating annotations");
			return;
		}

		AnnotationSet inputAS = document.getAnnotations(annotationSetName);
		AnnotationSet removableAnnots = fixTokens ? inputAS.get(TYPES_TO_REPLACE) : null;

		EmojiParser.parseFromUnicode(document.getContent().toString(), (EmojiParser.UnicodeCandidate candidate) -> {

			long start = candidate.getEmojiStartIndex();
			long end = candidate.getFitzpatrickEndIndex();

			FeatureMap features = Utils.featureMap("string",
					candidate.getRawString());

			if (candidate.hasFitzpatrick()) {
				features.put("fitzpatrick", candidate.getFitzpatrickType());
			} else if (candidate.getEmoji().supportsFitzpatrick()) {
				// add an explicit "none" for emoji that could have had a fitzpatrick modifier
				// but don't in this case. This is different from the case of an emoji that
				// can't be FP-modified in the first place.
				features.put("fitzpatrick", "none");
			}
			if (candidate.getEmoji().getDescription().contains("+ regional indic")) {
				// this is a flag, the country code is always the first alias
				features.put("emoji_kind", "flag");
				features.put("flag_country", candidate.getEmoji().getAliases().get(0));
			}

			if (fixTokens) {
				// remove any existing tokens over the span as we'll replace these later
				if (inputAS.removeAll(removableAnnots.getContained(start, end))) {

					FeatureMap tokFeatures = Utils.featureMap("length", String.valueOf(end - start), "kind", "symbol",
							"category", "UH", "emoji_base", candidate.getEmoji().getUnicode(), "kind", "symbol",
							"category", "UH", "emoji_description", candidate.getEmoji().getDescription(),
							"emoji_aliases", candidate.getEmoji().getAliases(), "emoji_tags",
							candidate.getEmoji().getTags());

					tokFeatures.putAll(features);

					// create the new Token annotation
					Utils.addAnn(inputAS, start, end, "Token", tokFeatures);
				}
			}

			if (createAnnotations) {

				FeatureMap annFeatures = Utils.featureMap("base", candidate.getEmoji().getUnicode(), "description",
						candidate.getEmoji().getDescription(), "aliases", candidate.getEmoji().getAliases(), "tags",
						candidate.getEmoji().getTags());

				annFeatures.putAll(features);

				// create the new annotation
				Utils.addAnn(inputAS, start, end, annotationType, annFeatures);
			}

			// transformer has to return something but we only care about the side effects
			return "";
		});
	}
}