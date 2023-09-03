import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Задание №3
 * Напишите приложение, которое будет запрашивать у пользователя следующие
 * данные, разделенные пробелом:
 * Фамилия Имя Отчество номертелефона
 * 
 * Форматы данных:
 * фамилия, имя, отчество - строки
 * номертелефона - целое беззнаковое число без форматирования
 * 
 * Ввод всех элементов через пробел
 * 
 * Приложение должно проверить введенные данные по количеству. Если количество
 * не совпадает с требуемым, вернуть код ошибки, обработать его и показать
 * пользователю сообщение, что он ввел меньше и больше данных, чем требуется.
 * 
 * Приложение должно попытаться распарсить полученные значения и выделить из них
 * требуемые параметры. Если форматы данных не совпадают, нужно бросить
 * исключение, соответствующее типу проблемы. Можно использовать встроенные типы
 * java и создать свои. Исключение должно быть корректно обработано,
 * пользователю выведено сообщение с информацией, что именно неверно.
 * 
 * Если всё введено и обработано верно, должен создаться файл с названием,
 * равным фамилии, в него в одну строку должны записаться полученные данные,
 * вида
 * 
 * <Фамилия><Имя><Отчество><номер_телефона>
 * 
 * Однофамильцы должны записаться в один и тот же файл, в отдельные строки.
 * Не забудьте закрыть соединение с файлом.
 * 
 * При возникновении проблемы с чтением-записью в файл, исключение должно быть
 * корректно обработано, пользователь должен увидеть стектрейс ошибки.
 */

public class Program {

  static Scanner scan = new Scanner(System.in, "866");

  public static String[] addNewData() throws MyNotFullDataException, MyWrongFormatException {
    String[] newAbonent;
    System.out.println("\nВведите данные пользователя, разделенные пробелом (Фамилия_Имя_Отчество_НомерТелефона). ");
    newAbonent = scan.nextLine().split(" ");
    if (newAbonent.length != 4) {
      throw new MyNotFullDataException(newAbonent.length);
    }
    if (!newAbonent[0].matches("^[а-яА-Яa-zA-Z]*$")) {
      throw new MyWrongFormatException("Surname entered incorrectly. ");
    }
    if (!newAbonent[1].matches("^[а-яА-Яa-zA-Z]*$")) {
      throw new MyWrongFormatException("Name entered incorrectly. ");
    }
    if (!newAbonent[2].matches("^[а-яА-Яa-zA-Z]*$")) {
      throw new MyWrongFormatException("Patronymic entered incorrectly. ");
    }
    for (int i = 0; i < newAbonent[3].length(); i++) {
      if (!Character.isDigit(newAbonent[3].charAt(i))) {
        throw new MyWrongFormatException("Phone number entered incorrectly. ");
      }
    }
    return newAbonent;
  }

  public static void saveInFile(String[] data) {
    try (FileWriter fileWriter = new FileWriter(data[0] + ".txt", true)) {
      fileWriter.append("<" + data[0] + "> <" + data[1] + "> <" + data[2] + "> <" + data[3] + "> \n");
    } catch (IOException ex) {
      System.out.println(ex.getMessage() + " Save error. Try again. ");
    }
  }

  public static void main(String[] args) {
    String[] data = null;
    boolean flag = true;
    while (flag) {
      try {
        data = addNewData();
        flag = false;
      } catch (MyNotFullDataException e) {
        System.out.println(e.getMessage() + "Enter the data again. ");
      } catch (MyWrongFormatException e) {
        System.out.println(e.getMessage() + "Enter the data again. ");
      }
    }
    saveInFile(data);
  }
}

class MyWrongFormatException extends Exception {
  public MyWrongFormatException(String message) {
    super("Incorrect data format. " + message);
  }
}

class MyNotFullDataException extends Exception {
  public MyNotFullDataException(int number) {
    super("Exception: Not enough/too much information has been entered. You have filled in " + number
        + " fields. It is necessary to fill in 4 fields. ");
  }
}
