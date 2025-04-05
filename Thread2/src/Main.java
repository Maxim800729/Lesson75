//Читаем файл и анализируем символы через ASCII  .
//
//Нужно посчитать:
//
//🔤 Количество слов
//
//🔢 Количество чисел
//
//📝 Количество предложений
//
//🅰️ Количество гласных
//
//🅱️ Количество согласных
//
//➕ Количество знаков препинания (точка, запятая, !, ?, ;, : )
//
// ⏱ Время выполнения
//
//Использовать потоки

import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        long start = System.currentTimeMillis();

        String content = new String(Files.readAllBytes
                (Paths.get("D:\\Java\\Lesson75\\Thread2\\src\\text.txt")),
                StandardCharsets.UTF_8);

        ExecutorService executor = Executors.newFixedThreadPool(6);

        executor.submit(() -> {
            int words = content.trim().split("\\s+").length;
            System.out.println("Слов" + words);
        });
        executor.submit(() -> {
            int numbers = 0;
            for (String word : content.split("\\s+")) {
                if (word.matches("\\d+")) numbers++;

            }
            System.out.println("Чисел" + numbers);
        });
        executor.submit(() -> {
            int sentences = content.split("[.!?]").length;
            System.out.println("Предложений" + sentences);
        });

        executor.submit(() -> {
            int vowels = 0;
            for (char ch : content.toLowerCase().toCharArray()) {
                if ("аеёиоуыэюяaieou".indexOf(ch) != -1) vowels++;
            }
            System.out.println("Гласных" + vowels);
        });

        executor.submit(() -> {
            int consonants = 0;
            for (char ch : content.toLowerCase().toCharArray()) {
                if ("бвгджзклмнпрстфхцчшщbcdfjhgklmnpqrstvwxyz".indexOf(ch) != -1)consonants++;
            }
            System.out.println("Согласных" + consonants);
        });
        executor.submit(() -> {
            int punctuation = 0;
            for (char ch : content.toCharArray()) {
                if (".,!?:;".indexOf(ch) != -1) punctuation++;
            }
            System.out.println("Знаков препинания" + punctuation);

        });

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        long end = System.currentTimeMillis();
        System.out.println("Время выполнеия: " + (end - start) + "мс");

    }
}