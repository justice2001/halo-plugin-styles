package run.halo.starter;

/**
 * Script Builder
 * 用于构造Script、内嵌Script、样式表
 * @author zhengyi59
 */
public class ScriptBuilder {
    private final StringBuilder script;

    private final String basePath;

    private final String version;

    ScriptBuilder(String basePath, String version) {
        this.script = new StringBuilder();
        this.basePath = basePath;
        this.version = version;
    }

    public ScriptBuilder script(String path, String id) {
        this.script.append("<script src=\"%s?version=%s\" id=\"%s\"></script>"
            .formatted(getUrl(path), this.version, id));
        return this;
    }

    public ScriptBuilder innerScript(String script) {
        this.script.append("<script>%s</script>".formatted(script));
        return this;
    }

    public ScriptBuilder stylesheet(String path, String id) {
        this.script.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"%s?version=%s\" id=\"%s\" />"
            .formatted(getUrl(path), this.version, id));
        return  this;
    }

    public ScriptBuilder sign(String signId) {
        this.script.append("<div data-type=\"sign\" id=\"%s\"></div>".formatted(signId));
        return this;
    }

    public ScriptBuilder variable(String varId, String varValue) {
        this.script.append("<div data-type=\"var\" id=\"var-%s\" value=\"%s\"></div>"
            .formatted(varId, varValue));
        return this;
    }

    public String getScript() {
        return this.script.toString();
    }

    private String getUrl(String url) {
        if (url.startsWith("http")) {
            return url;
        } else {
            return "/plugins/%s/assets/static/%s".formatted(this.basePath, url);
        }
    }
}