// Copyright (c) 2009 by the projectusus.org contributors
// This software is released under the terms and conditions
// of the Eclipse Public License (EPL) 1.0.
// See http://www.eclipse.org/legal/epl-v10.html for details.
package org.projectusus.core.internal.proportions.sqi;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.projectusus.core.internal.proportions.model.Hotspot;
import org.projectusus.core.internal.proportions.model.IHotspot;
import org.projectusus.core.internal.proportions.sqi.jdtdriver.ASTSupport;

public class FileRawData extends RawData<Integer, ClassRawData> {

    private final IFile fileOfResults;

    public FileRawData( IFile file ) {
        super(); // sagt AL ;)
        this.fileOfResults = file;
    }

    public IFile getFileOfResults() {
        return fileOfResults;
    }

    public void setCCResult( MethodDeclaration methodDecl, int value ) {
        getResults( methodDecl ).setCCResult( methodDecl, value );
    }

    public void setCCResult( Initializer initializer, int value ) {
        getResults( initializer ).setCCResult( initializer, value );
    }

    public void setMLResult( MethodDeclaration methodDecl, int value ) {
        getResults( methodDecl ).setMLResult( methodDecl, value );
    }

    public void setMLResult( Initializer initializer, int value ) {
        getResults( initializer ).setMLResult( initializer, value );
    }

    public void addClass( AbstractTypeDeclaration node ) {
        getResults( node );
    }

    private ClassRawData getResults( AbstractTypeDeclaration node ) {
        return getResults( node.getStartPosition(), node.getName().toString(), node.resolveBinding().getQualifiedName() );
    }

    public int getNumberOfClasses() {
        return getResultCount();
    }

    @Override
    public void addHotspots( IsisMetrics metric, List<IHotspot> hotspots ) {
        List<IHotspot> localHotspots = new ArrayList<IHotspot>();
        super.addHotspots( metric, localHotspots );
        for( IHotspot hotspot : localHotspots ) {
            ((Hotspot)hotspot).setFile( fileOfResults );
        }
        hotspots.addAll( localHotspots );
    }

    private ClassRawData getResults( int start, String name, String qualifiedName ) {
        return getResults( new Integer( start ), new ClassRawData( name, qualifiedName, start ) );
    }

    private ClassRawData getResults( MethodDeclaration node ) {
        return getResults( ASTSupport.findEnclosingClass( node ) );
    }

    private ClassRawData getResults( Initializer node ) {
        return getResults( ASTSupport.findEnclosingClass( node ) );
    }

    public ClassRawData getResults( IJavaElement element ) {
        if( element == null ) {
            return null;
        }
        ICompilationUnit compilationUnit = JDTSupport.getCompilationUnit( element );
        if( compilationUnit == null ) {
            return null;
        }

        try {
            for( Integer startPosition : getAllKeys() ) {
                IJavaElement foundElement = compilationUnit.getElementAt( startPosition.intValue() );
                if( element.equals( foundElement ) ) {
                    return getResults( startPosition.intValue(), "", "" ); //$NON-NLS-1$ //$NON-NLS-2$
                }
            }
        } catch( JavaModelException e ) {
            return null;
        }
        return null;
    }
}