package id.msams.webrepo.ctr.abs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.toedter.spring.hateoas.jsonapi.MediaTypes;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@RequestMapping(produces = MediaTypes.JSON_API_VALUE)
public @interface JsonApiRequestMapping {

  @AliasFor(annotation = RequestMapping.class, attribute = "name")
  String name() default "";

  @AliasFor(annotation = RequestMapping.class, attribute = "value")
  String[] value() default {};

  @AliasFor(annotation = RequestMapping.class, attribute = "path")
  String[] path() default {};

  @AliasFor(annotation = RequestMapping.class, attribute = "params")
  String[] params() default {};

  @AliasFor(annotation = RequestMapping.class, attribute = "headers")
  String[] headers() default {};

  @AliasFor(annotation = RequestMapping.class, attribute = "consumes")
  String[] consumes() default {};

}
