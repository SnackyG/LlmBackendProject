package dk_kea.llmbackendproject.chat_gpt;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
public class ChatGPTRequestReturningJSON extends ChatGPTRequest {
    private List<Tool> tools;
    private ToolChoice tool_choice;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Tool {
        private String type;
        private Function function;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Function {
        private String name;
        private Parameters parameters;

        // Setting strict to true will ensure function calls reliably adhere to the function schema, instead of being best effort
        private boolean strict = true;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ToolChoice {
        private String type;
        private Function function;

        @Getter
        @Setter
        @AllArgsConstructor
        public static class Function {
            private String name;
        }
    }
}

