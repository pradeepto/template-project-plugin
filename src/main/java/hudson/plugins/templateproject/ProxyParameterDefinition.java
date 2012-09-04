package hudson.plugins.templateproject;

import hudson.model.Hudson;
import hudson.model.ParameterDefinition;
import hudson.model.ParameterValue;
import hudson.model.AbstractProject;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.DataBoundConstructor;

public class ProxyParameterDefinition extends ParameterDefinition {
    private String projectName;

    @DataBoundConstructor
    public ProxyParameterDefinition(String name) {
        super(name);
    }

    public ProxyParameterDefinition(String name, String description) {
        super(name, description);
    }

    public ProxyParameterDefinition(String projectName, String name, String description) {
        super(name,description);
        this.projectName = projectName;
    }

    @Override
    public ParameterValue createValue(StaplerRequest staplerRequest, JSONObject jsonObject) {
        return new ProxyParameterValue(getName(), getDescription());
    }

    @Override
    public ParameterValue createValue(StaplerRequest staplerRequest) {
        return new ProxyParameterValue(getName(), getDescription());
    }

    public AbstractProject<?, ?> getProject() {
        return (AbstractProject<?, ?>) Hudson.getInstance()
                .getItem(projectName);
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDisplayName() {
        return "Use parameters from another project";
    }
}
