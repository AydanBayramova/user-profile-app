
//package az.edu.turing.msuser.validators;
//
//import az.edu.turing.msuser.annotation.EmailOrPhoneRequired;
//import az.edu.turing.msuser.domain.entity.ProfileEntity;
//import az.edu.turing.msuser.model.dto.ProfileDto;
//import jakarta.validation.ConstraintValidator;
//import jakarta.validation.ConstraintValidatorContext;
//
//public class EmailOrPhoneValidator implements ConstraintValidator<EmailOrPhoneRequired, ProfileDto> {
//
//    @Override
//    public boolean isValid(ProfileDto profileDto, ConstraintValidatorContext context) {
//        if (profileDto == null) {
//            return false;
//        }
//        boolean hasEmail = profileDto.getEmail() != null && !profileDto.getEmail().trim().isEmpty();
//        boolean hasPhoneNumber = profileDto.getPhoneNumber() != null && !profileDto.getPhoneNumber().trim().isEmpty();
//
//        if (!hasEmail && !hasPhoneNumber) {
//            context.disableDefaultConstraintViolation();
//            context.buildConstraintViolationWithTemplate("Either email or phone number must be provided.")
//                    .addPropertyNode("email")
//                    .addConstraintViolation();
//            System.out.println("testgtygiudwvldvc");
//            return false;
//        }
//        System.out.println("ygdiygdeid7e38d");
//        return true; // Əgər biri doludursa, validator true qaytarır.
//    }
//
//
//}

