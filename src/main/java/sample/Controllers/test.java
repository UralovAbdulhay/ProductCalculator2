package sample.Controllers;

import java.io.*;
import java.util.Properties;

import javafx.application.Application;
import javafx.stage.Stage;
import org.ini4j.*;
import sample.Classes.LocaleConfig;

public class test {

    public static void main(String[] args) {

        LocaleConfig config = LocaleConfig.getInstance();


        System.out.println(config.toString());

        System.out.println();


    }


}
