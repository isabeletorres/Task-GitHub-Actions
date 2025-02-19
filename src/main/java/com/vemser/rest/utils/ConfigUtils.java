package com.vemser.rest.utils;

import com.vemser.rest.data.factory.LoginDataFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtils {

    private ConfigUtils() {
    }

    public static Properties loadProperties(){
        try(InputStream is = LoginDataFactory.class.getClassLoader().getResourceAsStream("data.properties")){
            if (is == null){
                throw new FileNotFoundException("Arquivo de propriedades não encontrado");
            }
            Properties props = new Properties();
            props.load(is);
            return props;
        } catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String getUsernameAdmin(){
        return System.getenv("USERNAME_ADMIN");
    }

    public static String getUsernameUser(){
        return System.getenv("USERNAME_USER");
    }

    public static String getPasswordAdmin(){
        return System.getenv("PASSWORD_ADMIN");
    }

    public static String getPasswordUser(){
        return System.getenv("PASSWORD_USER");
    }
}
