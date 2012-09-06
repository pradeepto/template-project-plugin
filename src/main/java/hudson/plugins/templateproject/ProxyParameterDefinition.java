package hudson.plugins.templateproject;

import hudson.Extension;
import hudson.model.*;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.DataBoundConstructor;

import java.util.ArrayList;

public class ProxyParameterDefinition extends ParameterDefinition {
    private String projectName;

    @DataBoundConstructor
    public ProxyParameterDefinition(String projectName, String name, String description) {
        super(name,description);
        this.projectName = projectName;
    }

    public ProxyParameterDefinition(String name) {
        super(name);
    }

    public ProxyParameterDefinition(String name, String description) {
        super(name, description);
    }

    @Extension
    public static class DescriptorImpl extends ParameterDescriptor {
        @Override
        public String getDisplayName() {
            return "Use parameters from another project";
        }

        @Override
        public ParameterDefinition newInstance(StaplerRequest req, JSONObject formData) throws FormException {
            return req.bindJSON(ProxyParameterDefinition.class, formData);
        }
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

    public ArrayList<ParameterDefinition> getParameters() {
        System.out.println("getParameters called.");
        ArrayList<ParameterDefinition> parameters = new ArrayList();

        ParametersDefinitionProperty projectParams = getProject().getProperty(ParametersDefinitionProperty.class);
        if(projectParams == null) {
            return parameters;
        }

        for(ParameterDefinition parameterDefinition : projectParams.getParameterDefinitions()) {
            ParameterValue defaultValue = parameterDefinition.getDefaultParameterValue();
            if( defaultValue == null ) {
                continue;
            }

            if (defaultValue instanceof  StringParameterValue) {
                parameters.add(parameterDefinition);
            }
        }

        if( parameters instanceof ArrayList ) {
            System.out.println("ArrayList");
        }

        System.out.println(parameters.toString());
        System.out.println(parameters.size());
        return parameters;
    }
}
