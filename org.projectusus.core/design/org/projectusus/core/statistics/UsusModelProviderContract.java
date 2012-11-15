package org.projectusus.core.statistics;

import java.util.Set;

import org.projectusus.c4j.C4JFileWriter;
import org.projectusus.c4j.UsusContractBase;
import org.projectusus.core.IMetricsResultVisitor;
import org.projectusus.core.IMetricsWriter;
import org.projectusus.core.IUsusModel;
import org.projectusus.core.IUsusModelForAdapter;
import org.projectusus.core.basis.GraphNode;

public class UsusModelProviderContract extends UsusContractBase<UsusModelProvider> {

    public UsusModelProviderContract( UsusModelProvider target ) {
        super( target );
    }

    public void classInvariant() {
        // TODO no class invariant identified yet
    }

    public static void pre_ususModel() {
        // TODO no pre-condition identified yet
    }

    public static void post_ususModel() {
        IUsusModel returnValue = (IUsusModel)getReturnValue();
        // TODO no post-condition identified yet
    }

    public static void pre_getMetricsWriter() {
        // TODO no pre-condition identified yet
    }

    public static void post_getMetricsWriter() {
        IMetricsWriter returnValue = (IMetricsWriter)getReturnValue();
        // TODO no post-condition identified yet
    }

    public static void pre_ususModelForAdapter() {
        // TODO no pre-condition identified yet
    }

    public static void post_ususModelForAdapter() {
        IUsusModelForAdapter returnValue = (IUsusModelForAdapter)getReturnValue();
        // TODO no post-condition identified yet
    }

    public static void pre_getAllClassRepresenters() {
        // TODO no pre-condition identified yet
    }

    public static void post_getAllClassRepresenters() {
        Set<GraphNode> returnValue = (Set<GraphNode>)getReturnValue();
        // TODO no post-condition identified yet
    }

    public static void pre_getAllPackages() {
        // TODO no pre-condition identified yet
    }

    public static void post_getAllPackages() {
        Set<GraphNode> returnValue = (Set<GraphNode>)getReturnValue();
        // TODO no post-condition identified yet
    }

    public static void pre_getAllCrossPackageClasses() {
        // TODO no pre-condition identified yet
    }

    public static void post_getAllCrossPackageClasses() {
        Set<GraphNode> returnValue = (Set<GraphNode>)getReturnValue();
        // TODO no post-condition identified yet
    }

    public static void pre_acceptAndGuide( IMetricsResultVisitor visitor ) {
        // TODO Auto-generated pre-condition
        C4JFileWriter.assertStatic( visitor != null, "visitor_not_null" );
    }

    public static void post_acceptAndGuide( IMetricsResultVisitor visitor ) {
        // TODO no post-condition identified yet
    }

}
