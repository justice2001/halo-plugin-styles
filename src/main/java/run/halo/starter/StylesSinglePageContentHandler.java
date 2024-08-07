package run.halo.starter;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import run.halo.app.theme.ReactiveSinglePageContentHandler;

@Component
@AllArgsConstructor
@Slf4j
public class StylesSinglePageContentHandler implements ReactiveSinglePageContentHandler {
    private final StylesProcessor stylesProcessor;

    @Override
    public Mono<SinglePageContentContext> handle(
        SinglePageContentContext singlePageContentContext) {
        return stylesProcessor.processContent(singlePageContentContext);
    }
}
