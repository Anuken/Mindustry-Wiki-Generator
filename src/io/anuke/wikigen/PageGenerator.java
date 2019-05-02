package io.anuke.wikigen;

import io.anuke.arc.util.Log;
import io.anuke.mindustry.Vars;
import io.anuke.mindustry.type.ContentType;
import org.reflections.Reflections;

public class PageGenerator{

    public static void generate(){
        Reflections reflections = new Reflections("io.anuke.wikigen.generators");
        reflections.getTypesAnnotatedWith(Generates.class).forEach(type -> {
            try{
                ContentType content = type.getAnnotation(Generates.class).value();
                Log.info("Generating content of type {0}...", content);
                FileGenerator<?> generator = (FileGenerator)type.newInstance();
                generator.generate(Vars.content.getBy(content));
            }catch(Exception e){
                throw new RuntimeException(e);
            }
        });
    }
}