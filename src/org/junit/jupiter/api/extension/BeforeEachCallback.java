/*
 * Copyright 2015-2022 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v2.0 which
 * accompanies this distribution and is available at
 *
 * https://www.eclipse.org/legal/epl-v20.html
 */

package org.junit.jupiter.api.extension;

import org.junit.jupiter.api.BeforeEach;
import org.apiguardian.api.API;

import static org.apiguardian.api.API.Status.STABLE;

/**
 * {@code BeforeEachCallback} defines the API for {@link org.junit.jupiter.api.extension.Extension Extensions}
 * that wish to provide additional behavior to tests before an individual test
 * and any user-defined setup methods (e.g.,
 * {@link BeforeEach @BeforeEach} methods) for that test
 * have been executed.
 *
 * <p>Concrete implementations often implement {@link org.junit.jupiter.api.extension.AfterEachCallback} as well.
 * If you do not wish to have your callbacks <em>wrapped</em> around user-defined
 * setup and teardown methods, implement {@link BeforeTestExecutionCallback} and
 * {@link AfterTestExecutionCallback} instead of {@link BeforeEachCallback} and
 * {@link org.junit.jupiter.api.extension.AfterEachCallback}.
 *
 * <h3>Constructor Requirements</h3>
 *
 * <p>Consult the documentation in {@link org.junit.jupiter.api.extension.Extension} for details on
 * constructor requirements.
 *
 * <h3>Wrapping Behavior</h3>
 *
 * <p>JUnit Jupiter guarantees <em>wrapping behavior</em> for multiple
 * registered extensions that implement lifecycle callbacks such as
 * {@link org.junit.jupiter.api.extension.BeforeAllCallback}, {@link org.junit.jupiter.api.extension.AfterAllCallback},
 * {@link BeforeEachCallback}, {@link org.junit.jupiter.api.extension.AfterEachCallback},
 * {@link BeforeTestExecutionCallback}, and {@link AfterTestExecutionCallback}.
 *
 * <p>That means that, given two extensions {@code Extension1} and
 * {@code Extension2} with {@code Extension1} registered before
 * {@code Extension2}, any "before" callbacks implemented by {@code Extension1}
 * are guaranteed to execute before any "before" callbacks implemented by
 * {@code Extension2}. Similarly, given the two same two extensions registered
 * in the same order, any "after" callbacks implemented by {@code Extension1}
 * are guaranteed to execute after any "after" callbacks implemented by
 * {@code Extension2}. {@code Extension1} is therefore said to <em>wrap</em>
 * {@code Extension2}.
 *
 * @since 5.0
 * @see BeforeEach
 * @see AfterEachCallback
 * @see BeforeTestExecutionCallback
 * @see AfterTestExecutionCallback
 * @see BeforeAllCallback
 * @see AfterAllCallback
 */
@FunctionalInterface
@API(status = STABLE, since = "5.0")
public interface BeforeEachCallback extends Extension {

	/**
	 * Callback that is invoked <em>before</em> an individual test and any
	 * user-defined setup methods for that test have been executed.
	 *
	 * @param context the current extension context; never {@code null}
	 */
	void beforeEach(ExtensionContext context) throws Exception;

}
