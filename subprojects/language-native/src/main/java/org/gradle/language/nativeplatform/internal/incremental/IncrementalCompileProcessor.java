/*
 * Copyright 2013 the original author or authors.
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
package org.gradle.language.nativeplatform.internal.incremental;

import org.gradle.cache.PersistentStateCache;

import java.io.File;
import java.util.Collection;

public class IncrementalCompileProcessor {
    private final PersistentStateCache<CompilationState> previousCompileStateCache;
    private final IncrementalCompileFilesFactory incrementalCompileFilesFactory;

    public IncrementalCompileProcessor(PersistentStateCache<CompilationState> previousCompileStateCache, IncrementalCompileFilesFactory incrementalCompileFilesFactory) {
        this.previousCompileStateCache = previousCompileStateCache;
        this.incrementalCompileFilesFactory = incrementalCompileFilesFactory;
    }

    public IncrementalCompilation processSourceFiles(Collection<File> sourceFiles) {
        long start = System.nanoTime();

        CompilationState previousCompileState = previousCompileStateCache.get();
        IncementalCompileSourceProcessor processor = incrementalCompileFilesFactory.filesFor(previousCompileState);
        for (File sourceFile : sourceFiles) {
            processor.processSource(sourceFile);
        }
        IncrementalCompilation result = processor.getResult();

        long end = System.nanoTime();

        System.out.println("HEADER ANALYSIS");
        System.out.println("analysis time: " + (end-start)/1000000);
        System.out.println("source files: " + result.getSourceFileIncludeDirectives().size());
        System.out.println("recompile: " + result.getRecompile().size());
        System.out.println("unresolved? " + result.isUnresolvedHeaders());
        System.out.println("headers: " + result.getExistingHeaders().size());
        System.out.println("discovered: " + result.getDiscoveredInputs().size());

        return result;
    }

}
