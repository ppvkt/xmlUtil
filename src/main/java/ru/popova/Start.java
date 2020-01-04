package ru.popova;
/**
 *1. Сделать runnable jar файл который бы получал как входные данные: имя xml файла,
 *  имя xsd файла(по этой схеме должен быть сформирован xml файл), имя xslt файла и
 *  имя результирующего файла(тоже хмл). Приложение должно взять xml файл по имени,
 *  провалидировать его по xsd схеме, затем произвести xslt трансформацию по xslt,
 *  затем результат провалидировать по xsd схеме(результирующий хмл также должен
 *  соответствовать некоему классу их xsd схемы) и сохранить результирующий xml в файл.
 *  Нужно предусмотреть обработку эксепшенов и ведение лога по каждой операции
 *  (валидация входящего хмл, xslt трансформация, валидация результата).
 * 2. Сделать юнит тесты на JUnit 5.
 * 3. Вычислить покрытие кода тестами используя JaCoCo.
 * 4. Покрытие должно быть 100%, либо объяснено почему 100% недостижимо.
 * @author epopova
 * @since 11/14/2019
 */
import java.io.File;
import java.util.Scanner;

public class Start {

    public static void main(String[] args) {
        System.out.println("логирование в консоль и в файл myJavaLog.txt;");
        System.out.println("введите имена файлов без расширения, через пробел, в данной последовательности:");
        System.out.println("xml(исходный) xsd(схема для исходного) xsd2(схема после трансформации) xsl(трасформация) xml(итоговый)");
        Scanner sc = new Scanner(System.in);
        String xmlFilename = sc.next() + ".xml";
        String schemaFilename = sc.next() + ".xsd";
        String schemaFilename2 = sc.next() + ".xsd";
        String xsltFilename = sc.next() + ".xsl";
        String resFileName = sc.next() + ".xml";
        sc.close();
        String path = new File("").getAbsolutePath();
        if (!new File(path, schemaFilename).exists()
                || !new File(path, xmlFilename).exists()
                || !new File(path, xsltFilename).exists()) {
            throw new IllegalArgumentException("Какого-то файла не существует");
        }
        Out out = new Out();
        out.run(xmlFilename, schemaFilename, schemaFilename2, xsltFilename, resFileName);
    }
}