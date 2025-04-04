package br.com.mendes.controle_gastos.infraestructure.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Component
public class ObjectMapperUtil {

    private static final ModelMapper MODEL_MAPPER;
    private static final Logger logger = LoggerFactory.getLogger(ObjectMapperUtil.class);

    static{
        MODEL_MAPPER = new ModelMapper();
        MODEL_MAPPER.getConfiguration()
                .setAmbiguityIgnored(true)
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
    }

    public <Input, Output> Output map(final Input object,
                                      final Class<Output> clazz){
        try {
            if (clazz != null && isRecord(clazz)) {
                return mapToRecord(object, clazz);
            }
            return MODEL_MAPPER.map(object, clazz);

        } catch (Exception e) {
            String className = clazz != null ? clazz.getName() : null;
            logger.error("Erro ao mapear objeto para a classe: {}. Detalhes: {}", className, e.getMessage(), e);
            return null;
        }
    }

    private <Input, Output> Output mapToRecord(Input object, Class<Output> clazz) throws Exception {
        Constructor<?> constructor = clazz.getDeclaredConstructors()[0];
        constructor.setAccessible(true);

        Class<?>[] parameterTypes = constructor.getParameterTypes();
        Object[] initArgs = new Object[parameterTypes.length];

        for (int i = 0; i < parameterTypes.length; i++) {
            String paramName = constructor.getParameters()[i].getName();
            initArgs[i] = getFieldValue(object, paramName);
        }

        Object instance = constructor.newInstance(initArgs);

        if (clazz.isInstance(instance)) {
            return clazz.cast(instance);
        } else {
            throw new ClassCastException("Falha ao mapear para o tipo " + clazz.getName());
        }
    }

    private Object getFieldValue(Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(object);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass(); // Verifica a superclasse
            }
        }
        throw new NoSuchFieldException("Campo " + fieldName + " não encontrado em " + object.getClass().getName());
    }

    public <Source, Target> Target map(final Source s, Target t){

        if (isRecord(t.getClass())) {
            try {
                return mapToRecord(s, (Class<Target>) t.getClass());
            } catch (Exception e) {
                logger.error("Erro ao mapear objeto para Record: {}", t.getClass().getName(), e);
                return null;
            }
        }

        try {
            for (Field sourceField : s.getClass().getDeclaredFields()) {
                boolean fieldExists = Arrays.stream(t.getClass().getDeclaredFields())
                        .anyMatch(f -> f.getName().equals(sourceField.getName()));

                if (!fieldExists)
                    continue;

                Field targetField = t.getClass().getDeclaredField(sourceField.getName());
                sourceField.setAccessible(true);
                targetField.setAccessible(true);

                Object value = sourceField.get(s);
                targetField.set(t, value);
            }
        } catch (Exception ex) {
            logger.error("Erro ao mapear objeto de {} para {}.", s.getClass().getName(), t.getClass().getName(), ex);
        }


        return t;

    }

    private boolean isRecord(Class<?> clazz){
        return clazz.isRecord();
    }

    public <Input, Output> Function<Input, Output> mapFn(final Class<Output> clazz) {
        return object -> map(object, clazz);
    }

    public <Input, Output> List<Output> mapAll(final Collection<Input> objectList, Class<Output> out) {
        return objectList.stream()
                .map(obj -> map(obj, out))
                .toList();
    }

    public <Input, Output> Function<List<Input>, List<Output>> mapAllFn(final Class<Output> clazz) {
        return objectList -> objectList.stream()
                .map(object -> map(object, clazz))
                .toList();
    }
}
