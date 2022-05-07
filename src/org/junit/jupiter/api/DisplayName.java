/*
 * Copyright 2015-2022 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v2.0 which
 * accompanies this distribution and is available at
 *
 * https://www.eclipse.org/legal/epl-v20.html
 */

package org.junit.jupiter.api;

import org.apiguardian.api.API;

import java.lang.annotation.*;

import static org.apiguardian.api.API.Status.STABLE;

/**
 * {@code @DisplayName} is used to declare a {@linkplain #value custom display
 * name} for the annotated test class or test method.
 *
 * <p>Display names are typically used for test reporting in IDEs and build
 * tools and may contain spaces, special characters, and even emoji.
 *
 * @since 5.0
 * @see Test
 * @see Tag
 * @see TestInfo
 * @see DisplayNameGeneration
 * @see DisplayNameGenerator
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@API(status = STABLE, since = "5.0")
public @interface DisplayName {

	/**
	 * Custom display name for the annotated class or method.
	 *
	 * @return a custom display name; never blank or consisting solely of
	 * whitespace
	 */
	String value();

}
