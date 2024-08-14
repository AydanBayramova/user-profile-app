package az.edu.turing.msuser.validators;

import az.edu.turing.msuser.annotation.EmailOrPhoneRequired;
import az.edu.turing.msuser.domain.entity.ProfileEntity;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailOrPhoneValidator implements ConstraintValidator<EmailOrPhoneRequired, ProfileEntity> {

    @Override
    public boolean isValid(ProfileEntity profileEntity, ConstraintValidatorContext constraintValidatorContext) {
        if (profileEntity == null) {
            return false;
        }
        return profileEntity.getEmail() != null && !profileEntity.getEmail().isEmpty()
                || profileEntity.getPhoneNumber() != null && !profileEntity.getPhoneNumber().isEmpty();
    }
}
