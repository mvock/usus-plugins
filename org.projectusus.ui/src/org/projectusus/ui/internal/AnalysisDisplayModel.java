package org.projectusus.ui.internal;

import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;
import static org.projectusus.core.internal.proportions.rawdata.UsusModel.ususModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.projectusus.core.IUsusModel;
import org.projectusus.core.IUsusModelListener;
import org.projectusus.core.basis.CodeProportion;
import org.projectusus.core.basis.Hotspot;
import org.projectusus.core.internal.project.FindUsusProjects;
import org.projectusus.ui.internal.proportions.cockpit.MetricStatisticsCategory;

public class AnalysisDisplayModel {

    private static AnalysisDisplayModel instance;

    private final Set<IDisplayModelListener> listeners;

    private final IUsusModel ususModel;

    private List<AnalysisDisplayEntry> snapshot = new ArrayList<AnalysisDisplayEntry>();

    private IUsusModelListener listener;

    private final DisplayCategories displayCategories;

    public static AnalysisDisplayModel displayModel() {
        if( instance == null ) {
            instance = new AnalysisDisplayModel( ususModel() );
        }
        return instance;
    }

    private AnalysisDisplayModel( IUsusModel ususModel ) {
        super();
        this.ususModel = ususModel;
        listeners = new HashSet<IDisplayModelListener>();
        displayCategories = new DisplayCategories();
        initModelListener();
    }

    private void initModelListener() {
        listener = new IUsusModelListener() {
            public void ususModelChanged() {
                handleUsusModelChanged();
            }
        };
        ususModel().addUsusModelListener( listener );
    }

    protected void handleUsusModelChanged() {
        if( hasUsusProjects() ) {
            if( getSnapshot().isEmpty() ) {
                createSnapshot();
            }
        }
        AnalysisDisplayCategory[] categories = new AnalysisDisplayCategory[] { new MetricStatisticsCategory( createDisplayEntries( ususModel.getCodeProportions() ) ) };
        displayCategories.replaceCategories( categories );
        notifyListeners();
    }

    public AnalysisDisplayCategory[] getCategories() {
        return displayCategories.getCategories();
    }

    private List<AnalysisDisplayEntry> createDisplayEntries( List<CodeProportion> codeProportions ) {
        List<AnalysisDisplayEntry> result = new ArrayList<AnalysisDisplayEntry>();
        for( CodeProportion codeProportion : codeProportions ) {
            String label = codeProportion.getMetricLabel();
            double level = codeProportion.getLevel();
            int violations = codeProportion.getViolations();
            String basis = codeProportion.getBasis().toString();
            List<Hotspot> hotspots = codeProportion.getHotspots();
            result.add( new AnalysisDisplayEntry( label, level, violations, basis, codeProportion.hasHotspots(), hotspots, trendValueFor( label ) ) );
        }
        return result;
    }

    public List<AnalysisDisplayEntry> getEntriesOfAllCategories() {
        List<AnalysisDisplayEntry> result = new ArrayList<AnalysisDisplayEntry>();
        for( AnalysisDisplayCategory category : getCategories() ) {
            result.addAll( Arrays.asList( category.getChildren() ) );
        }
        return result;
    }

    public void createSnapshot() {
        snapshot = getEntriesOfAllCategories();
    }

    public List<AnalysisDisplayEntry> getSnapshot() {
        return snapshot;
    }

    private Double trendValueFor( String label ) {
        for( AnalysisDisplayEntry codeProportion : snapshot ) {
            if( codeProportion.isSameKindAs( label ) ) {
                return Double.valueOf( codeProportion.getLevel() );
            }
        }
        return null;
    }

    public void addModelListener( IDisplayModelListener listener ) {
        listeners.add( listener );
    }

    public void removeModelListener( IDisplayModelListener listener ) {
        listeners.remove( listener );
    }

    // internal
    // ////////

    public void notifyListeners() {
        for( IDisplayModelListener listener : listeners ) {
            listener.updateCategories( this );
        }
    }

    private boolean hasUsusProjects() {
        IProject[] wsProjects = getWorkspace().getRoot().getProjects();
        return new FindUsusProjects( wsProjects ).compute().size() > 0;
    }

}
