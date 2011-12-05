/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dabloem.jsf.less.component;

import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;

/**
 *
 * @author duncan
 */
@ResourceDependency(name="less-1.1.5.js", target="head")
@FacesComponent("lessStyleSheetComponent")
public class LessStyleSheetComponent extends UIComponentBase {
    
    private final String RENDERTYPE = "javax.faces.resource.LessStylesheet";

    public LessStyleSheetComponent() {
        setRendererType(RENDERTYPE);
    }

    @Override
    public String getFamily() {
        return "javax.faces.Output";
    }
    
    
}
