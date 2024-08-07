package run.halo.starter;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import run.halo.app.theme.ReactivePostContentHandler;

@AllArgsConstructor
@Component
@Slf4j
public class StylesPostContentHandler implements ReactivePostContentHandler {

    private final StylesProcessor stylesProcessor;

    @Override
    public Mono<PostContentContext> handle(PostContentContext contentContext) {
        return stylesProcessor.processContent(contentContext);
    }
}
