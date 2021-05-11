package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

  public static final String REGEX_EMPTY = "^\\s*$"; // chaine vide

  public static Pattern EMAIL_REGEX = Pattern.compile(
      "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

  public static Pattern REGEX_TEL = Pattern.compile("^0[1-9]{9}");

  public static Pattern REGEX_NUM_COMPTE_BANCAIRE = Pattern.compile("^BE[0-9]{14}");

  public static Pattern REGEX_CODE_BIC = Pattern.compile("^[A-Z]{8}$");

  /**
   * Vérifie si le paramètre correspond au format d'une adresse email.
   * 
   * @param email à vérifier
   * @return vrai si le paramètre correspond au format d'une adresse email
   */
  public static boolean validateEmail(String email) {
    Matcher match = EMAIL_REGEX.matcher(email);
    if (match.matches()) {
      return true;
    }
    return false;
  }

  public static boolean validateTel(String tel) {
    Matcher match = REGEX_TEL.matcher(tel);
    if (match.matches()) {
      return true;
    }
    return false;
  }

  public static boolean validateCompteBancaire(String compte) {
    Matcher match = REGEX_NUM_COMPTE_BANCAIRE.matcher(compte);
    if (match.matches()) {
      return true;
    }
    return false;
  }

  public static boolean validateCodeBic(String codeBic) {
    Matcher match = REGEX_CODE_BIC.matcher(codeBic);
    if (match.matches()) {
      return true;
    }
    return false;
  }

  /**
   * Lance une exception si le paramètre est null.
   * 
   * @param obj à tester
   */
  public static void checkObject(Object obj) {
    if (obj == null) {
      throw new IllegalArgumentException("Objet null !");
    }
  }

  /**
   * Lance une exception si le paramètre est une chaine vide ou null.
   * 
   * @param stringToCheck à tester
   */
  public static void checkString(String stringToCheck) {
    checkObject(stringToCheck);
    if (stringToCheck.matches(REGEX_EMPTY)) {
      throw new IllegalArgumentException("Chaine vide");
    }
  }

  /**
   * Lance une exception si le paramètre est un format numérique incorrect ou null.
   * 
   * @param stringNumber à tester
   */
  public static void checkNumber(String stringNumber) {
    checkString(stringNumber);
    try {
      Long.parseLong(stringNumber);
    } catch (NumberFormatException exception) {
      throw new IllegalArgumentException("Format numéro incorrect");
    }
  }

  /**
   * Lance une exception si le paramètre est négatif ou null.
   * 
   * @param number à tester
   */
  public static boolean checkPositive(int number) {
    if (number <= 0) {
      return false;
    }
    return true;
  }

  /**
   * Valide une string.
   * 
   * @param stringToCheck à tester
   * @return renvoie faux si la string est null ou vide ou sinon renvoie vrai
   */
  public static boolean validateString(String stringToCheck) {
    if (stringToCheck == null) {
      return false;
    }
    if (stringToCheck.equals("")) {
      return false;
    }
    if (stringToCheck.matches(REGEX_EMPTY)) {
      return false;
    }
    return true;
  }

  public static void loadProperties(Properties prop, InputStream input) {

    try {

      input = new FileInputStream("prod.properties");

      prop.load(input);
    } catch (IOException ex) {
      ex.printStackTrace();
    } finally {
      if (input != null) {
        try {
          input.close();
        } catch (IOException exception) {
          exception.printStackTrace();
        }
      }
    }
  }

  @SuppressWarnings("unchecked")

  public static <T> T newObject(Class<T> className, Object... arguments)
      throws InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
    Properties prop = new Properties();
    InputStream input = null;
    loadProperties(prop, input);

    if (arguments.length == 0) {
      return (T) Class.forName((className.getName())).getConstructor().newInstance();
    } else {
      @SuppressWarnings("rawtypes")
      Class[] parameters = new Class[arguments.length];
      for (int i = 0; i < arguments.length; i++) {
        @SuppressWarnings("rawtypes")
        Class[] interfaces = arguments[i].getClass().getInterfaces();
        if (interfaces.length == 0) {
          parameters[i] = arguments[i].getClass();
        } else {
          parameters[i] = arguments[i].getClass().getInterfaces()[0];
        }
      }
      return (T) Class.forName((className.getName())).getConstructor(parameters)
          .newInstance(arguments);
    }
  }
}
