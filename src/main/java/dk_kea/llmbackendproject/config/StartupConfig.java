package dk_kea.llmbackendproject.config;




import dk_kea.llmbackendproject.service.ChatGPTRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupConfig implements CommandLineRunner {
    private final ChatGPTRequestService chatGPTRequestService;

    @Autowired
    public StartupConfig(ChatGPTRequestService chatGPTRequestService) {
        this.chatGPTRequestService = chatGPTRequestService;
    }

    @Override
    public void run(String... args) {
        chatGPTRequestService.generateRecipeWithSchema().subscribe(System.out::println);
    }
}
