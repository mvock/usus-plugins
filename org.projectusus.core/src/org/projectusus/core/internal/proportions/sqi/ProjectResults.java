// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.sqi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

public class ProjectResults extends Results<IFile, FileResults> {

    private final IProject projectOfResults;
    private IFile currentFile;

    public ProjectResults( IProject project ) {
        this.projectOfResults = project;
    }

    public IProject getProjectOfResults() {
        return projectOfResults;
    }

    public void setCurrentFile( IFile currentFile ) {
        this.currentFile = currentFile;
    }

    public FileResults getCurrentFileResults() {
        return getResults( currentFile, new FileResults( currentFile ) );
    }

    public void dropResults( IFile file ) {
        remove( file );
    }

    public Map<IFile, Integer> getViolationsForProject( IsisMetrics metric ) {
        Map<IFile, Integer> violations = new HashMap<IFile, Integer>();
        for( IFile file : this.keySet() ) {
            List<Integer> lineNumbers = new ArrayList<Integer>();
            this.get( file ).getViolationLineNumbers( metric, lineNumbers );
            for( Integer lineNo : lineNumbers ) {
                violations.put( file, lineNo );
            }
        }
        return violations;
    }
}
