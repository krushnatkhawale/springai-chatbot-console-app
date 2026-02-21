# Spring AI Chatbot (Console)

TL;DR

A tiny Spring Boot console chatbot demonstrating the Spring AI chat client with two pluggable providers (Ollama for local/dev and OpenAI for production). Includes a minimal service layer, a console runner, and a unit test so you can run, extend, and test a conversational service quickly.

---

## Project Overview

This repository contains a small Java + Gradle project that boots a Spring application and exposes a simple console chatbot. The app delegates conversation to `ChatbotService`, which uses Spring AI's `ChatClient` to prompt a language model. The project demonstrates:

- How to wire a chat client with Spring
- Profile-based configuration (local `dev` uses Ollama; `prod` uses OpenAI)
- How to write a small unit test around the service

Key Java classes:

- `com.krushnatkhawale.springaichatbot.App` — application entrypoint and simple console loop
- `com.krushnatkhawale.springaichatbot.ChatbotService` — encapsulates chat prompt creation and client usage
- `src/test/java/.../ChatbotServiceTest` — simple smoke test that executes the service under the `dev` profile

---

## Tech stack

- Java (project toolchain requests Java 25 in `build.gradle`)
- Gradle (Gradle wrapper included)
- Spring Boot 4.0.3
- Spring AI (spring-ai starters for OpenAI and Ollama)
- JUnit 5 for testing

Note: The project uses the Gradle Java toolchain API and specifies `JavaLanguageVersion.of(25)` in `build.gradle`. If you don't have JDK 25 installed, either install a matching JDK or change the toolchain setting to your installed Java version (for example 17 or 21).

---

## Architecture & Key Files

- `build.gradle` — plugin and dependency definitions (Spring Boot, Spring AI starters), Gradle toolchain
- `src/main/java/com/krushnatkhawale/springaichatbot/App.java` — starts Spring and runs a console input loop
- `src/main/java/com/krushnatkhawale/springaichatbot/ChatbotService.java` — builds a `PromptTemplate` and uses `ChatClient` to fetch a response
- `src/main/resources/application.yml` — profile-driven configuration for `dev` (Ollama) and `prod` (OpenAI)
- `src/test/java/.../ChatbotServiceTest.java` — JUnit test annotated with `@ActiveProfiles("dev")`

---

## Quick Start

Prerequisites

- JDK 25 (or modify `build.gradle` toolchain to match your local JDK)
- Internet (for fetching dependencies) or a configured local Maven cache
- (Optional) Ollama running locally for the `dev` profile: default `http://localhost:11434` as set in `application.yml`.

Build

```bash
# From the project root
./gradlew clean build
```

Run (development / console)

```bash
# Use Spring Boot's bootRun task (loads default profile `dev`):
./gradlew bootRun

# Or build an executable jar and run it:
./gradlew bootJar
java -jar build/libs/springai-chatbot-1.0-SNAPSHOT.jar
```

Behavior

- The console app prints `Chatbot ready. Type 'exit' to quit.` and then accepts lines of input.
- Type any message and press Enter; the bot replies with the model's output.
- Type `exit` to quit.

Test

```bash
./gradlew test
```

Configuration

Configuration is profile-driven in `application.yml`:

- `dev` profile (default): uses Ollama at `http://localhost:11434`, model `llama3.2` and a default temperature of `0.7`. OpenAI auto-configuration is excluded in this profile.
- `prod` profile: uses OpenAI (set `OPENAI_API_KEY` in your environment), model `gpt-3.5-turbo`, temperature `0.7`. Ollama auto-configuration is excluded in this profile.

Switching profiles

- To explicitly activate `prod`:

```bash
# Unix/macOS
SPRING_PROFILES_ACTIVE=prod ./gradlew bootRun

# Or pass as JVM arg
./gradlew bootRun --args='--spring.profiles.active=prod'
```

Secrets

- Set your OpenAI key in the environment when using `prod`:

```bash
export OPENAI_API_KEY=sk_...
```

---

## Usage Examples

Programmatic use (call the service in code):

```java
// ...existing imports...
@Autowired
private ChatbotService chatbotService;

String reply = chatbotService.getResponse("Hello there!");
System.out.println(reply);
```

Console transcript example

```
Chatbot ready. Type 'exit' to quit.
You: Hello
Bot: Hi there! How can I help you today?
You: Tell me a joke
Bot: Why did the programmer quit his job? Because he didn't get arrays. 😄
You: exit
```

Unit test snippet (from `ChatbotServiceTest`):

```java
@SpringBootTest
@ActiveProfiles("dev")
class ChatbotServiceTest {
    @Autowired
    private ChatbotService service;

    @Test
    void testResponse() {
        String response = service.getResponse("Hello");
        assertNotNull(response);
        assertFalse(response.isBlank());
    }
}
```

---

## Developing & Contributing

- Tests: `./gradlew test` (JUnit 5 / platform configured in `build.gradle`)
- Code style: follow simple Java conventions used in the repo; keep service logic small and testable.
- To propose changes: fork, implement a focused change, add/extend tests, and open a pull request.

Suggested small contributions:

- Add richer prompt templates and a conversation history wrapper
- Add an integration test that runs a headless Ollama container in CI
- Add a small README diagram showing data flow

---

## Suggested Visuals & Medium Article Tips

When writing a Medium post about this project include:

- A short opening paragraph that explains the goal (see the example below)
- A diagram showing App -> ChatbotService -> ChatClient -> Model
- Terminal screenshots for `./gradlew bootRun` and a sample conversation
- A small code snippet (the `ChatbotService#getResponse` implementation) to illustrate how the prompt is created and executed

Example opening paragraph for Medium:

> I built a tiny Spring-like console chatbot to explore building a lightweight conversational app using familiar Spring conventions, the Gradle wrapper, and an easily testable service layer. In this short walkthrough I’ll show how the project is organized, how to run it locally, and how to extend the core ChatbotService to connect to smarter backends.

Snippets to consider highlighting in the article:

- `ChatbotService#getResponse` (prompt template usage)
- `application.yml` profiles (dev vs prod)
- A short test that asserts the service returns non-empty text

---

## Roadmap & Next Steps

- Add history-aware prompts
- Add message streaming support (if supported by the chosen provider)
- Provide Docker/Compose for a local Ollama instance for reproducible dev setup
- Add CI workflow to run tests and optionally run a lightweight Ollama or mock server

---

## License & Acknowledgements

This project is provided under the MIT license.

---

## Maintainers / Contact

- Original author: Krushnat Khawale (see repo authorship)
