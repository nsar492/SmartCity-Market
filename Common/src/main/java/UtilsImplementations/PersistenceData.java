package UtilsImplementations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/** PersistenceData - annotation for reletive path
 * @author Shimon Azulay
 * @since 2016-03-28*/

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PersistenceData {
	String relativePath();
}
