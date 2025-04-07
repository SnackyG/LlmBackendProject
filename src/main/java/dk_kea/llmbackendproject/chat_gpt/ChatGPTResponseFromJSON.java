package dk_kea.llmbackendproject.chat_gpt;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChatGPTResponseFromJSON {
    // Add more fields as needed, also to nested classes ex. usage(contains token data), model, created
    public List<Choice> choices;

    @Getter
    @Setter
    public static class Choice {
        public Message message;
    }

    @Getter
    @Setter
    public static class Message {
        public List<ToolCall> tool_calls;
    }

    @Getter
    @Setter
    public static class ToolCall {
        public Function function;
    }

    @Getter
    @Setter
    public static class Function {
        public String name;
        public String arguments;
    }
}
