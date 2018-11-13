package com.softserve.presentations.annotationExample;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ${JDEEK} on ${11.11.2018}.
 */
public class UseCaseTracker {

    public static void trackUseCases(List<Integer> useCases, Class<?> c1){
        for (Method m : c1.getDeclaredMethods()){
            UseCase uc = m.getAnnotation(UseCase.class);

            if (uc!= null){
                System.out.println("Found UseCase: "+uc.id() + " "
                                  + uc.description());
                useCases.remove(new Integer(uc.id()));
            }
        }

        for (int i : useCases){
            System.out.println("Warning: Missing use case- "+ i);
        }
    }

    public static void main(String[] args){
        List<Integer> useCases = new ArrayList<>();
        Collections.addAll(useCases,47,48,49);
        trackUseCases(useCases,PasswordUtil.class);
    }
}
