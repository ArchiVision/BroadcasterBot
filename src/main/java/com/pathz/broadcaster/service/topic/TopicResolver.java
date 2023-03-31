package com.pathz.broadcaster.service.topic;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
@Slf4j
public class TopicResolver {
    final Properties props;
    final StanfordCoreNLP pipeline;

    TopicResolver() {
        log.info("Initializing topic NLP resolver...");
        props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner");
        props.setProperty("ner.useSUTime", "false");

        pipeline = new StanfordCoreNLP(props);

        log.info("Initializing of topic NLP resolver is done");
    }

    public List<String> resolveTopicsFromPostInformation(String text) {
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
