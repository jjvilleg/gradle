/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gradle.internal.component.local.model;

import org.gradle.api.artifacts.component.ComponentIdentifier;
import org.gradle.internal.component.model.Exclude;
import org.gradle.internal.component.model.ExcludeMetadata;
import org.gradle.internal.component.model.LocalOriginDependencyMetadata;

// TODO:DAZ Remove redundant 'configuration' parameters.
public interface BuildableLocalConfigurationMetadata {
    /**
     * Returns the identifier for this component.
     */
    ComponentIdentifier getComponentId();

    /**
     * Adds a dependency to this component. Dependencies are attached to the configuration specified by {@link LocalOriginDependencyMetadata#getModuleConfiguration()} and each of its children.
     */
    void addDependency(LocalOriginDependencyMetadata dependency);

    /**
     * Adds an exclude rule to this component. Exclude rules are attached to the configurations specified by {@link Exclude#getConfigurations()} and each of their children.
     */
    void addExclude(String configuration, ExcludeMetadata exclude);

    /**
     * Adds some files to this component.  The files are attached to the given configuration and each of its children.
     *
     * These files should be treated as dependencies of this component, however they are currently treated separately as a migration step.
     */
    void addFiles(String configuration, LocalFileDependencyMetadata files);
}
