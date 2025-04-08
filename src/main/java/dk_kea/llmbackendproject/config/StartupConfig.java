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
        // Provide a query string here for the recipe generation
   //     String query = "red thai curry"; // Example query
   //     chatGPTRequestService.generateRecipeWithSchema(query).subscribe(
   //             System.out::println,
   //             error -> System.err.println("Error: " + error.getMessage()) // Handle any errors
   //     );

//        chatGPTRequestService.generateRecipeWithSchema().subscribe(System.out::println);
    }

}