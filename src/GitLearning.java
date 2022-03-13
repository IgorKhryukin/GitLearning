import java.io.*;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GitLearning {
    public static final char[] ALPHABET = {'А', 'а', 'Б', 'б', 'В', 'в', 'Г', 'г', 'Д', 'д', 'Е', 'е', 'Ё', 'ё'
            , 'Ж', 'ж', 'З', 'з', 'И', 'и', 'Й', 'й', 'К', 'к', 'Л', 'л', 'М', 'м', 'Н', 'н', 'О', 'о', 'П', 'п', 'Р', 'р', 'С', 'с'
            , 'Т', 'т', 'У', 'у', 'Ф', 'ф', 'Х', 'х', 'Ц', 'ц', 'Ч', 'ч', 'Ш', 'ш', 'Щ', 'щ', 'Ъ', 'ъ', 'Ы', 'ы', 'Ь', 'ь', 'Э', 'э'
            , 'Ю', 'ю', 'Я', 'я', '.', ',', '«', '»', ':', '!', '?', ' '};

    public static void main(String[] args) {

        int key = 7;
        Path path = Path.of("Texts\\test.txt");
        Path path2 = Path.of("Texts\\encode.txt");

        encodeFunction(path, key);
        //decodeFunction(path2, key);

        //Map<Character, Integer> testMap = charsCounter(path);
        //System.out.println(testMap);
        //Map<Character, Integer> encodeMap = charsCounter(path2);
        //System.out.println(encodeMap);

        bruteForceDecoder(path2);
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(String.valueOf(path)))){
            String str = bufferedReader.readLine();
            if(str.contains(" и ") || str.contains(" в ") || str.contains(" с ") || str.contains(" о ")){
                System.out.println(true);
            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void bruteForceDecoder(Path path){
        boolean flag = false;
        for (int i = 1; i < ALPHABET.length; i++) {
            decodeFunction(path, i);
            try(BufferedReader bufferedReader = new BufferedReader(new FileReader("Texts\\decode.txt"))) {
                while(bufferedReader.ready()) {
                    String str = bufferedReader.readLine();
                    if (str.contains(" и ") || str.contains(" в ") || str.contains(" с ") || str.contains(" о ") || str.contains(" на ")) {
                        if (str.contains(". ") || str.contains("! ") || str.contains(", ")) {
                            System.out.println("Файл раскодирован");
                            flag = true;
                            break;
                        }
                    }
                }
                if(flag){
                    break;
                }
            } catch (IOException e) {
                System.out.println("File not found");
            }
        }
    }
    //Подсчет букв и символов в тексте
    public static Map<Character, Integer> charsCounter(Path path){
        Map<Character, Integer> charsMap = new HashMap<>();
        for (char c : ALPHABET) {
            charsMap.put(c, 0);
        }

        try(FileReader fileReader = new FileReader(String.valueOf(path))){
            while (fileReader.ready()) {
                char temp = (char) fileReader.read();
                if (charsMap.containsKey(temp)) {
                    int count = charsMap.get(temp);
                    count++;
                    charsMap.put(temp, count);
                }

            }
        } catch (IOException e){
            System.out.println("File not found");
        }
        return charsMap;
    }

    public static void encodeFunction(Path filePath, int key){
        Path path2 = Path.of("Texts\\encode.txt");

        try(FileReader fileReader = new FileReader(String.valueOf(filePath));
            FileWriter fileWriter = new FileWriter(String.valueOf(path2))){
            while(fileReader.ready()){
                char temp = (char) fileReader.read();
                /*if(Arrays.asList(ALPHABET).contains(temp)) {
                    for (int i = 0; i < ALPHABET.length; i++) {
                        if (temp == ALPHABET[i]) {
                            if (i + key >= ALPHABET.length) {
                                fileWriter.write(ALPHABET[i + key - ALPHABET.length]);
                            } else {
                                fileWriter.write(ALPHABET[i + key]);
                            }
                        }
                    }
                } else {
                    fileWriter.write(temp);
                }*/
                for (int i = 0; i < ALPHABET.length; i++) {
                    if (temp == ALPHABET[i]) {
                        if (i + key >= ALPHABET.length) {
                            fileWriter.write(ALPHABET[i + key - ALPHABET.length]);
                        } else {
                            fileWriter.write(ALPHABET[i + key]);
                        }
                    }
                }
            }
        } catch (IOException e){
            System.out.println("Файл не найден");
        }
        System.out.println("Закодированный файл можно найти по пути - " + path2.toAbsolutePath());
    }

    public static void decodeFunction(Path filePath, int key){
        Path path2 = Path.of("Texts\\decode.txt");

        try(FileReader fileReader = new FileReader(String.valueOf(filePath));
            FileWriter fileWriter = new FileWriter(String.valueOf(path2))){
            while (fileReader.ready()){
                char temp = (char) fileReader.read();
                for (int i = 0; i < ALPHABET.length; i++) {
                    if(temp == ALPHABET[i]){
                        if(i - key < 0){
                            fileWriter.write(ALPHABET[i - key + ALPHABET.length]);
                        } else {
                            fileWriter.write(ALPHABET[i - key]);
                        }
                    }
                }
            }
        } catch (IOException e){
            System.out.println("Файл не найден");
        }
        System.out.println("Раскодированный файл можно найти по пути - " + path2.toAbsolutePath());
    }

    public static Map<Character, Integer> charsCounterPercent(Path path){
        Map<Character, Integer> charsMap = new HashMap<>();
        for (char c : ALPHABET) {
            charsMap.put(c, 0);
        }
        try(FileReader fileReader = new FileReader(String.valueOf(path))){
            while (fileReader.ready()) {
                char temp = (char) fileReader.read();
                if (charsMap.containsKey(temp)) {
                    int count = charsMap.get(temp);
                    count++;
                    charsMap.put(temp, count);
                }

            }
        } catch (IOException e){
            System.out.println("File not found");
        }
        for (char c : charsMap.keySet()) {
            charsMap.put(c, (charsMap.get(c)*100)%1000);
        }
        return charsMap;
    }
}