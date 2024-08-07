package run.halo.starter;

import com.google.common.base.Throwables;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import run.halo.app.plugin.ReactiveSettingFetcher;
import run.halo.app.theme.ReactivePostContentHandler;
import run.halo.app.theme.ReactiveSinglePageContentHandler;

@AllArgsConstructor
@Component
@Slf4j
public class StylesProcessor {
    private final ReactiveSettingFetcher reactiveSettingFetcher;


    public <T> Mono<T> processContent(T ctx) {
        return reactiveSettingFetcher.fetch("basic", StylesConfig.class)
            .map(stylesConfig -> {
                log.info("Style Starting Inject");
                if (ctx instanceof ReactivePostContentHandler.PostContentContext) {
                    log.info("Styles: Inject Post.");
                    String content = getContent(
                        ((ReactivePostContentHandler.PostContentContext) ctx).getContent(),
                        stylesConfig
                    );
                    ((ReactivePostContentHandler.PostContentContext) ctx).setContent(content);
                } else if (ctx instanceof ReactiveSinglePageContentHandler.SinglePageContentContext) {
                    log.info("Styles: Inject Single Page.");
                    String content = getContent(
                        ((ReactiveSinglePageContentHandler.SinglePageContentContext) ctx).getContent(),
                        stylesConfig
                    );
                    ((ReactiveSinglePageContentHandler.SinglePageContentContext) ctx).setContent(content);
                } else {
                    log.info("Styles: Unknown Context.");
                }
                return ctx;
            })
            .onErrorResume(e -> {
                if (e != null) {
                    log.error("VditorHeadProcessor process failed", Throwables.getRootCause(e));
                }
                return Mono.just(ctx);
            });
    }

    public String getContent(String content, StylesConfig stylesConfig) {
        ScriptBuilder extra = new ScriptBuilder("styles", "v1.0.0");
        if (stylesConfig.getEnableMacCode()) {
            extra.stylesheet(StylesConstant.STYLE_MAC_CODE_CSS, "mac-style");
        }
        if (stylesConfig.getEnableAutoIncrement()) {
            extra.stylesheet(StylesConstant.STYLE_AUTO_INCREMENT_CSS, "auto-increment");
        }
        return extra.getScript() + "\n" + content;
    }

    public String getHeader() {
        return "";
    }
}
