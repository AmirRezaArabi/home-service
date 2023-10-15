package validation;

import exception.TheInputInformationIsNotValidException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import java.util.Set;

public class EntityValidator {
        public static final ValidatorFactory validatorFactory = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator()).buildValidatorFactory();

        public static final Validator validator = validatorFactory.usingContext()
                .messageInterpolator(new ParameterMessageInterpolator()).getValidator();



        public static <T> boolean isValid(T t){
            Set<ConstraintViolation<T>> violations = validator.validate(t);
            if (!violations.isEmpty()) {
                for (ConstraintViolation<T> p : violations) {
                    System.out.println(p.getMessage());
                    throw new TheInputInformationIsNotValidException("the input information is not correct");
                }
                return false;
            }
            return true;
        }
    }

