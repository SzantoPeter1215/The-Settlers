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
 * {@code @Tags} is a container for one or more {@link Tag @Tag} declarations.
 *
 * <p>Note, however, that use of the {@code @Tags} container is completely
 * optional since {@code @Tag} is a {@linkplain java.lang.annotation.Repeatable
 * repeatable} annotation.
 *
 * @since 5.0
 * @see Tag
 * @see java.lang.annotation.Repeatable
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@API(status = STABLE, since = "5.0")
public @interface Tags {

	/**
	 * An array of one or more {@link Tag Tags}.
	 */
	Tag[] value();

}
