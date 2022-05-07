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
import org.junit.jupiter.api.DisplayNameGenerator.IndicativeSentences;

import java.lang.annotation.*;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

/**
 * {@code @IndicativeSentencesGeneration} is used to register the
 * {@link IndicativeSentences} display name generator and configure it.
 *
 * <p>The {@link #separator} for sentence fragments and the display name
 * {@link #generator} for sentence fragments are configurable. If this annotation
 * is declared without any attributes &mdash; for example,
 * {@code @IndicativeSentencesGeneration} or {@code @IndicativeSentencesGeneration()}
 * &mdash; the default configuration will be used.
 *
 * <p>This annotation is <em>inherited</em> from superclasses and implemented
 * interfaces. It is also inherited from {@linkplain Class#getEnclosingClass()
 * enclosing classes} for {@link Nested @Nested} test classes.
 *
 * @since 5.7
 * @see DisplayName
 * @see DisplayNameGenerator
 * @see IndicativeSentences
 * @see DisplayNameGeneration
 */
@DisplayNameGeneration(IndicativeSentences.class)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@API(status = EXPERIMENTAL, since = "5.7")
public @interface IndicativeSentencesGeneration {

	String DEFAULT_SEPARATOR = ", ";

	Class<? extends DisplayNameGenerator> DEFAULT_GENERATOR = DisplayNameGenerator.Standard.class;

	/**
	 * Custom separator for sentence fragments.
	 *
	 * <p>Defaults to {@value #DEFAULT_SEPARATOR}.
	 */
	String separator() default DEFAULT_SEPARATOR;

	/**
	 * Custom display name generator to use for sentence fragments.
	 *
	 * <p>Defaults to {@link DisplayNameGenerator.Standard}.
	 */
	Class<? extends DisplayNameGenerator> generator() default DisplayNameGenerator.Standard.class;

}