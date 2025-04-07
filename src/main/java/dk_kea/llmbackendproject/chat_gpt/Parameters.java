package dk_kea.llmbackendproject.chat_gpt;

import java.util.List;
import java.util.Map;

public interface Parameters {
    Map<String, Object> getProperties();

    // All properties must be marked required when strict is true
    // see https://platform.openai.com/docs/guides/function-calling?api-mode=chat&lang=javascript&strict-mode=enabled#strict-mode
    List<String> getRequired();
    default String getType() {
        return "object";
    }

    // additionalProperties must be set to false for each object in the parameters when strict is true for function
    // see https://platform.openai.com/docs/guides/function-calling?api-mode=chat&lang=javascript&strict-mode=enabled#strict-mode
    default boolean getAdditionalProperties() {
        return false;
    }
}
