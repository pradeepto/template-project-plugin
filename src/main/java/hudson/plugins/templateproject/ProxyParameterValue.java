package hudson.plugins.templateproject;

import hudson.Extension;
import hudson.model.ParameterValue;
import org.kohsuke.stapler.DataBoundConstructor;

public class ProxyParameterValue extends ParameterValue {

    @DataBoundConstructor
    public ProxyParameterValue(String name, String description) {
        super(name, description);
    }

}
