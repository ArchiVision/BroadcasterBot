package com.pathz.broadcaster.service.topic;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class TopicResolver {
    public List<String> resolveTopicsFromPostInformation(String text) {
        final Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner");
        props.setProperty("ner.useSUTime", "false");

        final StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        final Annotation document = new Annotation(text);

        pipeline.annotate(document);

        List<String> topics = new ArrayList<>();
        for (CoreMap sentence : document.get(CoreAnnotations.SentencesAnnotation.class)) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                if (pos.startsWith("NN")) {
                    String lemma = token.get(CoreAnnotations.LemmaAnnotation.class);
                    topics.add(lemma);
                }
            }
        }

        return topics;
    }
}
