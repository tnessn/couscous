package com.github.tnessn.couscous.lang.validate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;

import org.hibernate.validator.HibernateValidator;
// TODO: Auto-generated Javadoc

/**
 * 校验参数工具类.
 *
 * @author huangjinfeng
 * <p>
 * 注解说明：
 *  <ul>
 *  <li>@AssertTrue    用于boolean字段，该字段只能为true      </li>
 *  <li>@AssertFalse    该字段的值只能为false    </li>
 *  <li>@CreditCardNumber    对信用卡号进行一个大致的验证    </li>
 *  <li>@DecimalMax    只能小于或等于该值    </li>
 *  <li>@DecimalMin    只能大于或等于该值    </li>
 *  <li>@Digits(integer=,fraction=)    检查是否是一种数字的整数、分数,小数位数的数字    </li>
 *  <li>@Email    检查是否是一个有效的email地址    </li>
 *  <li>@Future    检查该字段的日期是否是属于将来的日期    </li>
 *  <li>@Length(min=,max=)    检查所属的字段的长度是否在min和max之间,只能用于字符串    </li>
 *  <li>@Max    该字段的值只能小于或等于该值    </li>
 *  <li>@Min    该字段的值只能大于或等于该值    </li>
 *  <li>@NotNull    不能为null    </li>
 *  <li>@NotBlank    不能为空，检查时会将空格忽略    </li>
 *  <li>@NotEmpty    不能为空，这里的空是指空字符串    </li>
 *  <li>@Null    检查该字段为空    </li>
 *  <li>@Past    检查该字段的日期是在过去    </li>
 *  <li>@Pattern(regex=,flag=)    被注释的元素必须符合指定的正则表达式 </li>  
 *  <li>@Range(min=,max=,message=)    被注释的元素必须在合适的范围内     </li>
 *  <li>@Size(min=, max=)    检查该字段的size是否在min和max之间，可以是字符串、数组、集合、Map等     </li>
 *  <li>@URL(protocol=,host,port)    检查是否是一个有效的URL，如果提供了protocol，host等，则该URL还需满足提供的条件     </li>
 *  <li>@Valid    该注解主要用于字段为一个包含其他对象的集合或map或数组的字段，或该字段直接为一个其他对象的引用，这样在检查当前对象的同时也会检查该字段所引用的对象 </li>
 * </ul>
 */
public class ValidationUtil {

/** The Constant ERROR_DELIMITER. */
private final static String ERROR_DELIMITER="|";
	
    /**
     * 默认模式（非快速失败，所有验证一起返回）.
     *
     * @param <T> 泛型
     * @param bean 对象
     * @param groups 分组
     */
    public static <T> void validate(T bean, Class<?>... groups) {
        validate(bean, false, groups);
    }

    /**
     * 返回模式.
     *
     * @param <T> 泛型
     * @param bean 对象
     * @param flag true: 快速失败返回模式    false: 普通模式
     * @param groups 分组
     */
    public static <T> void validate(T bean, boolean flag, Class<?>... groups) {
        List<String> errorList=new ArrayList<>(0);

        Class<?>[] group;
        if (groups == null) {
            group = new Class<?>[1];
            group[0] = Default.class;
        } else {
            group = new Class<?>[groups.length + 1];
            System.arraycopy(groups, 0, group, 0, groups.length);
            group[groups.length] = Default.class;
        }

        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure().failFast(flag).buildValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(bean, group);
        if (!constraintViolations.isEmpty()) {
            for (ConstraintViolation<T> constraint : constraintViolations) {
            	errorList.add(constraint.getMessage());
            }
        }

        if (errorList.size() > 0) {
            throw new IllegalArgumentException(String.join(ERROR_DELIMITER, errorList));
        }
    }
}
