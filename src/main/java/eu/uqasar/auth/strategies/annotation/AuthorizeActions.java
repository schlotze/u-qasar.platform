/*
 */

package eu.uqasar.auth.strategies.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Documented
@Inherited
public @interface AuthorizeActions {

	/**
	 * The actions that are allowed.
	 * 
	 * @return the allowed actions
	 */
	AuthorizeAction[] actions();
}
