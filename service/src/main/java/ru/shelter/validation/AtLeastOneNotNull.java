package ru.shelter.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

// Указание, что хотя бы одно из полей должно быть не пустым
// Пример: в посте должно быть изображение или текст
@Target({ElementType.TYPE}) // Применяется к классу, а не полю
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AtLeastOneNotNullValidator.class)
public @interface AtLeastOneNotNull {
    String message() default "Хотя бы одно из полей должно быть заполнено";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String[] fieldNames(); // Названия полей для проверки
}