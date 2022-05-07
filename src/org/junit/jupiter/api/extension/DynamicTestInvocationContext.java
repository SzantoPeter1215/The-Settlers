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

import org.junit.jupiter.api.DynamicTest;
import org.apiguardian.api.API;
import org.junit.jupiter.api.function.Executable;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

/**
 * {@code DynamicTestInvocationContext} represents the <em>context</em> of a
 * single invocation of a {@linkplain DynamicTest
 * dynamic test}.
 *
 * @since 5.8
 * @see DynamicTest
 */
@API(status = EXPERIMENTAL, since = "5.8")
public interface DynamicTestInvocationContext {

	/**
	 * Get the {@code Executable} of this dynamic test invocation context.
	 *
	 * @return the executable of the dynamic test invocation, never {@code null}
	 */
	Executable getExecutable();

}
