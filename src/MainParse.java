import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MainParse {
    public static void main(String[] args) {
        String json = readFile("fileIn.txt");
        String[] forAppend = new String[]{"Студент", "получил", "по предмету"};

        String[] data = cleareStringV1(json); //Вариант первый очистки строки от личшней информации

//        String[] data = cleareStringV2(json); //Вариант второй очистки строки от личшней информации

        String res = getNewParseString(data, forAppend);
        System.out.println(res);

        writeFile(res);
    }

    static String[] cleareStringV1(String json) {
        json = json.substring(2, json.length() - 2).replace("\"", "").replace("{", "")
                .replace("}", "").replace(":", "")
                .replace("фамилия", "").replace("оценка", "")
                .replace("предмет", "");
        return json.split(",");
    } //Вариант первый очистки строки от личшней информации

    static String[] cleareStringV2(String json) {
        json = json.substring(2, json.length() - 2).replace("\"", "").replace("{", "");
        String[] students = json.split("},");

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < students.length; i++) {
            String[] items = students[i].split(",");
            for (int j = 0; j < items.length; j++) {
                String[] sub = items[j].split(":");
                sb.append(sub[1] + " ");
            }
        }
        return String.valueOf(sb).split(" ");
    } //Вариант второй очистки строки от личшней информации

    static String getNewParseString(String[] data, String[] forAppend) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < data.length - 2; i += 3) {
            sb.append(forAppend[0]).append(" ");
            sb.append(data[i]);
            sb.append(" ").append(forAppend[1]).append(" ");
            sb.append(data[i + 1]);
            sb.append(" ").append(forAppend[2]).append(" ");
            sb.append(data[i + 2]).append(".");
            sb.append("\n");
        }
        return String.valueOf(sb);
    }

    static String readFile(String filePath) {
        Logger log = getLog();

        File file = new File(filePath);

        StringBuilder sb = new StringBuilder();

        try (Scanner sc = new Scanner(file)) {
            sb.append(sc.nextLine());
            log.info("Файл " + filePath + " прочитан.");
        } catch (Exception e) {
            log.warning(e.getMessage());
            e.printStackTrace();
        }
        return sb.toString();
    }

    static void writeFile(String json){
        Logger log = getLog();

        try (FileWriter fileWriter = new FileWriter("fileOut.txt", false)){
            fileWriter.write(json);
            log.info("Файл fileOut.txt записан.");
        } catch (Exception e){
            log.warning(e.getMessage());
            e.printStackTrace();
        }
    }

    static Logger getLog() {
        Logger logger = Logger.getAnonymousLogger();

        FileHandler fileHandler = null;
        SimpleFormatter format = new SimpleFormatter();

        try {
            fileHandler = new FileHandler("log.txt", true);
            fileHandler.setFormatter(format);
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.addHandler(fileHandler);

        return logger;
    }
}
